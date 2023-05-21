package utcn.stackoverflow.stackoverflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utcn.stackoverflow.stackoverflow.dto.VoteDto;
import utcn.stackoverflow.stackoverflow.service.VotesService;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    VotesService votesService;

    @GetMapping("/getVote")
    @ResponseBody
    public VoteDto getVote(@RequestParam Long userId, @RequestParam Long contentId) {
        return votesService.getVote(userId, contentId);
    }

}
