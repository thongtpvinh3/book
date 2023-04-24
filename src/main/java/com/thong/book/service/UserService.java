package com.thong.book.service;

import com.thong.book.entities.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User createAdmin(User user);

    List<User> findAll();

    User findById(long id);

    User update(long id, User user);

}
