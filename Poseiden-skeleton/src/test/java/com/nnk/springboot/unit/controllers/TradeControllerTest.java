package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.TradeService;
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
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
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

@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class TradeControllerTest {

    @MockBean
    private TradeService tradeService;

    @Autowired
    protected MockMvc mvc;

    @BeforeEach
    void setup(){
        when(tradeService.findTradeByID(anyInt())).thenReturn(new Trade(1,"test","test",1D));
    }
    @Test
    public void generateAddTradeFormWithSuccess() throws Exception {
        mvc.perform(get("/trade/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void generateUpdateTradeFormWithSuccess() throws Exception {

        mvc.perform(get("/trade/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void addGoodTrade() throws Exception {

        mvc.perform(post("/trade/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("account","1")
                .param("type","10.5")
                .param("buyQuantity", "20.5")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void deleteTrade() throws Exception {

        mvc.perform(get("/trade/delete/1")
        )
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void updateTrade() throws Exception {


        mvc.perform(post("/trade/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("account","1")
                .param("type","10.5")
                .param("buyQuantity", "20.5")
        )
                .andExpect(status().is3xxRedirection());
    }
}
