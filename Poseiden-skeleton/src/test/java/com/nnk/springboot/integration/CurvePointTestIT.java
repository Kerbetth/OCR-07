package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
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
public class CurvePointTestIT extends AbstractIT {

    @Autowired
    private CurvePointRepository curvePointRepository;


    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        curvePointRepository.deleteAll();
        when(result.hasErrors()).thenReturn(false);
    }
    @AfterEach
    public void after() {
        curvePointRepository.deleteAll();
    }

    @Test
    public void generateAddCurvePointFormWithSuccess() throws Exception {
        mvc.perform(get("/curvePoint/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void generateUpdateCurvePointFormWithSuccess() throws Exception {
        // Given

        // When
        mvc.perform(post("/curvePoint/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","10.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/curvePoint/update/" + curvePointRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("curvePoint")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void addGoodCurvePoint() throws Exception {
        // Given

        // When
        mvc.perform(post("/curvePoint/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","10.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());


        // Then
        assertThat(curvePointRepository.findAll()).hasSize(1);
        assertThat(curvePointRepository.findAll().get(0).getTerm()).isEqualTo(10.5);
    }

    @Test
    public void deleteCurvePoint() throws Exception {
        // Given

        // When
        mvc.perform(post("/curvePoint/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","10.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/curvePoint/delete/" + curvePointRepository.findAll().get(0).getId())
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(curvePointRepository.findAll()).hasSize(0);
    }

    @Test
    public void deleteCurvePointWithWrongId() throws Exception {
        // Given

        // When
        mvc.perform(post("/curvePoint/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","10.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/curvePoint/delete/10")
        )
                .andExpect(status().is4xxClientError());

        // Then
        assertThat(curvePointRepository.findAll()).hasSize(1);
    }

    @Test
    public void updateCurvePoint() throws Exception {
        // Given

        // When
        mvc.perform(post("/curvePoint/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","10.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());

        assertThat(curvePointRepository.findAll()).hasSize(1);

        mvc.perform(post("/curvePoint/update/" + curvePointRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveID","1")
                .param("term","15.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(curvePointRepository.findAll()).hasSize(1);
        assertThat(curvePointRepository.findAll().get(0).getTerm()).isEqualTo(15.5);
    }

    @Test
    public void updateCurvePointWithWrongId() throws Exception {
        // Given

        // When
        mvc.perform(post("/curvePoint/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","10.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());

        assertThat(curvePointRepository.findAll()).hasSize(1);

        mvc.perform(post("/curvePoint/update/80")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","15.5")
                .param("value", "20.5")
        )
                .andExpect(status().is4xxClientError());

        // Then
        assertThat(curvePointRepository.findAll()).hasSize(1);
        assertThat(curvePointRepository.findAll().get(0).getTerm()).isEqualTo(10.5);
    }
}
