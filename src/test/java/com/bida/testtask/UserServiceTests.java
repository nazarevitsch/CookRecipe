package com.bida.testtask;

import com.bida.testtask.domain.User;
import com.bida.testtask.service.UserService;
import com.bida.testtask.service.dto.UserRegistrationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    void addUserAndFindThisUser() {
        UserRegistrationDTO testUser = new UserRegistrationDTO();
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("test1234567890");
        testUser.setName("Test");
        User savedUser = userService.save(testUser);
        User resultUser = userService.findUserByEmail("test@gmail.com");
        Assertions.assertEquals(testUser.getUser(), resultUser);
        Assertions.assertEquals(testUser.getUser(), savedUser);
    }

    @Test
    void passwordAndEmailValidation(){
        User testUserAcceptable = new User();
        testUserAcceptable.setEmail("test@gmail.com");
        testUserAcceptable.setPassword("test1234567890");
        User testUserUnacceptable = new User();
        testUserUnacceptable.setEmail("tes t@gmail.com");
        testUserUnacceptable.setPassword("test12 34567890");
        Assertions.assertFalse(userService.checkAcceptableUserData(testUserUnacceptable));
    }
}
