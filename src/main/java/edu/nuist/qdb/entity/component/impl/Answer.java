package edu.nuist.qdb.entity.component.impl;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.similarcheckor.SimilarCheckor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


public class Answer extends Component{
    public static final ComponentType type = ComponentType.ANSWER;

    public Answer(Content c){
        super(c);
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
    }

    public double judge(String answer){
        String s1 = answer;
        String s2 = super.getC().text().trim();
        return SimilarCheckor.check(s1, s2);
    }

}
