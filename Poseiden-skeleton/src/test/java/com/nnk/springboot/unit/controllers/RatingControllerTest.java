package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
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


@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class RatingControllerTest {
    @MockBean
    private RatingService ratingService;

    @Autowired
    protected MockMvc mvc;

    @BeforeEach
    void setup(){
        when(ratingService.findRatingByID(anyInt())).thenReturn(new Rating("mmoo","test","test",1));
    }
    @Test
    public void generateAddRatingFormWithSuccess() throws Exception {
        mvc.perform(get("/rating/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void generateUpdateRatingFormWithSuccess() throws Exception {

        mvc.perform(get("/rating/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("rating")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void addGoodRating() throws Exception {
        when(ratingService.findRatingByID(anyInt())).thenReturn(null);
        mvc.perform(post("/rating/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("fitchRating","1")
                .param("sandPRating","10.5")
                .param("moodysRating", "20.5")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void deleteRating() throws Exception {

        mvc.perform(get("/rating/delete/1")
        )
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void updateRatingWithWrongId() throws Exception {
        when(ratingService.findRatingByID(anyInt())).thenReturn(null);
        mvc.perform(post("/rating/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","15.5")
                .param("value", "20.5")
        )
                .andExpect(status().is4xxClientError());
    }
}
