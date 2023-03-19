package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.entity.Question;
import utcn.stackoverflow.stackoverflow.entity.Tag;
import utcn.stackoverflow.stackoverflow.repository.QuestionRepository;
import utcn.stackoverflow.stackoverflow.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    TagRepository tagRepository;

    public Tag addTagToQuestion(Long questionId, Tag tag) {
        Tag addedTag = this.addTag(tag);

        Optional<Question> foundQuestion = questionRepository.findById(questionId);

        if (foundQuestion.isEmpty())
            return null;
        else {
            foundQuestion.get().addTag(addedTag);
            questionRepository.save(foundQuestion.get());
        }

        return addedTag;
    }

    public Tag addTag(Tag tag) {
        Tag foundTag = tagRepository.findByName(tag.getName());

        if (foundTag != null) {
            return foundTag;
        }

        return tagRepository.save(tag);
    }

    public List<Tag> retrieveTags() {
        return (List<Tag>) tagRepository.findAll();
    }
}
