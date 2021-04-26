package edu.nuist.qdb.entity.question;

import java.util.ArrayList;
import java.util.List;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.XLSCell2Component;
import edu.nuist.qdb.entity.question.impl.Blank;
import edu.nuist.qdb.entity.question.impl.Judge;
import edu.nuist.qdb.entity.question.impl.MultiChoice;
import edu.nuist.qdb.entity.question.impl.ShortAnswer;
import edu.nuist.qdb.entity.question.impl.SingleChoice;
import edu.nuist.qdb.entity.question.impl.Synthesis;
import edu.nuist.qdb.xlsutil.Cell;
import edu.nuist.qdb.xlsutil.XLSReader;

public class QuestionAssemblor {
    public Question exec(List<Cell> row) throws Exception {
        System.out.println(row.size());
        QuestionTypeEnum type = getType( row );
        String code = getCode(row);
        List< Component > compontents = new ArrayList<Component>();

        for(Cell c : row){
            compontents.add(XLSCell2Component.exec(c));
        }
        
        Question q = Question.builder().code(code).type(type).components(compontents).build();
        
        return q;
    }

    public static Question getQuestion(QuestionTypeEnum type, Question q) throws Exception {

        switch( type ) {
            case BLANK:
                q = new Blank(q);
                break;
            case JUDEG:
                q = new Judge(q);
                break;
            case MULTICHOICES:
                q = new MultiChoice(q);
                break;
            case SHORTANSWER:
                q = new ShortAnswer(q);
                break;
            case CHOICE:
                q = new SingleChoice(q);
                break;
            case SYNTHESIS:
                q = new Synthesis(q);
                break;
        }
        return q;
    }


    public QuestionTypeEnum getType(List<Cell> row){
        for(Cell c : row){
            if(c.getHead().equals(ComponentType.QUESTIONTYPE.getName())){
                String type = c.getDatas().get(XLSReader.TEXT);
                return QuestionTypeEnum.getByName(type);
            }
        }
        return null;
    }

    public String getCode(List<Cell> row){
        for(Cell c : row){
            if(c.getHead().equals(ComponentType.CODE.getName())){
                return c.getDatas().get(XLSReader.TEXT);
            }
        }
        return "";
    }

}
