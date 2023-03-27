package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.entity.Content;
import utcn.stackoverflow.stackoverflow.entity.Question;
import utcn.stackoverflow.stackoverflow.entity.User;
import utcn.stackoverflow.stackoverflow.repository.ContentRepository;
import utcn.stackoverflow.stackoverflow.repository.QuestionRepository;
import utcn.stackoverflow.stackoverflow.repository.TagRepository;
import utcn.stackoverflow.stackoverflow.repository.UserRepository;

import java.util.HashSet;
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
    @Autowired
    UserRepository userRepository;


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

    public Question saveQuestion(Long userId, String title, String description) {
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            return null;
        } else {
            User foundUser = user.get();

            Content toSaveContent = new Content();
            toSaveContent.setUser(foundUser);
            toSaveContent.setDescription(description);

            foundUser.addContent(toSaveContent);
            userRepository.save(foundUser);

            Question questionToSave = new Question();
            questionToSave.setTitle(title);
            questionToSave.setContent(toSaveContent);
            questionToSave.setTags(new HashSet<>()); // empty set of tags

            contentRepository.save(toSaveContent); // save the content entity before the question entity

            return questionRepository.save(questionToSave);
        }
    }
}
