package edu.nuist.qdb.entity.component.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Data
@NoArgsConstructor
public class AnswerOptionGroup extends Component{
    public static final ComponentType type = ComponentType.ANSWERBLANKGROUP;
    public List<AnswerOption> group;
    
    public AnswerOptionGroup(List<Content> contents){
        this.group = new LinkedList<>();
        for(Content c : contents){
            this.group.add(new AnswerOption(c));
        }
        
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
    }

    public List<Component> getComponents(){
        List<Component> rst = new LinkedList<Component>();
        for(AnswerOption blank: group){
            rst.add(blank);
        }
        return rst;
    }

    public String getAnswerText(){
        List<Component> comps = getComponents();
        String rst = "";
        for(Component comp : comps){
            rst += comp.getC().text() + ",";
        }
        return rst.substring(0, rst.length()-1);
    }

    public double judge(List<String> answers){
        Set<String> tmp = new HashSet<String>();
        for(String s : answers ){
            tmp.add(s);
        }    
        int count = 0;
        for(AnswerOption o : group){
            for(String t : tmp){
                if( o.judge(t) ){
                    count++;
                }
            }
        }
        return count * 1.0 / group.size();
    }
    
}
