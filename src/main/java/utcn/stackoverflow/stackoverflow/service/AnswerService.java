package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.entity.Answer;
import utcn.stackoverflow.stackoverflow.entity.Content;
import utcn.stackoverflow.stackoverflow.entity.User;
import utcn.stackoverflow.stackoverflow.repository.AnswerRepository;
import utcn.stackoverflow.stackoverflow.repository.ContentRepository;
import utcn.stackoverflow.stackoverflow.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    ContentRepository contentRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;

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

    public Answer saveAnswer(Long questionId, Long userId, String description) {
        Content toSaveContent = new Content();

        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            return null;
        } else {
            User foundUser = user.get();

            toSaveContent.setUser(foundUser);
            toSaveContent.setDescription(description);

            foundUser.addContent(toSaveContent);
            userRepository.save(foundUser);
            contentRepository.save(toSaveContent);

            Answer answerToSave = new Answer();
            answerToSave.setQuestionId(questionId);
            answerToSave.setContent(toSaveContent);

            return answerRepository.save(answerToSave);
        }
    }
}
