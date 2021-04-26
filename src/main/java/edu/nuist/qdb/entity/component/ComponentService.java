package edu.nuist.qdb.entity.component;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nuist.qdb.entity.base.Attribute;
import edu.nuist.qdb.entity.base.AttributeReps;
import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.entity.base.ContentService;
import edu.nuist.qdb.entity.component.impl.Analysis;
import edu.nuist.qdb.entity.component.impl.Answer;
import edu.nuist.qdb.entity.component.impl.AnswerBlank;
import edu.nuist.qdb.entity.component.impl.AnswerBlankGroup;
import edu.nuist.qdb.entity.component.impl.AnswerJudge;
import edu.nuist.qdb.entity.component.impl.AnswerOption;
import edu.nuist.qdb.entity.component.impl.AnswerOptionGroup;
import edu.nuist.qdb.entity.component.impl.Option;
import edu.nuist.qdb.entity.component.impl.Stem;

/**
 * 只实现了查找，保存和删除，如果要更新就删除原来的题，再重新导入
 * Component分为二种，一种PLAIN COMPONENT,只包括CONTENT和相关属性，另一种是组合COMPONENT，组合COMPNENT没有CONTENT，只有子
 * 组件LIST和相关属性
 */

@Service
public class ComponentService {
    public static final String SUB_COMPONENT_ATTR_KEY = "subid";
    @Autowired
    private ComponentReps compReps;

    @Autowired
    private AttributeReps attrReps;

    @Autowired
    private ContentService cService;

    @Transactional
    public void deleteById(long id){
        Component comp = compReps.findById(id);
        if( comp == null ) return;
        ComponentType type = comp.getType();
        Content c = cService.findByRelationId(comp.getId());
        List<Attribute> attributes = attrReps.findByRelationId(comp.getId());
        switch( type ){
            case STEM:
            case ANALYSIS:
            case ANSWER:
            case ANSWERJUDGE:
            case ANSWEROPTION:
            case ANSWERBLANK:
            case OPTION:
                deletePlainComponent( c , attributes, type);
                break;
            case ANSWERBLANKGROUP:
            case ANSWEROPTIONGROUP:
                deleteGroupComponent(attributes, type);
                break;
        }
        compReps.delete(comp);
    }

    @Transactional
    public void deleteGroupComponent(List<Attribute> attributes, ComponentType type){
        for(Attribute attr : attributes){
            if( attr.getKey().equals(SUB_COMPONENT_ATTR_KEY)){ //删除所有子COMPONENT
                deleteById( Long.valueOf(attr.getValue()));
            }
        }
        for(Attribute attr : attributes){ //删除属性
            attrReps.delete(attr);
        }

    }

    @Transactional
    public void deletePlainComponent(Content c, List<Attribute> attributes, ComponentType type){
        cService.deleteById(c.getId());
        for(Attribute attr : attributes){
            attrReps.delete(attr);
        }
    }

    public Component findById(long id){
        Component comp = compReps.findById(id);
        Component rst = getComponent(comp);
        return rst;
    }

    public List<Component> findByRelId(long relId) {
        List<Component> components = compReps.findByRelId(relId);

        List<Component> rsts = new LinkedList<>();
        for (Component component: components) {
            Component rst = getComponent(component);
            rsts.add(rst);
        }

        return rsts;
    }

    public Component assimbleGroupComponent(List<Attribute> attributes, ComponentType type){
        //取得所有的子COMPONENT，组合COMPONENT没有CONTENT项，但是会有自己的属性
        List<Long> subIds = new LinkedList<Long>();
        for(Attribute attr : attributes){
            if( attr.getKey().equals(SUB_COMPONENT_ATTR_KEY)){
                subIds.add( Long.valueOf(attr.getValue()));
            }
        }

        //根据子COMPONENT ID，查找到后再获取
        List<Component> components = new LinkedList<Component>();
        for(Long subId : subIds){
            components.add(findById(subId) );
        }

        Component rst = null;
        //根据不同的组合COMPONENT类型，组装
        switch( type ){
            case ANSWERBLANKGROUP:{
                    List<AnswerBlank> blanks = new LinkedList<AnswerBlank>();
                    for(Component component : components){
                        blanks.add( (AnswerBlank)component );
                    }
                    AnswerBlankGroup blankGroup = new AnswerBlankGroup();
                    blankGroup.setBlanks(blanks);
                    rst = blankGroup;
                }
                break;
            case ANSWEROPTIONGROUP:
                List<AnswerOption> options = new LinkedList<AnswerOption>();
                for(Component component : components){
                    options.add( (AnswerOption)component );
                }
                AnswerOptionGroup optionGroup = new AnswerOptionGroup();
                optionGroup.setGroup(options);
                rst = optionGroup;
                break;
            
        }
        if( rst != null ){
            rst.setAttributes(attributes);
            rst.restore();
        }
        
        return rst;
    }

    public Component assimblePlainComponent(Content c, List<Attribute> attributes, ComponentType type){
        Component rst = null;
        switch( type ){
            case STEM:
                rst = Stem.builder().attributes(attributes).c(c).build();
                break;
            case ANALYSIS:
                rst = Analysis.builder().attributes(attributes).c(c).build();
                break;
            case ANSWER:
                rst = Answer.builder().attributes(attributes).c(c).build();
                break;
            case ANSWERJUDGE:
                rst = AnswerJudge.builder().attributes(attributes).c(c).build();
                break;
            case ANSWEROPTION:
                rst = AnswerOption.builder().attributes(attributes).c(c).build();
                break;
            case ANSWERBLANK:
                rst = AnswerBlank.builder().attributes(attributes).c(c).build();
                break;
            case OPTION:
                rst = Option.builder().attributes(attributes).c(c).build();
                break;
        }
        if( rst != null ) rst.restore(); //使用attribute属性恢复属性值
        return rst;
    }

    @Transactional
    public long save(Component c){
        ComponentType type = c.getType();
        long rst = -1l;
        switch( type ){
            case STEM:
            case ANALYSIS:
            case ANSWER:
            case ANSWERJUDGE:
            case ANSWEROPTION:
            case ANSWERBLANK:
            case OPTION:
                rst = savePlainComponent(c);
                break;
            case ANSWERBLANKGROUP:
            case ANSWEROPTIONGROUP:
                rst = saveGroupComponent(c);
                break;
        }
        return rst;
    }

    public long saveGroupComponent(Component c){
        c = compReps.save(c);
        List<Component> components = c.getComponents();
        List<Attribute> attributes = new LinkedList<Attribute>();
        for(Component comp : components){
            long compId = savePlainComponent( comp );
            attributes.add(Attribute.builder().relationId(c.getId()).key(SUB_COMPONENT_ATTR_KEY).value(compId+"").build());
        }

        c.getAttributes().addAll(attributes);

        for(Attribute a : c.getAttributes()){
            a.setRelationId(c.getId());
            attrReps.save(a);
        }
        return c.getId();
    }

    public long savePlainComponent(Component c){
        c = compReps.save(c);
        c.getC().setRelationId(c.getId());
        cService.save(c.getC());
        for(Attribute a : c.getAttributes()){
            a.setRelationId(c.getId());
            attrReps.save(a);
        }
        return c.getId();
    }

    /**
     * 组装component
     */
    private Component getComponent(Component component) {
        ComponentType type = component.getType();
        Content c = cService.findByRelationId(component.getId());
        List<Attribute> attributes = attrReps.findByRelationId(component.getId());

        Component rst = null;
        switch (type) {
            case STEM:
            case ANALYSIS:
            case ANSWER:
            case ANSWERJUDGE:
            case ANSWEROPTION:
            case ANSWERBLANK:
            case OPTION:
                rst = assimblePlainComponent(c, attributes, type);
                break;
            case ANSWERBLANKGROUP:
            case ANSWEROPTIONGROUP:
                rst = assimbleGroupComponent(attributes, type);
                break;
        }
        return rst;
    }
}
