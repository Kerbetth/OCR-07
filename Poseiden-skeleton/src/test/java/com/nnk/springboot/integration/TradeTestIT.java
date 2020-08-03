package com.nnk.springboot.integration;

import com.nnk.springboot.repositories.TradeRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */

@EnableAutoConfiguration
public class TradeTestIT extends AbstractIT{

    @Autowired
    private TradeRepository tradeRepository;


    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        tradeRepository.deleteAll();
        when(result.hasErrors()).thenReturn(false);
    }

    @Test
    public void addGoodTrade() throws Exception {
        // Given


        // When
        mvc.perform(post("/trade/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","another usual description")
                .param("buyQuantity", "50.5")
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
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","another usual description")
                .param("buyQuantity", "50.5")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void generateUpdateTradeFormWithSuccess() throws Exception {
        // Given

        // When
        mvc.perform(post("/trade/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","another usual description")
                .param("buyQuantity", "50.5")
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/trade/update/"+ tradeRepository.findAll().get(0).getTradeId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void deleteTrade() throws Exception {
        // Given


        // When
        mvc.perform(post("/trade/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","another usual description")
                .param("buyQuantity", "50.5")
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

        // When
        mvc.perform(post("/trade/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","another usual description")
                .param("buyQuantity", "50.5")
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

        // When
        mvc.perform(post("/trade/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","another usual description")
                .param("buyQuantity", "50.5")
        )
                .andExpect(status().is3xxRedirection());

        assertThat(tradeRepository.findAll()).hasSize(1);

        mvc.perform(post("/trade/update/"+ tradeRepository.findAll().get(0).getTradeId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","another usual description")
                .param("buyQuantity", "50.5")
        )
                .andExpect(status().is3xxRedirection());
        // Then
        assertThat(tradeRepository.findAll()).hasSize(2);
        assertThat(tradeRepository.findAll().get(1).getAccount()).isEqualTo("another usual description");
    }

    @Test
    public void updateTradeWithWrongId() throws Exception {
        // Given

        // When
        mvc.perform(post("/trade/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","usual description")
                .param("buyQuantity", "50.5")
        )
                .andExpect(status().is3xxRedirection());

        assertThat(tradeRepository.findAll()).hasSize(1);

        mvc.perform(post("/trade/update/10")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tradeId","1")
                .param("type","jsonFeature")
                .param("account","another usual description")
                .param("buyQuantity", "50.5")
        )
                .andExpect(status().isOk());

        // Then
        assertThat(tradeRepository.findAll()).hasSize(1);
        assertThat(tradeRepository.findAll().get(0).getAccount()).isEqualTo("usual description");
    }
}
