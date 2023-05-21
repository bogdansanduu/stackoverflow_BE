package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.dto.QuestionDTO;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.entity.*;
import utcn.stackoverflow.stackoverflow.repository.*;

import java.util.*;

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
    @Autowired
    VotesRepository votesRepository;
    @Autowired
    private AnswerRepository answerRepository;


    public List<QuestionDTO> retrieveQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            Long voteCount = votesRepository.getVotesValue(question.getContent().getContentId());

            if (voteCount == null) {
                voteCount = (long) 0;
            }

            User user = question.getContent().getUser();
            UserDTO userDTO = new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getScore(), user.getRole(), user.isBanned());

            QuestionDTO questionDTO = new QuestionDTO(question.getQuestionId(), question.getTitle(), question.getContent(), question.getTags(), userDTO, voteCount);
            questionDTOS.add(questionDTO);
        }

        Collections.sort(questionDTOS);

        return questionDTOS;
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        return question.orElse(null);
    }

    public long deleteQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        if (question.isEmpty()) {
            return -1;
        }

        Question foundQuestion = question.get();
        ArrayList<Answer> answers = new ArrayList<>(foundQuestion.getAnswers());

        for (Answer answer : answers) {
            Content answerContent = answer.getContent();

            answerRepository.deleteByAnswerId(answer.getAnswerId());
            contentRepository.deleteById(answerContent.getContentId());
        }

        long returnValue = questionRepository.deleteByQuestionId(id);

        Content questionContent = foundQuestion.getContent();
        contentRepository.deleteById(questionContent.getContentId());

        return returnValue;
    }

    public QuestionDTO saveQuestion(Long userId, String title, String description, String picture) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            return null;
        }

        //define content
        Content toSaveContent = new Content();
        toSaveContent.setUser(user);
        toSaveContent.setDescription(description);
        toSaveContent.setPicture(picture);

        user.addContent(toSaveContent);
        userRepository.save(user);

        Question questionToSave = new Question();
        questionToSave.setTitle(title);
        questionToSave.setContent(toSaveContent);
        questionToSave.setTags(new HashSet<>()); // empty set of tags

        contentRepository.save(toSaveContent); // save the content entity before the question entity

        Question savedQuestion = questionRepository.save(questionToSave);
        UserDTO userDTO = new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getScore(), user.getRole(), user.isBanned());

        return new QuestionDTO(savedQuestion.getQuestionId(), savedQuestion.getTitle(), savedQuestion.getContent(), savedQuestion.getTags(), userDTO, (long) 0);
    }

    public QuestionDTO updateQuestion(Long questionId, String title, String description, String picture) {
        Optional<Question> question = questionRepository.findById(questionId);

        if (question.isEmpty()) {
            return null;
        }

        //Get Question/Content/User
        Question foundQuestion = question.get();
        Content foundQuestionContent = foundQuestion.getContent();
        User foundQuestionCreator = foundQuestionContent.getUser();

        //Update Content
        foundQuestion.setTitle(title);
        foundQuestionContent.setDescription(description);
        foundQuestionContent.setPicture(picture);

        //Save Content and Questions
        contentRepository.save(foundQuestionContent);
        Question savedQuestion = questionRepository.save(foundQuestion);

        //Create QuestionDTO
        UserDTO userDTO = new UserDTO(foundQuestionCreator.getUserId(), foundQuestionCreator.getFirstName(), foundQuestionCreator.getLastName(), foundQuestionCreator.getScore(), foundQuestionCreator.getRole(), foundQuestionCreator.isBanned());
        Long voteCount = votesRepository.getVotesValue(foundQuestionContent.getContentId());

        return new QuestionDTO(savedQuestion.getQuestionId(), savedQuestion.getTitle(), savedQuestion.getContent(), savedQuestion.getTags(), userDTO, voteCount);
    }

    public QuestionDTO voteQuestion(Long userId, Long questionId, int value) {
        Question question = questionRepository.findByQuestionId(questionId);
        User user = userRepository.findByUserId(userId);

        if (question == null || user == null) {
            return null;
        }

        User questionCreator = question.getContent().getUser();

        Vote userVote = votesRepository.findById_UserIdAndId_ContentId(userId, question.getContent().getContentId());

        if (userVote == null) {
            user.addVoteQuestion(question.getContent(), value);
            updateUserScore(questionCreator, value == 1 ? 2.5 : -1.5);
        } else {
            int previousValue = userVote.getValue();
            userVote.setValue(value);
            votesRepository.save(userVote);

            double scoreChange = 0.0;

            switch (previousValue) {
                case -1 -> scoreChange = switch (value) {
                    case 1 -> 4.0;
                    case 0 -> 1.5;
                    default -> 0.0;
                };
                case 1 -> scoreChange = switch (value) {
                    case -1 -> -4.0;
                    case 0 -> -2.5;
                    default -> 0.0;
                };
                case 0 -> scoreChange = switch (value) {
                    case 1 -> 2.5;
                    case -1 -> -1.5;
                    default -> 0.0;
                };
            }

            updateUserScore(questionCreator, scoreChange);
        }

        Long voteCount = votesRepository.getVotesValue(question.getContent().getContentId());
        UserDTO questionCreatorDTO = new UserDTO(questionCreator.getUserId(), questionCreator.getFirstName(), questionCreator.getLastName(), questionCreator.getScore(), questionCreator.getRole(), questionCreator.isBanned());

        return new QuestionDTO(question.getQuestionId(), question.getTitle(), question.getContent(), question.getTags(), questionCreatorDTO, voteCount);
    }


    private void updateUserScore(User questionCreator, double scoreChange) {
        questionCreator.setScore(questionCreator.getScore() + scoreChange);
        userRepository.save(questionCreator);
    }
}

