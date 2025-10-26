package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Disabled
    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Harsh").password("Iijklgbll").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("Harsh");
        Assertions.assertNotNull(user);
    }
}
