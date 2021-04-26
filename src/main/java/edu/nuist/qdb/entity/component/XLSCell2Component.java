package edu.nuist.qdb.entity.component;

import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.entity.component.impl.Analysis;
import edu.nuist.qdb.entity.component.impl.Answer;
import edu.nuist.qdb.entity.component.impl.AnswerBlank;
import edu.nuist.qdb.entity.component.impl.Code;
import edu.nuist.qdb.entity.component.impl.Option;
import edu.nuist.qdb.entity.component.impl.QuestionType;
import edu.nuist.qdb.entity.component.impl.Stem;
import edu.nuist.qdb.entity.component.impl.SubQuestionEnd;
import edu.nuist.qdb.entity.component.impl.Unknown;
import edu.nuist.qdb.xlsutil.Cell;

/**
 * 在初始CELL 转化成COMPONENT中，只有以下五种类型，题干，分析，选项，答案，填空答案，后面的几种要在分析后，再形成
 */
public class XLSCell2Component {
    public static Component exec(Cell c){
        Content content = new Content(c.getDatas());
        if( ComponentType.STEM.getName().equals(c.getHead())){
            Component s = new Stem(content);
            return s;
        }else if( ComponentType.CODE.getName().equals(c.getHead())){
            Component s = new Code(content);
            return s;
        }else if( ComponentType.ANALYSIS.getName().equals(c.getHead())){
            Component s = new Analysis(content);
            return s;
        }else if( ComponentType.QUESTIONTYPE.getName().equals(c.getHead())){
            Component s = new QuestionType(content);
            return s;
        }else if( c.getHead().startsWith(ComponentType.OPTION.getName())){
            Component s = new Option(content); //要完善序号属性
            return s;
        }else if( ComponentType.ANSWER.getName().equals(c.getHead())){
            Component s = new Answer(content);//要进一步转换成其它ANSWER类
            return s;
        }else if( ComponentType.ANSWERBLANK.getName().equals(c.getHead())){
            Component s = new AnswerBlank(content);//要判断是否是ANSWERBLANKGROUP,还要完善TAG属性
            return s;
        }else if( ComponentType.SUBQUESTIONEND.getName().equals(c.getHead())){
            Component s = new SubQuestionEnd(content);//综合题中一个子题目的结束分隔
            return s;
        }else {
            Component s = new Unknown(content);//未知的Component
            return s;
        }
    }
}
