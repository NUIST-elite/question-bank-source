package edu.nuist.qdb.entity.component.impl;

import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

public class SubQuestionEnd extends Component{
    public static final ComponentType type = ComponentType.SUBQUESTIONEND;
    public SubQuestionEnd(Content c){
        super(c);
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
    }
}
