package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.dto.AnswerDTO;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.entity.*;
import utcn.stackoverflow.stackoverflow.repository.*;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    private VotesRepository votesRepository;

    public List<Answer> retrieveAnswers() {
        return (List<Answer>) answerRepository.findAll();
    }

    public Answer getAnswerById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);

        return answer.orElse(null);
    }

    public long deleteAnswerById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);

        if (answer.isEmpty()) {
            return -1;
        }

        Answer foundAnswer = answer.get();

        long returnValue = answerRepository.deleteByAnswerId(id);

        Content answerContent = foundAnswer.getContent();
        contentRepository.deleteById(answerContent.getContentId());

        return returnValue;
    }

    public AnswerDTO saveAnswer(Long questionId, Long userId, String description, String picture) {
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            return null;
        }

        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            return null; // fail if the question is not found in the database
        }

        User foundUser = user.get();
        Question foundQuestion = question.get();

        Content toSaveContent = new Content();
        toSaveContent.setUser(foundUser);
        toSaveContent.setDescription(description);
        toSaveContent.setPicture(picture);

        foundUser.addContent(toSaveContent);
        userRepository.save(foundUser);

        Answer answerToSave = new Answer();
        answerToSave.setQuestion(foundQuestion);
        answerToSave.setContent(toSaveContent);

        contentRepository.save(toSaveContent);
        answerRepository.save(answerToSave);

        UserDTO userDTO = new UserDTO(foundUser.getUserId(), foundUser.getFirstName(), foundUser.getLastName());
        AnswerDTO answerDTO = new AnswerDTO(answerToSave.getAnswerId(), answerToSave.getQuestion(), answerToSave.getContent(), userDTO, (long) 0);

        return answerDTO;
    }

    public AnswerDTO updateAnswer(Long answerId, String description, String picture) {
        Optional<Answer> answer = answerRepository.findById(answerId);

        if (answer.isEmpty()) {
            return null;
        }

        Answer foundAnswer = answer.get();
        Content foundAnswerContent = foundAnswer.getContent();
        User foundAnswerUser = foundAnswerContent.getUser();

        foundAnswerContent.setDescription(description);
        foundAnswerContent.setPicture(picture);

        contentRepository.save(foundAnswerContent);
        Answer savedAnswer = answerRepository.save(foundAnswer);

        UserDTO userDTO = new UserDTO(foundAnswerUser.getUserId(), foundAnswerUser.getFirstName(), foundAnswerUser.getLastName());

        Long voteCount = votesRepository.getVotesValue(foundAnswerContent.getContentId());

        return new AnswerDTO(savedAnswer.getAnswerId(), savedAnswer.getQuestion(), savedAnswer.getContent(), userDTO, voteCount);
    }

    public List<AnswerDTO> retrieveAnswersByQuestionId(Long questionId) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        List<AnswerDTO> answerDTOS = new ArrayList<>();

        for (Answer answer : answers) {
            Long voteCount = votesRepository.getVotesValue(answer.getContent().getContentId());

            if (voteCount == null)
                voteCount = (long) 0;


            User user = answer.getContent().getUser();
            UserDTO userDTO = new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName());

            AnswerDTO answerDTO = new AnswerDTO(answer.getAnswerId(), answer.getQuestion(), answer.getContent(), userDTO, voteCount);

            answerDTOS.add(answerDTO);
        }

        Collections.sort(answerDTOS);

        return answerDTOS;
    }

    public Answer voteAnswer(Long userId, Long answerId, int value) {
        Optional<Answer> foundAnswerOptional = answerRepository.findById(answerId);
        Optional<User> foundUserOptional = userRepository.findById(userId);

        if (foundAnswerOptional.isEmpty() || foundUserOptional.isEmpty()) {
            return null;
        }

        Answer foundAnswer = foundAnswerOptional.get();
        User voteUser = foundUserOptional.get();

        Vote vote = votesRepository.findById_UserIdAndId_ContentId(userId, foundAnswer.getContent().getContentId());

        if (vote == null) {
            voteUser.addVoteAnswer(foundAnswer.getContent(), value);

            Answer savedAnswer = answerRepository.save(foundAnswer);
            User answerCreator = savedAnswer.getContent().getUser();
            UserDTO userDTO = new UserDTO(answerCreator.getUserId(), answerCreator.getFirstName(), answerCreator.getLastName());

            return savedAnswer;
        } else {
            vote.setValue(value);
            votesRepository.save(vote);

            return answerRepository.findByAnswerId(answerId);
        }
    }
}
