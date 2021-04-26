package edu.nuist.qdb.entity.component.impl;


import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


public class AnswerOption extends Component{
    public static final ComponentType type = ComponentType.ANSWEROPTION;
    public AnswerOption(Content c){
        super(c);
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
    }

    public boolean judge(String answer){
        return super.getC().text().equals(answer.trim());
    }
}
