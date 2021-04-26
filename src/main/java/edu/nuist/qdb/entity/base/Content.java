package edu.nuist.qdb.entity.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.nuist.qdb.xlsutil.XLSReader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Content")
public class Content {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    private long relationId;

    @Transient
    List<Fragment> fragments = null;

    public static Content getContent(String c){
        HashMap<String, String> datas = new HashMap<>();
        datas.put(XLSReader.TEXT, c);
        return new Content(datas);
    }

    public Content(HashMap<String, String> datas){
        List<Fragment> fragments = new LinkedList<Fragment>();
        for(Entry<String, String> entry : datas.entrySet()){
            Fragment f = Fragment.builder().build();
            if( entry.getKey().equals(XLSReader.TEXT)){
                f.setText(entry.getValue());
                
            }else if(entry.getKey().equals(XLSReader.PIC)){
                f.setSrc(entry.getValue());
            }
            fragments.add(f);
        }
        this.fragments = fragments;
    }
    
    public String html(){
        StringBuffer sb = new StringBuffer();
        for(Fragment f: fragments){
            sb.append( f.html() );
        }
        return sb.toString();
    }

    public String text(){   
        return text("");
    }
    
    public String text(String split){
        StringBuffer sb = new StringBuffer();
        for(Fragment f: fragments){
            if( f.getSrc() == null )
                sb.append( f.getText()  );
        }
        return sb.toString();
    }
}
