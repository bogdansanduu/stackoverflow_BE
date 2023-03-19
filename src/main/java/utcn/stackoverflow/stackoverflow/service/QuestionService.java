package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.entity.Content;
import utcn.stackoverflow.stackoverflow.entity.Question;
import utcn.stackoverflow.stackoverflow.entity.Tag;
import utcn.stackoverflow.stackoverflow.repository.ContentRepository;
import utcn.stackoverflow.stackoverflow.repository.QuestionRepository;
import utcn.stackoverflow.stackoverflow.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ContentRepository contentRepository;

    @Autowired
    TagRepository tagRepository;

    public List<Question> retrieveQuestions() {
        return (List<Question>) questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        return question.orElse(null);
    }

    public long deleteQuestionById(Long id) {
        return questionRepository.deleteByQuestionId(id);
    }

    public Question saveQuestion(Question question) {
        contentRepository.save(question.getContent());
        return questionRepository.save(question);
    }
}
