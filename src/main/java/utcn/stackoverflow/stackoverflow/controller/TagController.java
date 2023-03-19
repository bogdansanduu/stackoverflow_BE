package utcn.stackoverflow.stackoverflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utcn.stackoverflow.stackoverflow.dto.TagRequest;
import utcn.stackoverflow.stackoverflow.entity.Tag;
import utcn.stackoverflow.stackoverflow.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Tag> retrieveTags() {
        return tagService.retrieveTags();
    }

    @PostMapping("/addTag")
    @ResponseBody
    public Tag addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @PostMapping("/addTagToQuestion")
    @ResponseBody
    public Tag addTagToQuestion(@RequestBody TagRequest tagRequest){
        return tagService.addTagToQuestion(tagRequest.getQuestionId(), tagRequest.getTag());
    }
}
