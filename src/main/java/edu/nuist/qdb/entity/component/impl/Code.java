package edu.nuist.qdb.entity.component.impl;

import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


public class Code extends Component{
    public static final ComponentType type = ComponentType.CODE;
    private String code;

    public Code(Content c){
        super(c);
        this.code = c.text();
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
        super.getAttributes().add(Attribute.builder().key("code").value(c.text()).build());
     }
}
