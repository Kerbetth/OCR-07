package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */


@EnableAutoConfiguration
public class RatingTestIT extends AbstractIT {

    @Autowired
    private RatingRepository ratingRepository;


    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        ratingRepository.deleteAll();
        when(result.hasErrors()).thenReturn(false);
    }

    @Test
    public void addGoodRating() throws Exception {
        // Given

        // When
        mvc.perform(post("/rating/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
        )
                .andExpect(status().is3xxRedirection());


        // Then
        assertThat(ratingRepository.findAll()).hasSize(1);
        assertThat(ratingRepository.findAll().get(0).getFitchRating()).isEqualTo("fitch");
    }

    @Test
    public void generateAddRatingFormWithSuccess() throws Exception {
        mvc.perform(get("/rating/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void generateUpdateRatingFormWithSuccess() throws Exception {
        // Given

        // When
        mvc.perform(post("/rating/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/rating/update/"+ ratingRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void deleteRating() throws Exception {
        // Given

        // When
        mvc.perform(post("/rating/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/rating/delete/"+ ratingRepository.findAll().get(0).getId())
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ratingRepository.findAll()).hasSize(0);
    }

    @Test
    public void deleteRatingWithWrongId() throws Exception {
        // Given

        // When
        mvc.perform(post("/rating/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/rating/delete/10")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ratingRepository.findAll()).hasSize(1);
    }

    @Test
    public void updateRating() throws Exception {
        // Given

        // When
        mvc.perform(post("/rating/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
        )
                .andExpect(status().is3xxRedirection());

        assertThat(ratingRepository.findAll()).hasSize(1);

        mvc.perform(post("/rating/update//"+ ratingRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch2")
                .param("moodysRating", "moody2")
                .param("sandPRating", "sand2")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ratingRepository.findAll()).hasSize(1);
        assertThat(ratingRepository.findAll().get(0).getFitchRating()).isEqualTo("fitch2");
    }

    @Test
    public void updateRatingWithWrongId() throws Exception {
        // Given

        // When
        mvc.perform(post("/rating/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
        )
                .andExpect(status().is3xxRedirection());

        assertThat(ratingRepository.findAll()).hasSize(1);

        mvc.perform(post("/rating/update//10")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","fitch2")
                .param("moodysRating", "moody2")
                .param("sandPRating", "sand2")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ratingRepository.findAll()).hasSize(1);
        assertThat(ratingRepository.findAll().get(0).getFitchRating()).isEqualTo("fitch");
    }
}
