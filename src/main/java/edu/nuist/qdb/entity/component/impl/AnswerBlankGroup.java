package edu.nuist.qdb.entity.component.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AnswerBlankGroup extends Component{
    public static final ComponentType type = ComponentType.ANSWERBLANKGROUP;
    @Transient
    private List<AnswerBlank> blanks;


    public AnswerBlankGroup(String groupTag, HashMap<String, Content> tagedContents){ //read content:tag:grouptag
        this.blanks = new LinkedList<>();
        for(Entry<String, Content> e : tagedContents.entrySet()) {
            blanks.add(new AnswerBlank(e.getKey(), e.getValue()));
        }
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
    }

    public List<Component> getComponents(){
        List<Component> rst = new LinkedList<Component>();
        for(AnswerBlank blank: blanks){
            rst.add(blank);
        }
        return rst;
    }


    public HashMap<String, Boolean> judge(HashMap<String, String> tagedAnswers){
        HashMap<String, Boolean> rst = new HashMap<String, Boolean>();
        for(AnswerBlank blank : blanks){
            rst.put(blank.getTag(), false);
        }

        for(String e : tagedAnswers.values()){
            for(AnswerBlank blank : blanks){
                if( blank.judge(e) ){
                    if( !rst.get(blank.getTag())){
                        rst.put(blank.getTag(), true);
                    }else continue;
                }
            }
        }
        return rst;
    }   

    

}
