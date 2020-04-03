package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */


@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class UserTestIT {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mvc;

    private User user;

    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        user = new User();
        when(result.hasErrors()).thenReturn(false);
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
    public void createGoodAccount() throws Exception {
        // Given
        user.setId(1);
        user.setBrutePassword("StrongPass0!");
        user.setFullname("A User");
        user.setUsername("User");
        user.setRole("USER");
        String body = (new ObjectMapper()).valueToTree(user).toString();

        // When
        mvc.perform(post("/user/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());


        // Then
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(userRepository.findAll().get(0).getUsername()).isEqualTo("User");
        assertThat(userRepository.findAll().get(0).getFullname()).isEqualTo("A User");
    }

    @Test
    public void deleteAccount() throws Exception {
        // Given
        user.setBrutePassword("StrongPass0!");
        user.setFullname("A User");
        user.setUsername("User");
        user.setRole("USER");
        String body = (new ObjectMapper()).valueToTree(user).toString();

        // When
        mvc.perform(post("/user/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/user/delete/" + userRepository.findByUserName("User").getId())
        )
                .andExpect(redirectedUrl("/user/list"));

        // Then
        assertThat(userRepository.findAll()).hasSize(0);
    }

    @Test
    public void updateAccount() throws Exception {
        // Given
        user.setBrutePassword("StrongPass0!");
        user.setFullname("A User");
        user.setUsername("User");
        user.setRole("USER");
        String body = (new ObjectMapper()).valueToTree(user).toString();

        // When
        mvc.perform(post("/user/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        user.setBrutePassword("AnotherStrongPass0!");
        user.setFullname("A User");
        user.setUsername("ANewUser");
        user.setRole("USER");
        body = (new ObjectMapper()).valueToTree(user).toString();
        mvc.perform(post("/user/update/api/" + userRepository.findByUserName("User").getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(userRepository.findAll()).hasSize(1);
    }
}
