package utcn.stackoverflow.stackoverflow.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.entity.User;
import utcn.stackoverflow.stackoverflow.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<UserDTO> retrieveUsers() {
        return userService.retrieveUsers();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    @Transactional
    public long deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @PutMapping("/updateUser")
    @ResponseBody
    public User updateUser(@RequestBody Long id) {
        return userService.updateUser(id);
    }

}
