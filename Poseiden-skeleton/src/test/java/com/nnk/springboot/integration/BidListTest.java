package com.nnk.springboot.integration;

import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes= SecurityConfig.class)
public class BidListTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BidListController bidListController;
    @Autowired
    private WebApplicationContext context;

    private User user;
    private MockMvc mvc;
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
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("spring")
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/bidList/list")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addABidListSuccessfully() {
        // Given

        // When
        bidListController.validate(bidList, result);

        // Then
        assertThat(userRepository.findAll()).hasSize(1);
    }
}
