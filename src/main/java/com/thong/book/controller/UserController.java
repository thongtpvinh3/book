package com.thong.book.controller;

import com.thong.book.entities.User;
import com.thong.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/create-admin")
    public User createAdmin(@RequestBody User user) {
        return userService.createAdmin(user);
    }

    @GetMapping("/find-all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PatchMapping("/update/{id}")
    public User update(@PathVariable long id, @RequestBody User user) {
        return userService.update(id, user);
    }

}
