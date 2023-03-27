package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.entity.Content;
import utcn.stackoverflow.stackoverflow.entity.User;
import utcn.stackoverflow.stackoverflow.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> retrieveUsers() {
        List<User> users = (List<User>) userRepository.findAll();

        if (users.isEmpty()) {
            return null;
        }

        return users.stream().map(user -> new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName())).toList();
    }

    public UserDTO getUserById(Long cnp) {
        Optional<User> user = userRepository.findByUserId(cnp);

        if (user.isPresent()) {
            User userPresent = user.get();

            return new UserDTO(userPresent.getUserId(), userPresent.getFirstName(), userPresent.getLastName());
        } else {
            return null;
        }
    }

    public UserDTO findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            User userPresent = user.get();

            return new UserDTO(userPresent.getUserId(), userPresent.getFirstName(), userPresent.getLastName());
        } else {
            return null;
        }
    }

    public long deleteUserById(Long id) {
        Optional<User> user = userRepository.findByUserId(id);

        if (user.isPresent()) {
            User userFound = user.get();
            List<Content> contentList = new ArrayList<>(userFound.getContentList());

            for (Content content : contentList) {
                userFound.removeContent(content);
            }
            return userRepository.deleteByUserId(id);
        } else
            return -1;
    }

    public User saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Optional<User> foundUser = userRepository.findByUserId(user.getUserId());

        if (foundUser.isPresent()) {
            User myUpdatedUser = foundUser.get();

            myUpdatedUser.setFirstName(user.getFirstName());
            myUpdatedUser.setFirstName(user.getLastName());
            return userRepository.save(myUpdatedUser);
        }
        return null;
    }
}
