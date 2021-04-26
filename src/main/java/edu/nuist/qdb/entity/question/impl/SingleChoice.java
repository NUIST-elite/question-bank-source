package edu.nuist.qdb.entity.question.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.AnswerOption;
import edu.nuist.qdb.entity.component.impl.Option;
import edu.nuist.qdb.entity.component.impl.Stem;
import edu.nuist.qdb.entity.question.Question;
import edu.nuist.qdb.exception.NullAnswerException;
import edu.nuist.qdb.exception.WrongAnswerException;
import lombok.Data;

/**
 * 多选题， 
 * 表头： ｜ 序号 ｜ 题型  ｜    题干 ｜ 选项 ｜ ...多个选项.... |  答案 ｜ 
 * 表例： ｜  1   ｜ 单选题｜ 题干内容｜ 选项1｜ ...多个选项.... |  A    ｜ 
 */
@Data
public class SingleChoice extends Question{
    private Stem stem;
    private List<Option> options = new ArrayList<>();
    private AnswerOption answer;


    public SingleChoice() {
    }

    public SingleChoice(Question q) throws Exception {
        super(q);

        List<Component> components = q.getComponents();
        char index = 'A';
        for(Component c : components){
            if( c.getType() == ComponentType.STEM){
                this.stem = new Stem(c.getC());
            }else if( c.getType() == ComponentType.OPTION ){
                options.add(new Option(c.getC(), index++));
            }else if( c.getType() == ComponentType.ANSWER ){
                this.answer = new AnswerOption(c.getC());
            }
        }
    }
    
}
