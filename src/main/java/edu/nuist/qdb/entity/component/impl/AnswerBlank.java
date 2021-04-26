package edu.nuist.qdb.entity.component.impl;
import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Data
@NoArgsConstructor
public class AnswerBlank extends Component{
    public static final ComponentType type = ComponentType.ANSWERBLANK;
    private String tag;

    public AnswerBlank(Content c){
        super(c);
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
    }

    public AnswerBlank(String tag, Content c){
        super(c);
        super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
        super.getAttributes().add(Attribute.builder().key("tag").value(tag).build());
        this.tag = tag;
    }

    public boolean judge(String answer){
        return super.getC().text().equals(answer.trim());
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
