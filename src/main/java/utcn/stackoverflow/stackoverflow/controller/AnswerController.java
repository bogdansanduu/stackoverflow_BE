package utcn.stackoverflow.stackoverflow.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utcn.stackoverflow.stackoverflow.entity.Answer;
import utcn.stackoverflow.stackoverflow.service.AnswerService;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Answer> retrieveAnswers() {
        return answerService.retrieveAnswers();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public Answer getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public long deleteAnswerById(@PathVariable Long id) {
        return answerService.deleteAnswerById(id);
    }

    @PostMapping("/addAnswer")
    @ResponseBody
    public Answer addAnswer(@RequestBody Answer answer) {
        return answerService.saveAnswer(answer);
    }

    @PostMapping("/updateAnswer")
    @ResponseBody
    public Answer updateAnswer(@RequestBody Answer answer) {
        return answerService.saveAnswer(answer);
    }

}
