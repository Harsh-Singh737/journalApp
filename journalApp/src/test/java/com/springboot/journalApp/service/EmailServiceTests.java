package com.springboot.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    void testSendMail(){
        emailService.sendMail("harshsinghlo737@gmail.com",
                "Testing java mail sender",
                "Hi, welcome to spring boot tutorial..");
    }
}
