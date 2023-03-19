package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.entity.Answer;
import utcn.stackoverflow.stackoverflow.repository.AnswerRepository;
import utcn.stackoverflow.stackoverflow.repository.ContentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    ContentRepository contentRepository;
    @Autowired
    AnswerRepository answerRepository;

    public List<Answer> retrieveAnswers() {
        return (List<Answer>) answerRepository.findAll();
    }

    public Answer getAnswerById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);

        return answer.orElse(null);
    }

    public long deleteAnswerById(Long id) {
        return answerRepository.deleteByAnswerId(id);
    }

    public Answer saveAnswer(Answer answer) {
        contentRepository.save(answer.getContent());
        return answerRepository.save(answer);
    }
}
