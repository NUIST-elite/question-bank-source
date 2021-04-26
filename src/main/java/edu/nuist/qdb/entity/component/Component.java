package edu.nuist.qdb.entity.component;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Component")
public class Component {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    private long relId;

    @Transient
    private Content c;
    @Transient
    private List<Attribute> attributes  = new LinkedList<Attribute>();;

    private String type;

    public Component(Content c){
        this.c = c;
    }

    public String html(){
        return c.html();
    }

    public ComponentType getType(){
        if( this.type != null){
            return ComponentType.getByName(this.type);
        }
        for(Attribute a : this.attributes){
            if(a.getKey().equals("type")){
               return ComponentType.getByName(a.getValue());
            }
        }
        return null;
    }

    public List<Component> getComponents(){
        return null;
    }

    public void restore(){

    }

}
