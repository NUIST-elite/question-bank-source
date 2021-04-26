package edu.nuist.qdb.entity.question.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.AnswerOptionGroup;
import edu.nuist.qdb.entity.component.impl.Stem;
import edu.nuist.qdb.entity.component.impl.Option;
import edu.nuist.qdb.entity.question.Question;
import edu.nuist.qdb.xlsutil.XLSReader;
import lombok.Data;


/**
 * 多选题， 
 * 表头： ｜ 序号 ｜ 题型  ｜    题干 ｜ 选项 ｜ ...多个选项.... |  答案 ｜ ， 其中答案由多个选项序号组成，中间用逗号分开
 * 表例： ｜  1   ｜ 多选题｜ 题干内容｜ 选项1｜ ...多个选项.... |  A,B,C ｜ ， 其中答案由多个选项序号组成，中间用逗号分开
 */
@Data
public class MultiChoice  extends Question{
    private Stem stem;
    private List<Option> options = new ArrayList<>();
    private AnswerOptionGroup group;

    public static final String SPLIT = ",";

    public MultiChoice() {
    }

    public MultiChoice(Question q) {
        super(q);
        List<Component> components = q.getComponents();
        char index = 'A';
        for(Component c : components){
            if( c.getType() == ComponentType.STEM){
                this.stem = new Stem(c.getC());
            }else if( c.getType() == ComponentType.OPTION ){
                options.add(new Option(c.getC(), index++));
            }else if( c.getType() == ComponentType.ANSWER ){
                String[] answers = c.getC().text().split(SPLIT);
                List<Content> contents = new LinkedList<>();
                for(String answer : answers) {
                    HashMap<String, String> datas = new HashMap<>();
                    datas.put(XLSReader.TEXT, answer);
                    contents.add( new Content(datas) );
                }
                this.group = new AnswerOptionGroup(contents);
            }
        }
    }
    
}
