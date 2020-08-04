package com.nnk.springboot.integration;

import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */


@EnableAutoConfiguration
public class UserTestIT extends AbstractIT{

    @Autowired
    private UserRepository userRepository;

    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        when(result.hasErrors()).thenReturn(false);
    }

    @AfterEach
    public void after() {
        userRepository.deleteAll();
    }

    @Test
    public void generateAddUserFormWithSuccess() throws Exception {
        mvc.perform(get("/user/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void createGoodAccount() throws Exception {
        // Given

        // When
        mvc.perform(post("/user/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("brutPassword","StrongPass0!")
                .param("username","user")
                .param("fullname","A user")
                .param("role", "USER")
        )
                .andExpect(status().is3xxRedirection());


        // Then
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(userRepository.findAll().get(0).getUsername()).isEqualTo("user");
        assertThat(userRepository.findAll().get(0).getFullname()).isEqualTo("A user");
    }

    @Test
    public void deleteAccount() throws Exception {
        // Given

        // When
        mvc.perform(post("/user/validate/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","1")
                        .param("brutPassword","StrongPass0!")
                        .param("username","user")
                        .param("fullname","user")
                        .param("role", "USER")
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/user/delete/" + userRepository.findByUserName("user").getId())
        )
                .andExpect(redirectedUrl("/user/list"));

        // Then
        assertThat(userRepository.findAll()).hasSize(0);
    }

    @Test
    public void generateUpdateUserFormWithSuccess() throws Exception {
        // Given

        // When
        mvc.perform(post("/user/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("brutPassword","StrongPass0!")
                .param("username","user")
                .param("fullname","user")
                .param("role", "USER")
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/user/update/"+userRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void updateAccount() throws Exception {
        // Given

        // When
        mvc.perform(post("/user/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("brutPassword","StrongPass0!")
                .param("username","user")
                .param("fullname","user")
                .param("role", "USER")
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/user/update/" + userRepository.findByUserName("user").getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("brutPassword","StrongPass0!")
                .param("username","user")
                .param("fullname","userFull")
                .param("role", "USER")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(userRepository.findByUserName("user").getFullname()).isEqualTo("userFull");
    }
}
