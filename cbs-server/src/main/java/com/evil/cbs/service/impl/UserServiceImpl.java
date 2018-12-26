package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Ticket;
import com.evil.cbs.domain.User;
import com.evil.cbs.repository.UserRepository;
import com.evil.cbs.service.UserService;
import com.evil.cbs.service.util.UserRole;
import com.evil.cbs.service.util.UserState;
import com.evil.cbs.web.form.RegisterUserFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(RegisterUserFormBean registerUserFormBean) {
        User u = User.UserBuilder.anUser()
                .firstName(registerUserFormBean.getFirstName())
                .lastName(registerUserFormBean.getLastName())
                .email(registerUserFormBean.getEmail())
                .password(passwordEncoder.encode(registerUserFormBean.getUserPassword()))
                .telephoneNumber(registerUserFormBean.getTelephoneNumber())
                .role(UserRole.USER_ROLE.getValue())
                .build();

        userRepository.save(u);
        return u;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void enableUser(String email) {
        userRepository.updateEnabledValue(email, UserState.ENABLED.getValue());
    }

    @Transactional
    @Override
    public void disableUser(String email) {
        userRepository.updateEnabledValue(email, UserState.DISABLED.getValue());
    }

}
