package utcn.stackoverflow.stackoverflow.controller;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utcn.stackoverflow.stackoverflow.dto.AddAnswerRequest;
import utcn.stackoverflow.stackoverflow.dto.AnswerDTO;
import utcn.stackoverflow.stackoverflow.dto.UpdateAnswerRequest;
import utcn.stackoverflow.stackoverflow.dto.VoteAnswerRequest;
import utcn.stackoverflow.stackoverflow.service.AnswerService;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<AnswerDTO> retrieveAnswers() {
        return answerService.retrieveAnswers();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public AnswerDTO getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    @Transactional
    public long deleteAnswerById(@PathVariable Long id) {
        return answerService.deleteAnswerById(id);
    }

    @PostMapping("/addAnswer")
    @ResponseBody
    public AnswerDTO addAnswer(@RequestBody AddAnswerRequest answer) {
        return answerService.saveAnswer(answer.getQuestionId(), answer.getUserId(), answer.getDescription(), answer.getPicture());
    }

    @PostMapping("/updateAnswer")
    @ResponseBody
    public AnswerDTO updateAnswer(@RequestBody UpdateAnswerRequest answer) {
        return answerService.updateAnswer(answer.getAnswerId(), answer.getDescription(), answer.getPicture());
    }

    @GetMapping("/getAllByQuestionId/{questionId}")
    @ResponseBody
    public List<AnswerDTO> retrieveAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.retrieveAnswersByQuestionId(questionId);
    }

    @PatchMapping("/voteAnswer")
    @ResponseBody
    public AnswerDTO voteAnswer(@RequestBody VoteAnswerRequest voteRequest) {
        return answerService.voteAnswer(voteRequest.getUserId(), voteRequest.getAnswerId(), voteRequest.getValue());
    }
}
