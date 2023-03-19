package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.entity.User;
import utcn.stackoverflow.stackoverflow.repository.UserRepository;

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

        return users.stream().map(user -> new UserDTO(user.getFirstName(), user.getLastName())).toList();
    }

    public UserDTO getUserById(Long cnp) {
        Optional<User> user = userRepository.findByUserId(cnp);

        if (user.isPresent()) {
            return new UserDTO(user.get().getFirstName(), user.get().getLastName());
        } else {
            return null;
        }
    }

    public UserDTO findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return new UserDTO(user.get().getFirstName(), user.get().getLastName());
        } else {
            return null;
        }
    }

    public long deleteUserById(Long id) {
        return userRepository.deleteByUserId(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
