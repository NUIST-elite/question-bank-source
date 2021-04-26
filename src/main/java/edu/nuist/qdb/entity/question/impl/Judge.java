package edu.nuist.qdb.entity.question.impl;

import java.util.List;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.AnswerJudge;
import edu.nuist.qdb.entity.component.impl.Stem;
import edu.nuist.qdb.entity.question.Question;
import lombok.Data;

/**
 * 判断题， 
 * 表头： ｜ 序号 ｜ 题型  ｜    题干 ｜  答案              ｜ 
 * 表例： ｜  1   ｜ 判断题｜ 题干内容｜  题目答案 （T|F）   ｜ 
 * 答案只有T，代表True, F，代表False;
 */
@Data
public class Judge extends Question{
    private Stem stem;
    private AnswerJudge answer;

    public Judge(Question q){
        super(q);
        List<Component> components = q.getComponents();
        
        for(Component c : components){
            if( c.getType() == ComponentType.STEM){
                this.stem = new Stem(c.getC());
            }else if( c.getType() == ComponentType.ANSWER ){
                this.answer = new AnswerJudge(c.getC());
            }
        }
    }
}
