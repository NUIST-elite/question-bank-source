package edu.nuist.qdb.entity.question.impl;

import java.util.LinkedList;
import java.util.List;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.Stem;
import edu.nuist.qdb.entity.question.Question;
import edu.nuist.qdb.entity.question.QuestionAssemblor;
import edu.nuist.qdb.entity.question.QuestionTypeEnum;
import lombok.Data;

/**
 * 综合题包括题干，子题目，子题目可以为填空题，判断题，多选题，单选题，简答题中的任何一个，各子题分别以一个结束表项来与其它子题分隔开来
 * 表头： ｜ 序号 ｜ 题型  ｜    题干 ｜  题号|  题型        ｜ .... |    结束   |    题号 ｜题型               ｜ .... |    结束   |
 * ---------------------------------｜     子题1开始-----------  子题1结束------|----子题2开始-----------  子题2结束-----
 * 表例： ｜  1   ｜ 综合题｜ 题干内容｜  子题题型      ｜       |    结束   |子题型              ｜ .... |    结束   |
 * 子题的定义与其它题型的定义相同，综合题的答案分布在各子题的答案中
 */
@Data
public class Synthesis extends Question {
    private Stem stem;
    private List<Question> subQuestions = new LinkedList<>();

    public Synthesis(Question q) throws Exception {
        super(q);
        String topCode = q.getCode();
        List<Component> components = q.getComponents();

        List<Component> currentQuestion = new LinkedList<>();
        int index = 0;
        boolean flag = false; //flag 用来避开第一个综合题的题型

        while (index < components.size()) {
            Component c = components.get(index);

            if (c.getType() == ComponentType.STEM) {
                this.stem = new Stem(c.getC());
            } else if (c.getType() == ComponentType.QUESTIONTYPE) {
                if (!flag) {
                    flag = true;
                } else {
                    while (c.getType() != ComponentType.SUBQUESTIONEND) {
                        System.out.println(c.getType() + ",,," + c.getC().text());
                        currentQuestion.add(c);
                        index++;
                        c = components.get(index);
                    }

                    System.out.println("assemble");
                    subQuestions.add(assembleSubQuestion(currentQuestion));
                    index++;
                    c = components.get(index);
                    continue;
                }
            }
            index++;
        }
    }

    public Question assembleSubQuestion(List<Component> currentQuestion) throws Exception {

        QuestionTypeEnum type = null;
        String code = "";
        for (Component c : currentQuestion) {
            if (c.getType() == ComponentType.QUESTIONTYPE) {
                type = QuestionTypeEnum.getByName(c.getC().text());
            } else if (c.getType() == ComponentType.CODE) {
                code = c.getC().text();
            }
        }
        Question q = Question.builder().code(code).type(type).components(currentQuestion).build();
        currentQuestion.clear();

        return new QuestionAssemblor().getQuestion(type, q);
    }
}
