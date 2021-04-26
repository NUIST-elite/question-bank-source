package edu.nuist.qdb.render;

import edu.nuist.qdb.entity.base.Fragment;
import edu.nuist.qdb.entity.question.Question;
import edu.nuist.qdb.entity.question.QuestionAssemblor;
import edu.nuist.qdb.entity.question.QuestionService;
import edu.nuist.qdb.entity.question.impl.*;
import edu.nuist.qdb.xlsutil.Cell;
import edu.nuist.qdb.xlsutil.XLSReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.nuist.qdb.entity.question.QuestionTypeEnum;

@CrossOrigin
@RestController
public class QuestionRender {
    @Autowired
    private QuestionService questionService;

    /**
     * 批量存储
     * @return questions
     * @throws Exception exception
     */
    @GetMapping("/batchsave")
    public List<Question> batchSave() throws Exception {
        QuestionAssemblor qAssemblor = new QuestionAssemblor();
//<<<<<<< HEAD
        XLSReader rr = new XLSReader("e:/testwzj.xls");
//=======
//        XLSReader rr = new XLSReader("d:/testwzj.xls");
//>>>>>>> a7a530f01556a76c37fc3d841bf315bb56a50e21
            List<List<Cell>> table = rr.read(0);
            List<Question> questions = new LinkedList<>();

            int index = 0;
            for (List<Cell> row: table) {
                try {
                    if (index == 0) {
                        index++;
                        continue;
                    }
                    System.out.println(row);
                    Question q = qAssemblor.exec(row);
                    questionService.save(q);
                    questions.add(q);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return questions;
    }

    /**
     * 查找所有
     * @return list
     */
    @GetMapping("/find")
    public List<Question> find() {
        List<Question> questions = questionService.findAll();
        List<Question> rsts = questions.stream()
                .map(question -> {
                    long id = question.getId();
                    Question question1 = new Question();
                    try {
                        question1 = QuestionAssemblor.getQuestion(question.getType(), question);
                        question1.setId(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return question1;
                })
                .collect(Collectors.toList());

        rsts.forEach(question -> {
            if (question.getType().equals(QuestionTypeEnum.BLANK)) {
                Blank blank = (Blank) question;
                List<Fragment> fragments = blank.getStem().getC().getFragments();
                 fragments.forEach(fragment -> {
                     Pattern pattern = Pattern.compile(Blank.TAGREGEX);
                     Matcher matcher  = pattern.matcher(fragment.text());
                     StringBuffer sb = new StringBuffer();
                     while(matcher.find()) {
                         matcher.appendReplacement(sb, "<span class=\"blank\"></span>");
                     }
                     matcher.appendTail(sb);
                     fragment.setText(sb.toString());
                 });
            }
        });

        return rsts;
    }
}
