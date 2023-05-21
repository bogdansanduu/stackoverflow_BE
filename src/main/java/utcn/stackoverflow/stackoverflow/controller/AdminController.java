package utcn.stackoverflow.stackoverflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.entity.User;
import utcn.stackoverflow.stackoverflow.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addUser")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return adminService.saveUser(user);
    }

    @PatchMapping("/banUser/{userId}")
    @ResponseBody
    public UserDTO banUser(@PathVariable Long userId) {
        return adminService.banUser(userId);
    }
}
