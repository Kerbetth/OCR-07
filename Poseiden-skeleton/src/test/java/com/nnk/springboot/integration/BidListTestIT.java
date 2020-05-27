package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    private BidListRepository bidListRepository;
    @Autowired
    private MockMvc mvc;

    private BidList bidList;

    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        bidListRepository.deleteAll();
        bidList = new BidList();
        when(result.hasErrors()).thenReturn(false);
    }

    @Test
    public void addGoodBidList() throws Exception {
        // Given
        bidList.setAccount("account");
        bidList.setType("type1");
        bidList.setBidQuantity(15.5);

        // When
        mvc.perform(post("/bidList/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account","account")
                .param("type","type1")
                .param("bidQuantity", "15.5")
                .requestAttr("bidList", bidList)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().is3xxRedirection());


        // Then
        assertThat(bidListRepository.findAll()).hasSize(1);
        assertThat(bidListRepository.findAll().get(0).getAccount()).isEqualTo("account");
    }

    @Test
    public void generateAddBidListFormWithSuccess() throws Exception {
        mvc.perform(get("/bidList/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("bidList")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void generateUpdateBidListFormWithSuccess() throws Exception {
        // Given
        bidList.setAccount("account");
        bidList.setType("type1");
        bidList.setBidQuantity(15.5);

        // When
        mvc.perform(post("/bidList/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account","account")
                .param("type","type1")
                .param("bidQuantity", "15.5")
                .requestAttr("bidList", bidList)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/bidList/update/" + bidListRepository.findAll().get(0).getBidListId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("bidList")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    public void deleteBidList() throws Exception {
        // Given
        bidList.setAccount("account");
        bidList.setType("type1");
        bidList.setBidQuantity(15.5);

        // When
        mvc.perform(post("/bidList/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account","account")
                .param("type","type1")
                .param("bidQuantity", "15.5")
                .requestAttr("bidList", bidList)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/bidList/delete/" + bidListRepository.findAll().get(0).getBidListId())
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(bidListRepository.findAll()).hasSize(0);
    }

    @Test
    public void deleteBidListWithWrongId() throws Exception {
        // Given
        bidList.setAccount("account");
        bidList.setType("type1");
        bidList.setBidQuantity(15.5);

        // When
        mvc.perform(post("/bidList/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account","account")
                .param("type","type1")
                .param("bidQuantity", "15.5")
                .requestAttr("bidList", bidList)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/bidList/delete/10")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(bidListRepository.findAll()).hasSize(1);
    }

    @Test
    public void updateBidList() throws Exception {
        // Given
        bidList.setAccount("account");
        bidList.setType("type1");
        bidList.setBidQuantity(15.5);

        // When
        mvc.perform(post("/bidList/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account","account")
                .param("type","type1")
                .param("bidQuantity", "15.5")
                .requestAttr("bidList", bidList)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().is3xxRedirection());

        assertThat(bidListRepository.findAll()).hasSize(1);

        bidList.setAccount("account2");
        bidList.setType("type2");
        bidList.setBidQuantity(15.5);

        mvc.perform(post("/bidList/update/" + bidListRepository.findAll().get(0).getBidListId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account","account2")
                .param("type","type2")
                .param("bidQuantity", "15.5")
                .requestAttr("bidList", bidList)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(bidListRepository.findAll()).hasSize(1);
        assertThat(bidListRepository.findAll().get(0).getAccount()).isEqualTo("account2");
    }

    @Test
    public void updateBidListWhithWrongId() throws Exception {
        // Given
        bidList.setAccount("account");
        bidList.setType("type1");
        bidList.setBidQuantity(15.5);

        // When
        mvc.perform(post("/bidList/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account","account")
                .param("type","type1")
                .param("bidQuantity", "15.5")
                .requestAttr("bidList", bidList)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().is3xxRedirection());

        assertThat(bidListRepository.findAll()).hasSize(1);

        bidList.setAccount("account2");
        bidList.setType("type2");
        bidList.setBidQuantity(20.0);

        mvc.perform(post("/bidList/update/10")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account","account2")
                .param("type","type2")
                .param("bidQuantity", "20.0")
                .requestAttr("bidList", bidList)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isOk());

        // Then
        assertThat(bidListRepository.findAll()).hasSize(1);
        assertThat(bidListRepository.findAll().get(0).getAccount()).isEqualTo("account");
    }
}