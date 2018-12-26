package com.evil.cbs.service;

import com.evil.cbs.domain.Ticket;
import com.evil.cbs.domain.User;
import com.evil.cbs.web.form.RegisterUserFormBean;

import java.util.List;

public interface UserService {

    User saveUser(RegisterUserFormBean registerUserFormBean);

    User findUserByEmail(String email);

    List<User> findAll();

    void enableUser(String email);

    void disableUser(String email);

}
