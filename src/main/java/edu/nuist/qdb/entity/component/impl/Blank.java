package edu.nuist.qdb.entity.component.impl;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Data
public class Blank extends Component{
    public static final ComponentType type = ComponentType.UNKNOWN;
    private String tag;

    public Blank(Content c){
        super(c);
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
        super.getAttributes().add(Attribute.builder().key("tag").value(c.text()).build());
        this.tag = c.text();
    }

    @Override
    public void restore(){
        for(Attribute attr : super.getAttributes()){
            if( attr.getKey().equals("tag")){
                this.tag = attr.getValue();
            }
        }
    }
}
