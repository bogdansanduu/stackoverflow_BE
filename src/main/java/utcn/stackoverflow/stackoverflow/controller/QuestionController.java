package utcn.stackoverflow.stackoverflow.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utcn.stackoverflow.stackoverflow.dto.AddQuestionRequest;
import utcn.stackoverflow.stackoverflow.dto.QuestionDTO;
import utcn.stackoverflow.stackoverflow.dto.UpdateQuestionRequest;
import utcn.stackoverflow.stackoverflow.dto.VoteQuestionRequest;
import utcn.stackoverflow.stackoverflow.entity.Question;
import utcn.stackoverflow.stackoverflow.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<QuestionDTO> retrieveQuestions() {
        return questionService.retrieveQuestions();
    }


    @GetMapping("/getById/{id}")
    @ResponseBody
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    @Transactional
    public long deleteQuestionById(@PathVariable Long id) {
        return questionService.deleteQuestionById(id);
    }

    @PostMapping("/addQuestion")
    @ResponseBody
    public QuestionDTO addQuestion(@RequestBody AddQuestionRequest question) {
        return questionService.saveQuestion(question.getUserId(), question.getTitle(), question.getDescription(), question.getPicture());
    }

    @PostMapping("/updateQuestion")
    @ResponseBody
    public QuestionDTO updateQuestion(@RequestBody UpdateQuestionRequest question) {
        return questionService.updateQuestion(question.getQuestionId(), question.getTitle(), question.getDescription(),question.getPicture());
    }

    @PatchMapping("/voteQuestion")
    @ResponseBody
    public QuestionDTO voteQuestion(@RequestBody VoteQuestionRequest voteRequest) {
        return questionService.voteQuestion(voteRequest.getUserId(), voteRequest.getQuestionId(), voteRequest.getValue());
    }

}
