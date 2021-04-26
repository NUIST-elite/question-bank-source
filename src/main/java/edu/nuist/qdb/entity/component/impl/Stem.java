package edu.nuist.qdb.entity.component.impl;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.Content;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
public class Stem extends Component{
   public static final ComponentType type = ComponentType.STEM;
   
   public Stem(Content c){
      super(c);
      super.getAttributes().add(Attribute.builder().key("type").value(type.getName()).build());
   }
}