package com.evil.cbs.service;

import com.evil.cbs.domain.User;
import com.evil.cbs.web.dto.UserDTO;

import java.util.List;

public interface UserService {

    User saveUser(UserDTO userDTO);

    User findUserByEmail(String email);

    List<User> findAll();

    User findById(Long id);

    User saveUser(User user);

    void deleteById(Long userId);
}