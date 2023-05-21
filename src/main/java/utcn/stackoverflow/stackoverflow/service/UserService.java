package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.entity.Content;
import utcn.stackoverflow.stackoverflow.entity.User;
import utcn.stackoverflow.stackoverflow.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> retrieveUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

        if (users.isEmpty()) {
            return null;
        }

        return users.stream().map(user -> new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getScore(), user.getRole(), user.isBanned())).toList();
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findByUserId(id);

        if (user == null) {
            return null;
        }

        return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getScore(), user.getRole(), user.isBanned());
    }

    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getScore(), user.getRole(), user.isBanned());
    }

    public long deleteUserById(Long id) {
        User user = userRepository.findByUserId(id);

        if (user == null) {
            return -1;
        }

        List<Content> contentList = new ArrayList<>(user.getContentList());

        for (Content content : contentList) {
            user.removeContent(content);
        }

        return userRepository.deleteByUserId(id);
    }

    public User updateUser(Long id) {
        User user = userRepository.findByUserId(id);

        if (user == null) {
            return null;
        }

        user.setFirstName(user.getFirstName());
        user.setFirstName(user.getLastName());
        return userRepository.save(user);
    }
}
