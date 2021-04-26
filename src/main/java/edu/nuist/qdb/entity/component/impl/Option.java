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
public class Option extends Component{
   public static final ComponentType type = ComponentType.OPTION;
   private char index;
   public Option(Content c){
      super(c);
      super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
   }

   public Option(Content c, char index) {
      super(c);
      super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
      super.getAttributes().add(Attribute.builder().key("index").value(index+"").build());
      this.index = index;
   }

   

   @Override
   public void restore(){
       for(Attribute attr : super.getAttributes()){
           if( attr.getKey().equals("index")){
               this.index = attr.getValue().charAt(0);
           }
       }
   }
}
