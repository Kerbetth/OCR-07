package com.nnk.springboot.integration;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CreateAccountTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserController userController;

    private User user;

    BindingResult result = mock(BindingResult.class);
    @BeforeEach
    public void setup() {
        user = new User();
        when(result.hasErrors()).thenReturn(false);
        userRepository.deleteAll();
    }

    @Test
    public void addAUserAccountSuccessfully() {
        // Given

        user.setId(1);
        user.setBrutePassword("StrongPass0!");
        user.setFullname("A User");
        user.setUsername("A User");
        user.setRole("USER");

        // When
        userController.validate(user, result);

        // Then
        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    public void createGoodAccount() {
        // Given
        user.setId(1);
        user.setBrutePassword("StrongPass0!");
        user.setFullname("A User");
        user.setUsername("A User");
        user.setRole("USER");

        // When
        userController.updateUser(1,user, result);

        // Then
        assertThat(userRepository.findAll()).hasSize(1);
    }
    @Test

    public void deleteAccount() {
        // Given
        user.setId(1);
        user.setBrutePassword("StrongPass0!");
        user.setFullname("A User");
        user.setUsername("A User");
        user.setRole("USER");

        // When
        userController.validate(user, result);
        userController.deleteUser(1);
        // Then
        assertThat(userRepository.findAll()).hasSize(0);
    }
}
