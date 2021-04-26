package edu.nuist.qdb.entity.component.impl;

import java.util.LinkedList;
import java.util.List;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;


@Data
public class BlankGroup extends Component{
    public static final ComponentType type = ComponentType.UNKNOWN;
    @Transient
    private List<Blank> group;
    private String groupTag;

    public BlankGroup(List<Content> tagedContents, String groupTag ){
        this.group = new LinkedList<Blank>();
        for(Content e : tagedContents){
            group.add( new Blank(e));
        }
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
        super.getAttributes().add(Attribute.builder().key("grouptag").value(groupTag).build());
        this.groupTag = groupTag;
    }

    public List<Component> getComponents(){
        List<Component> rst = new LinkedList<Component>();
        for(Blank blank: group){
            rst.add(blank);
        }
        return rst;
    }

    @Override
    public void restore(){
        for(Attribute attr : super.getAttributes()){
            if( attr.getKey().equals("grouptag")){
                this.groupTag = attr.getValue();
            }
        }
    }
}
