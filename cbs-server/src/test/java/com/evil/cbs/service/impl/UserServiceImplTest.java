package com.evil.cbs.service.impl;

import com.evil.cbs.domain.User;
import com.evil.cbs.repository.UserRepository;
import com.evil.cbs.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private User firstUser;
    private User secondUser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        firstUser = User.builder()
                .username("Hector")
                .firstName("Hector")
                .lastName("Hector")
                .email("hector@gmail.com")
                .role("ROLE_USER")
                .telephoneNumber("0(69)-373-373")
                .enabled(1)
                .password("123")
                .build();
        secondUser = User.builder()
                .username("Hector")
                .firstName("Bob")
                .lastName("Bob")
                .email("bob@gmail.com")
                .role("ROLE_ADMIN")
                .telephoneNumber("0(69)-373-373")
                .enabled(1)
                .password("123")
                .build();
        when(userRepository.findByUsername("Hector")).thenReturn(Optional.of(firstUser));
        when(userRepository.findUserByEmail("bob@gmail.com")).thenReturn(Optional.of(secondUser));
        when(userRepository.existsByEmail("bob@gmail.com")).thenReturn(true);
    }


    @Test
    public void testFindUserByUsername() {
        User hector = userService.findUserByUsername("Hector");
        verify(userRepository).findByUsername("Hector");
        assertThat(hector).isEqualTo(firstUser);
    }

    @Test
    public void testFindUserByEmail() {
        User bob = userService.findUserByEmail("bob@gmail.com");
        verify(userRepository).findUserByEmail("bob@gmail.com");
        assertThat(bob).isEqualTo(secondUser);
    }

    @Test
    public void testExistsByEmail() {
        Boolean existsByEmail = userService.existsByEmail("bob@gmail.com");
        verify(userRepository).existsByEmail("bob@gmail.com");
        assertThat(existsByEmail).isTrue();
    }

}