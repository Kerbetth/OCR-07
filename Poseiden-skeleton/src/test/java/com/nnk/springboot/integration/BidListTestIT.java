package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.controllers.BidListController;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.Matchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
public class BidListTestIT {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BidListController bidListController;
    @Autowired
    private MockMvc mvc;

    private User user;

    private BidList bidList;

    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1);
        user.setBrutePassword("StrongPass0!");
        user.setFullname("A User");
        user.setUsername("A User");
        user.setRole("USER");
        bidList = new BidList();
        when(result.hasErrors()).thenReturn(false);
        userRepository.deleteAll();
        bidList.setBidListId(1);
        bidList.setAccount("account");
        bidList.setType("type");
        bidList.setBidQuantity(20d);

    }


    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/bidList/list")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void depositController() throws Exception {
        mvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bidList", any(BidList.class)));
    }


    @Test
    public void depositingController() throws Exception {
        BidList bidList = new BidList();
        bidList.setType("Type Test");
        bidList.setAccount("Account Test");
        bidList.setBidListId(1);
        bidList.setBidQuantity(80d);
        String body = (new ObjectMapper()).valueToTree(bidList).toString();
        mvc.perform(post("/bidList/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
    }
}