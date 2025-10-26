package com.springboot.journalApp.service;

import com.springboot.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;



    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "Harsh",
            "Singh",
            "new-admin"
    })
    public void testFindByUserName(String name){
        assertNotNull(userRepository.findByUserName(name));
    }


    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "6,9,69"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected,a+b);
    }
}
