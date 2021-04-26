package edu.nuist.qdb;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.nuist.qdb.entity.base.Content;
import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentService;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.Analysis;
import edu.nuist.qdb.entity.component.impl.Answer;
import edu.nuist.qdb.entity.component.impl.Option;
import edu.nuist.qdb.entity.component.impl.Stem;
import edu.nuist.qdb.xlsutil.Cell;
import edu.nuist.qdb.xlsutil.XLSReader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestComponent {
    @Autowired
    private ComponentService cService;

    @Test
    public void save(){
        XLSReader rr = new XLSReader("d:/test.xls");
        try {
            List<List<Cell>> rows = rr.read();
            for(List<Cell> row : rows){
                for(Cell c : row){
                    Component component = cell2Component(c) ;
                    if(component != null) {
                        System.out.println(component);

                        cService.save(
                                Component.builder()
                                        .c(component.getC())
                                        .type(component.getType().getName())
                                        .attributes(component.getAttributes())
                                        .build()
                        );
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void read(){
        System.out.println(cService.findById(6));
        System.out.println(cService.findById(7));
    }

    //@Test
    public void delete(){
        cService.deleteById(2);
    }

    public Component cell2Component(Cell c){
        Content content = new Content(c.getDatas());
        if( ComponentType.STEM.getName().equals(c.getHead())){
           Component s = new Stem(content);
           return s;
        }else if( ComponentType.ANALYSIS.getName().equals(c.getHead())){
            Component s = new Analysis(content);
            return s;
        }else if( c.getHead().startsWith(ComponentType.OPTION.getName())){
           char index = c.getHead().charAt(c.getHead().length()-1);
           Component s = new Option(content, index);
           return s;
        }else if( ComponentType.ANSWER.getName().equals(c.getHead())){
            Component s = new Answer(content);
            return s;
        }

        return null;
    }
}
