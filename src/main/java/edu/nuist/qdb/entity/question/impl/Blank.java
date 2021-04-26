package edu.nuist.qdb.entity.question.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.AnswerBlank;
import edu.nuist.qdb.entity.component.impl.AnswerBlankGroup;
import edu.nuist.qdb.entity.component.impl.Stem;
import edu.nuist.qdb.entity.question.Question;
import lombok.Data;

/**
 * 填空题， 
 * 表头： ｜ 序号 ｜ 题型  ｜ 题干    ｜ 答案 ｜ 答案 |...|  答案 |， 其中答案有多个
 * 表例： ｜  1   ｜ 填空题｜ 题干内容｜ 答案1｜ ...多个答案.... |  答案 ｜ ， 
   填空题中空的表示： 填空题中要填写的空，标注在题干中，如：
                               表达式 1 and 0的值___,  1 or 0的值___，
                               墙面装饰分为___和___, 
                     应该写成：表达式 1 and 0的值<#1>,  1 or 0的值<#2>，
                               墙面装饰分为<#1>和<#2>,                      
                     其中1，2为题中空的序号，序号要唯一
   填空题中答案分二种，一种是普通的答案，与题干中的空序号一一对应，一种是答案组，即几个空答案不需要按确定的顺序填写
                     如上例 表达式 1 and 0的值<#1>,  1 or 0的值<#2>，对应的答案为| <1>false ｜ <2> true, 答案与序号一一对应
                            墙面装饰分为<#1>和<#2>,  对应的答案为| <1|1>软装  ｜ <1|2>硬装  ｜ ，
                            这表明空1与2为成组，组号为1，二个空的答案不需要一一对应
 */
@Data
public class Blank extends Question {
    private Stem stem;
    private List<String> tags;
    private List<AnswerBlankGroup> groupAnswers;
    private List<AnswerBlank> blankAnswers;

    //题干中标识的空格位置： <#2>
    public static final String TAGREGEX = "(<#\\d+>)";
    //填空题的答案，以<3|2>第一个是组号，第二个是空格号，也可以<2>直接是空格号
    public static final String ANSWERPREFIX = "^<(\\d+\\|)?\\d+>"; 

    public Blank(Question q){
        super(q);
        List<Component> components = q.getComponents();
        List<Component> answers = new LinkedList<Component>();
        for(Component c : components){
            if( c.getType() == ComponentType.STEM){
                this.stem = new Stem(c.getC());
                this.tags = getTags(c.getC().text());
            }else if( c.getType() == ComponentType.ANSWER ){
                answers.add( c );
            }
        }

        getAnswers(answers);
    }

    private void getAnswers(List<Component> answers){
         
        List<AnswerBlank> blanks  = new LinkedList<>();
        HashMap<String, List<AnswerBlank>> groups = new HashMap<>();

        Pattern pattern = Pattern.compile(ANSWERPREFIX);
        for(Component c :answers ){
            String t = c.getC().text(); 
            Matcher matcher = pattern.matcher(t);
            if(matcher.find()) {
                String res = matcher.group();
                if( res.indexOf("|") != -1){
                    String gtag = res.substring(1, res.indexOf("|"));
                    String tag = res.substring(res.indexOf("|")+1, res.length()-1);
                    Content content = Content.getContent(t.substring(t.indexOf(">")+1));

                    List<AnswerBlank> tmp = groups.get(gtag);
                    if( tmp == null ) tmp = new LinkedList<>();
                    tmp.add(new AnswerBlank(tag, content));
                    groups.put(gtag, tmp);
                }else{
                    Content content = Content.getContent(t.substring(t.indexOf(">")+1));
                    String tag = res.substring(1, res.length()-1);
                    blanks.add(new AnswerBlank(tag, content));
                }
            }
        }

        List<AnswerBlankGroup> grst = new LinkedList<>();
        for(List<AnswerBlank> g : groups.values()){
            grst.add( new AnswerBlankGroup(g));
        }

        this.blankAnswers = blanks;
        this.groupAnswers = grst;
    }

    
    private List<String> getTags(String stem){
        Pattern pattern = Pattern.compile(TAGREGEX);
        Matcher matcher = pattern.matcher(stem);
        List<String> rst = new LinkedList<String>();
        while (matcher.find()) {
            String res = matcher.group();
            rst.add(res.substring(2, res.length()-1));
        }

        return rst;
    }
    
}
