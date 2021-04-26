package edu.nuist.qdb.entity.question;

import edu.nuist.qdb.entity.component.Component;
import edu.nuist.qdb.entity.component.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionReps questionReps;

    @Autowired
    private ComponentService componentService;

    public Question save(Question question) {
        Question q = questionReps.save(question);
        long id = q.getId();

        List<Component> components = question.getComponents();
        components.forEach(component -> {
            componentService.save(
                    Component.builder()
                            .c(component.getC())
                            .type(component.getType().getName())
                            .relId(id)
                            .attributes(component.getAttributes())
                            .build()
            );
        });

        q.setComponents(components);
        return q;
    }

    public List<Question> findAll() {
        List<Question> questions = questionReps.findAll();
        for (Question question: questions) {
            long relId = question.getId();
            List<Component> components = componentService.findByRelId(relId);
            question.setComponents(components);
        }

        return questions;
    }
}
