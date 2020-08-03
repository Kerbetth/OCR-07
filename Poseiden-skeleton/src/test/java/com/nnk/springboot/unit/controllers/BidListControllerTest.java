package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
public class BidListControllerTest {

    @MockBean
    private BidService bidService;

    @Autowired
    protected MockMvc mvc;


    @Test
    public void addGoodBidList() throws Exception {
        mvc.perform(post("/bidList/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("bidListId", "1")
                .param("account", "account")
                .param("type", "type1")
                .param("bidQuantity", "15.5")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void generateAddBidListFormWithSuccess() throws Exception {
        mvc.perform(get("/bidList/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void generateUpdateBidListFormWithSuccess() throws Exception {
        mvc.perform(get("/bidList/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    public void deleteBidList() throws Exception {
        mvc.perform(get("/bidList/delete/1")
        )
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void updateBidList() throws Exception {
        mvc.perform(post("/bidList/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("bidListId", "1")
                .param("account", "account2")
                .param("type", "type2")
                .param("bidQuantity", "15.5")
        )
                .andExpect(status().is3xxRedirection());
    }
}