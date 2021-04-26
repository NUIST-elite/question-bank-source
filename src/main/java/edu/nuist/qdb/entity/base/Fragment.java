package edu.nuist.qdb.entity.base;

import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "Fragment")
public class Fragment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    private String htmlTag;
    private String style;
    @Column(columnDefinition="TEXT")
    private String text; 
    @Lob  
    private String src; 
    private long cid;

    public static final String tpl = "<{0} class=\"{1}\" src=\"{3}\">{2}</{0}>";

    public String html(){
        return MessageFormat.format(tpl, this.htmlTag, this.style, this.text, this.src);
    }

    public String text(){
        return this.text;
    }
}
