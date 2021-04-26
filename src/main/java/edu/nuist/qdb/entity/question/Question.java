package edu.nuist.qdb.entity.question;

import java.util.List;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentType;
import edu.nuist.qdb.entity.component.impl.QuestionType;
import edu.nuist.qdb.exception.NullQuestionException;
import edu.nuist.qdb.exception.NullStemException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private QuestionTypeEnum type;

    @Transient
    private List<Component> components;

    private String code;

    public Question(Question q){
        this.code = q.getCode();
        this.type = q.getType();
        this.components = q.getComponents();
    }



}





