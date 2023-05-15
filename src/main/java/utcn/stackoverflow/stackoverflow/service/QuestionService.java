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
        List<Question> questions = (List<Question>) questionRepository.findAll();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            Long voteCount = votesRepository.getVotesValue(question.getContent().getContentId());

            if (voteCount == null)
                voteCount = (long) 0;

            User user = question.getContent().getUser();
            UserDTO userDTO = new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName());
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
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            return null;
        }

        User foundUser = user.get();

        //define content
        Content toSaveContent = new Content();
        toSaveContent.setUser(foundUser);
        toSaveContent.setDescription(description);
        toSaveContent.setPicture(picture);

        foundUser.addContent(toSaveContent);
        userRepository.save(foundUser);

        Question questionToSave = new Question();
        questionToSave.setTitle(title);
        questionToSave.setContent(toSaveContent);
        questionToSave.setTags(new HashSet<>()); // empty set of tags

        contentRepository.save(toSaveContent); // save the content entity before the question entity

        Question savedQuestion = questionRepository.save(questionToSave);

        UserDTO userDTO = new UserDTO(foundUser.getUserId(), foundUser.getFirstName(), foundUser.getLastName());

        return new QuestionDTO(savedQuestion.getQuestionId(), savedQuestion.getTitle(), savedQuestion.getContent(), savedQuestion.getTags(), userDTO, (long) 0);

    }

    public QuestionDTO updateQuestion(Long questionId, String title, String description, String picture) {
        Optional<Question> question = questionRepository.findById(questionId);

        if (question.isEmpty()) {
            return null;
        }

        Question foundQuestion = question.get();
        Content foundQuestionContent = foundQuestion.getContent();
        User foundQuestionUser = foundQuestionContent.getUser();

        foundQuestion.setTitle(title);
        foundQuestionContent.setDescription(description);
        foundQuestionContent.setPicture(picture);

        contentRepository.save(foundQuestionContent);
        Question savedQuestion = questionRepository.save(foundQuestion);

        UserDTO userDTO = new UserDTO(foundQuestionUser.getUserId(), foundQuestionUser.getFirstName(), foundQuestionUser.getLastName());

        Long voteCount = votesRepository.getVotesValue(foundQuestionContent.getContentId());

        return new QuestionDTO(savedQuestion.getQuestionId(), savedQuestion.getTitle(), savedQuestion.getContent(), savedQuestion.getTags(), userDTO, voteCount);
    }

    public Question voteQuestion(Long userId, Long questionId, int value) {
        Optional<Question> foundQuestionOptional = questionRepository.findById(questionId);
        Optional<User> foundUserOptional = userRepository.findById(userId);

        if (foundQuestionOptional.isEmpty() || foundUserOptional.isEmpty()) return null;

        Question foundQuestion = foundQuestionOptional.get();
        User voteUser = foundUserOptional.get();


        Vote vote = votesRepository.findById_UserIdAndId_ContentId(userId, foundQuestion.getContent().getContentId());

        if (vote == null) {
            voteUser.addVoteQuestion(foundQuestion.getContent(), value);

            Question savedQuestion = questionRepository.save(foundQuestion);
            User questionCreator = savedQuestion.getContent().getUser();
            UserDTO userDTO = new UserDTO(questionCreator.getUserId(), questionCreator.getFirstName(), questionCreator.getLastName());

            return savedQuestion;
        } else {
            vote.setValue(value);
            votesRepository.save(vote);

            return questionRepository.findByQuestionId(questionId);
        }
    }

}
