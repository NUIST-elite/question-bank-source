package edu.nuist.qdb;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.nuist.qdb.exception.NullQuestionException;
import edu.nuist.qdb.util.AssembleCheck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.nuist.qdb.entity.question.Question;
import edu.nuist.qdb.entity.question.QuestionAssemblor;
import edu.nuist.qdb.entity.question.impl.Blank;
import edu.nuist.qdb.entity.question.impl.Judge;
import edu.nuist.qdb.entity.question.impl.MultiChoice;
import edu.nuist.qdb.entity.question.impl.ShortAnswer;
import edu.nuist.qdb.entity.question.impl.SingleChoice;
import edu.nuist.qdb.entity.question.impl.Synthesis;
import edu.nuist.qdb.xlsutil.Cell;
import edu.nuist.qdb.xlsutil.XLSReader;


public class TestQuestion {
    private static final AssembleCheck check = new AssembleCheck();
    
//    @Test
    public void assemble() throws Exception {
        QuestionAssemblor qAssemblor = new QuestionAssemblor();
        XLSReader rr = new XLSReader("d:/test.xls");
        try {
            List<Cell> row = rr.read(1).get(0);
            Question q = qAssemblor.exec(row);

            System.out.println(((Synthesis)q).getSubQuestions().get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkTest() throws Exception {
        QuestionAssemblor qAssemblor = new QuestionAssemblor();
        XLSReader rr = new XLSReader("d:/testwzj.xls");
        List<Cell> row = rr.read(0).get(4);
//        System.out.println(row);

        Question q = qAssemblor.exec(row);
        Blank blank = (Blank) q;
        check.check(blank);
    }
}
