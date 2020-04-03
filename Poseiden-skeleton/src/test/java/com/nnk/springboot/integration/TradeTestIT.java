package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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


@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class TradeTestIT {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private MockMvc mvc;

    private Trade trade;

    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        tradeRepository.deleteAll();
        trade = new Trade();
        when(result.hasErrors()).thenReturn(false);
    }

    @Test
    public void addGoodTrade() throws Exception {
        // Given
        trade.setType("jsonFeature");
        trade.setAccount("usual description");
        trade.setBuyQuantity(50.5);
        String body = (new ObjectMapper()).valueToTree(trade).toString();

        // When
        mvc.perform(post("/trade/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());


        // Then
        assertThat(tradeRepository.findAll()).hasSize(1);
        assertThat(tradeRepository.findAll().get(0).getType()).isEqualTo("jsonFeature");
    }

    @Test
    public void generateAddTradeFormWithSuccess() throws Exception {
        mvc.perform(get("/trade/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("trade")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void generateUpdateTradeFormWithSuccess() throws Exception {
        // Given
        trade.setType("jsonFeature");
        trade.setAccount("usual description");
        trade.setBuyQuantity(50.5);
        String body = (new ObjectMapper()).valueToTree(trade).toString();

        // When
        mvc.perform(post("/trade/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/trade/update/"+ tradeRepository.findAll().get(0).getTradeId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("trade")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void deleteTrade() throws Exception {
        // Given
        trade.setType("jsonFeature");
        trade.setAccount("usual description");
        trade.setBuyQuantity(50.5);
        String body = (new ObjectMapper()).valueToTree(trade).toString();

        // When
        mvc.perform(post("/trade/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/trade/delete/"+ tradeRepository.findAll().get(0).getTradeId())
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(tradeRepository.findAll()).hasSize(0);
    }

    @Test
    public void deleteTradeWithWrongId() throws Exception {
        // Given
        trade.setType("jsonFeature");
        trade.setAccount("usual description");
        trade.setBuyQuantity(50.5);
        String body = (new ObjectMapper()).valueToTree(trade).toString();

        // When
        mvc.perform(post("/trade/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/trade/delete/10")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(tradeRepository.findAll()).hasSize(1);
    }

    @Test
    public void updateTrade() throws Exception {
        // Given
        trade.setTradeId(2);
        trade.setType("jsonFeature");
        trade.setAccount("usual description");
        trade.setBuyQuantity(50.5);
        String body = (new ObjectMapper()).valueToTree(trade).toString();

        // When
        mvc.perform(post("/trade/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        assertThat(tradeRepository.findAll()).hasSize(1);

        trade.setType("jsonFeature");
        trade.setAccount("another usual description");
        trade.setBuyQuantity(50.5);
        body = (new ObjectMapper()).valueToTree(trade).toString();
        mvc.perform(post("/trade/update/api/"+ tradeRepository.findAll().get(0).getTradeId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(tradeRepository.findAll()).hasSize(1);
        assertThat(tradeRepository.findAll().get(0).getAccount()).isEqualTo("another usual description");
    }

    @Test
    public void updateTradeWhithWrongId() throws Exception {
        // Given
        trade.setTradeId(2);
        trade.setType("jsonFeature");
        trade.setAccount("usual description");
        trade.setBuyQuantity(50.5);
        String body = (new ObjectMapper()).valueToTree(trade).toString();

        // When
        mvc.perform(post("/trade/add/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        assertThat(tradeRepository.findAll()).hasSize(1);

        trade.setType("jsonFeature");
        trade.setAccount("another usual description");
        trade.setBuyQuantity(50.5);
        body = (new ObjectMapper()).valueToTree(trade).toString();
        mvc.perform(post("/trade/update/api/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(tradeRepository.findAll()).hasSize(1);
        assertThat(tradeRepository.findAll().get(0).getAccount()).isEqualTo("usual description");
    }
}
