package edu.nuist.qdb.util;

import com.alibaba.druid.sql.visitor.functions.Char;
import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.*;
import edu.nuist.qdb.entity.question.Question;
import edu.nuist.qdb.entity.question.impl.*;
import edu.nuist.qdb.entity.question.impl.Blank;
import edu.nuist.qdb.exception.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AssembleCheck {
    /**
     * 问题校验入口函数
     *
     * @param question 问题
     * @throws Exception exception
     */
    public void check(Question question) throws Exception {
        List<Component> components = question.getComponents();

        if (components == null || components.size() == 0) {
            throw new NullQuestionException("");
        }

        boolean stem = false;
        String code = null;

        for (Component component: components) {
            if (component.getType().equals(ComponentType.STEM)) {
                if (component.getC() != null && !component.getC().text().equals("")) {
                    stem = true;
                }
            } else if (component.getType().equals(ComponentType.CODE)) {
                code = component.getC().text();
            }
        }

        if (!stem) {
            throw new NullStemException(code);
        }

        if (question instanceof SingleChoice) {
            // 单选校验
            this.singleChoiceCheck(question, code);
        } else if (question instanceof MultiChoice) {
            // 多选校验
            this.multiChoiceCheck(question, code);
        } else if (question instanceof Judge) {
            // 判断校验
            this.JudgeCheck(question, code);
        } else if (question instanceof ShortAnswer) {
            // 简答校验
            this.shortAnswerCheck(question, code);
        } else if (question instanceof Blank) {
            // 填空校验
            this.BlankCheck(question, code);
        } else if (question instanceof Synthesis) {
            // 综合题校验，对每一个子问题单独校验
            Synthesis synthesis = (Synthesis) question;
            List<Question> questions = synthesis.getSubQuestions();
            if (questions == null || questions.size() == 0) {
                throw new NullQuestionException(code);
            } else {
                for (Question question1: questions) {
                    check(question1);
                }
            }
        }
    }


    /**
     * 单选校验
     *
     * @param question 问题
     * @param code 题号
     * @throws Exception exception
     */
    private void singleChoiceCheck(Question question, String code) throws Exception {
        SingleChoice singleChoice = (SingleChoice) question;
        char answerTxt = 0;
        List<Component> components = singleChoice.getComponents();

        String str = answerCheck(ComponentType.ANSWER, components);
        if (str == null) {
            throw new NullAnswerException(code);
        }
        answerTxt = str.toCharArray()[0];

        List<Option> options = singleChoice.getOptions();
        if (answerTxt >= 'A' && answerTxt < 'A' + options.size()) {
            return;
        }

        throw new WrongAnswerException(code + "答案不在选项中");
    }

    /**
     * 多选题校验
     *
     * @param question 问题
     * @param code 题号
     * @throws Exception exception
     */
    private void multiChoiceCheck(Question question, String code) throws Exception {
        MultiChoice multiChoice = (MultiChoice) question;
        AnswerOptionGroup answerOptionGroup = multiChoice.getGroup();
        if(answerOptionGroup == null) {
            throw new NullAnswerException(code);
        }

        List<Option> options = multiChoice.getOptions();
        int size = options.size();

        // 答案集
        List<AnswerOption> answerOptions = answerOptionGroup.getGroup();

        // 用来判断是否有重复答案
        List<Character> alpha = new ArrayList<>();
        for (AnswerOption answerOption: answerOptions) {
            char[] txt = answerOption.getC().text().toCharArray();
            if (txt.length > 1) {
                throw new WrongAnswerException(code + "答案未用英文逗号隔开");
            }

            if (txt[0] < 'A' || txt[0] >= 'A' + size) {
                throw new WrongAnswerException(code + "答案不在选项范围内");
            }

            if (alpha.contains(txt[0])) {
                throw new WrongAnswerException(code + "有重复答案");
            } else {
                alpha.add(txt[0]);
            }
        }
    }

    /**
     * 判断题校验
     *
     * @param question 问题
     * @param code 题号
     * @throws Exception exception
     */
    private void JudgeCheck(Question question, String code) throws Exception {
        char answerTxt = 0;
        Judge judge = (Judge) question;
        List<Component> components = judge.getComponents();
        String str = answerCheck(ComponentType.ANSWER, components);
        if (str == null) {
            throw new NullAnswerException(code);
        }

        answerTxt = str.toCharArray()[0];
        if (answerTxt != 'T' && answerTxt != 'F') {
            throw new WrongAnswerException(code + "答案不是T或F");
        }
    }

    /**
     * 简答题校验
     *
     * @param question 问题
     * @param code 题号
     * @throws Exception exception
     */
    private void shortAnswerCheck(Question question, String code) throws Exception {
        ShortAnswer shortAnswer = (ShortAnswer) question;
        List<Component> components = shortAnswer.getComponents();

        String answerTxt = answerCheck(ComponentType.ANSWER, components);
        if (answerTxt == null) {
            throw new NullAnswerException(code);
        }
    }

    /**
     * 填空题校验
     *
     * @param question 问题
     * @param code 题号
     * @throws Exception exception
     */
    private void BlankCheck(Question question, String code) throws Exception {
        Blank blank = (Blank) question;
        List<String> tags = blank.getTags();
        if (tags == null || tags.size() <= 0) {
            throw new NullBlankException(code);
        }

        List<AnswerBlankGroup> answerBlankGroups = blank.getGroupAnswers();
        System.out.println(answerBlankGroups);
        if(answerBlankGroups != null && answerBlankGroups.size() != 0) {
            int len = 0;
            for (AnswerBlankGroup answerBlankGroup: answerBlankGroups) {
                len += answerBlankGroup.getBlanks().size();
            }

            if (len != tags.size()) {
                throw new WrongAnswerException(code + "答案数量与给空不相等");
            }
            return;
        }

        List<AnswerBlank> answerBlanks = blank.getBlankAnswers();
        if (answerBlanks == null || answerBlanks.size() == 0) {
            throw new NullAnswerException(code);
        } else {
            if (answerBlanks.size() != tags.size()) {
                throw new WrongAnswerException(code + "答案数量与给空不相等");
            }
        }
    }

    /**
     * 判断答案是否存在
     *
     * @param type 答案种类
     * @param components 问题组成部分
     * @return answer
     */
    private String answerCheck(ComponentType type, List<Component> components) {
        String answerTxt = null;
        for (Component component: components) {
            if (component.getType().equals(type)) {
                if (component.getC() != null && !component.getC().text().equals("")) {
                    answerTxt = component.getC().text();
                    break;
                }
            }
        }

        return answerTxt;
    }
}
