package com.evil.cbs.service.impl;

import com.evil.cbs.domain.User;
import com.evil.cbs.domain.UserRole;
import com.evil.cbs.domain.common.UserNotFoundException;
import com.evil.cbs.repository.UserRepository;
import com.evil.cbs.service.UserService;
import com.evil.cbs.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(UserDTO userDTO) {
        User u = User.builder()
                .username(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .telephoneNumber(userDTO.getTelephoneNumber())
                .role(UserRole.USER.getValue())
                .build();
        userRepository.save(u);
        return u;
    }

    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
