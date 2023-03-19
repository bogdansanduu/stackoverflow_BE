package utcn.stackoverflow.stackoverflow.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public List<Question> retrieveQuestions() {
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
    public Question addQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @PostMapping("/updateQuestion")
    @ResponseBody
    public Question updateQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

}
