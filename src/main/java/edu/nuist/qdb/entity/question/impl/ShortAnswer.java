package edu.nuist.qdb.entity.question.impl;

import java.util.List;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.Answer;
import edu.nuist.qdb.entity.component.impl.Stem;
import edu.nuist.qdb.entity.question.Question;
import lombok.Data;

/**
 * 简答题， 
 * 表头： ｜ 序号 ｜ 题型  ｜    题干 ｜  答案        ｜ 
 * 表例： ｜  1   ｜ 简答题｜ 题干内容｜  题目答案    ｜ 
 */
@Data
public class ShortAnswer extends Question{
    private Stem stem;
    private Answer answer;
    
    public ShortAnswer(Question q){
        super(q);
        List<Component> components = q.getComponents();
        
        for(Component c : components){
            if( c.getType() == ComponentType.STEM){
                this.stem = new Stem(c.getC());
            }else if( c.getType() == ComponentType.ANSWER ){
                this.answer = (Answer)c;
            }
        }
    }
}
