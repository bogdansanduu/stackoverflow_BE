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

@Service
public class AnswerService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VotesRepository votesRepository;

    public List<AnswerDTO> retrieveAnswers() {
        List<Answer> answers = answerRepository.findAll();
        List<AnswerDTO> answerDTOS = new ArrayList<>();

        for (Answer answer : answers) {
            AnswerDTO answerDTO = mapAnswer(answer);
            answerDTOS.add(answerDTO);
        }

        return answerDTOS;
    }

    public AnswerDTO getAnswerById(Long id) {
        Answer answer = answerRepository.findByAnswerId(id);

        if (answer == null) {
            return null;
        }

        return mapAnswer(answer);
    }

    public long deleteAnswerById(Long id) {
        Answer answer = answerRepository.findByAnswerId(id);

        if (answer == null) {
            return -1;
        }

        long returnValue = answerRepository.deleteByAnswerId(id);

        Content answerContent = answer.getContent();
        contentRepository.deleteById(answerContent.getContentId());

        return returnValue;
    }

    public AnswerDTO saveAnswer(Long questionId, Long userId, String description, String picture) {
        User user = userRepository.findByUserId(userId);
        Question question = questionRepository.findByQuestionId(questionId);


        if (user == null || question == null) {
            return null;
        }

        Content toSaveContent = new Content();
        toSaveContent.setUser(user);
        toSaveContent.setDescription(description);
        toSaveContent.setPicture(picture);

        user.addContent(toSaveContent);
        userRepository.save(user);

        Answer answerToSave = new Answer();
        answerToSave.setQuestion(question);
        answerToSave.setContent(toSaveContent);

        contentRepository.save(toSaveContent);
        answerRepository.save(answerToSave);

        UserDTO userDTO = new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getScore(), user.getRole(), user.isBanned());

        return new AnswerDTO(answerToSave.getAnswerId(), answerToSave.getQuestion(), answerToSave.getContent(), userDTO, (long) 0);
    }

    public AnswerDTO updateAnswer(Long answerId, String description, String picture) {
        Answer answer = answerRepository.findByAnswerId(answerId);

        if (answer == null) {
            return null;
        }

        Content answerContent = answer.getContent();

        answerContent.setDescription(description);
        answerContent.setPicture(picture);

        contentRepository.save(answerContent);
        Answer savedAnswer = answerRepository.save(answer);

        User answerCreator = answerContent.getUser();
        UserDTO userDTO = new UserDTO(answerCreator.getUserId(), answerCreator.getFirstName(), answerCreator.getLastName(), answerCreator.getScore(), answerCreator.getRole(), answerCreator.isBanned());
        Long voteCount = votesRepository.getVotesValue(answerContent.getContentId());

        return new AnswerDTO(savedAnswer.getAnswerId(), savedAnswer.getQuestion(), savedAnswer.getContent(), userDTO, voteCount);
    }

    public List<AnswerDTO> retrieveAnswersByQuestionId(Long questionId) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        List<AnswerDTO> answerDTOS = new ArrayList<>();

        for (Answer answer : answers) {
            AnswerDTO answerDTO = mapAnswer(answer);
            answerDTOS.add(answerDTO);
        }

        Collections.sort(answerDTOS);

        return answerDTOS;
    }

    public AnswerDTO voteAnswer(Long userId, Long answerId, int value) {
        Answer answer = answerRepository.findByAnswerId(answerId);
        User voteUser = userRepository.findByUserId(userId);

        if (answer == null || voteUser == null) {
            return null;
        }

        User answerCreator = answer.getContent().getUser();

        Vote vote = votesRepository.findById_UserIdAndId_ContentId(userId, answer.getContent().getContentId());

        if (vote == null) {
            voteUser.addVoteAnswer(answer.getContent(), value);
            updateUserScore(answerCreator, value == 1 ? 5 : -2.5);
            voteUser.setScore(voteUser.getScore() - 1.5);
        } else {
            int previousValue = vote.getValue();
            vote.setValue(value);
            votesRepository.save(vote);

            double scoreChangeCreator = 0.0;
            double scoreChangeUser = 0.0;

            switch (previousValue) {
                case -1 -> {
                    switch (value) {
                        case 1 -> {
                            scoreChangeCreator = 7.5;
                            scoreChangeUser = 1.5;
                        }
                        case 0 -> {
                            scoreChangeCreator = 2.5;
                            scoreChangeUser = 1.5;
                        }
                    }
                }
                case 1 -> {
                    switch (value) {
                        case -1 -> {
                            scoreChangeCreator = -7.5;
                            scoreChangeUser = -1.5;
                        }
                        case 0 -> {
                            scoreChangeCreator = -5.0;
                            scoreChangeUser = 0.0;
                        }
                    }
                }
                case 0 -> {
                    switch (value) {
                        case 1 -> {
                            scoreChangeCreator = 5.0;
                            scoreChangeUser = 0.0;
                        }
                        case -1 -> {
                            scoreChangeCreator = -2.5;
                            scoreChangeUser = -1.5;
                        }
                    }
                }
            }

            updateUserScore(answerCreator, scoreChangeCreator);
            updateUserScore(voteUser, scoreChangeUser);
        }

        return mapAnswer(answer);
    }

    private AnswerDTO mapAnswer(Answer answer) {
        Long voteCount = votesRepository.getVotesValue(answer.getContent().getContentId());

        if (voteCount == null) {
            voteCount = 0L;
        }

        User user = answer.getContent().getUser();
        UserDTO userDTO = new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getScore(), user.getRole(), user.isBanned());

        return new AnswerDTO(answer.getAnswerId(), answer.getQuestion(), answer.getContent(), userDTO, voteCount);
    }

    private void updateUserScore(User answerCreator, double scoreChange) {
        answerCreator.setScore(answerCreator.getScore() + scoreChange);
        userRepository.save(answerCreator);
    }
}
