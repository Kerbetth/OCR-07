package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
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


@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class CurvePointTestIT {

    @Autowired
    private CurvePointRepository curvePointRepository;
    @Autowired
    private MockMvc mvc;

    private CurvePoint curvePoint;

    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        curvePointRepository.deleteAll();
        curvePoint = new CurvePoint();
        when(result.hasErrors()).thenReturn(false);
    }
    

    @Test
    public void generateAddCurvePointFormWithSuccess() throws Exception {
        mvc.perform(get("/curvePoint/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("curvePoint")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void generateUpdateCurvePointFormWithSuccess() throws Exception {
        // Given
        curvePoint.setCurveID(1);
        curvePoint.setTerm(10.5);
        curvePoint.setValue(20.5);
        String body = (new ObjectMapper()).valueToTree(curvePoint).toString();

        // When
        mvc.perform(post("/curvePoint/validate/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/curvePoint/update/"+curvePointRepository.findAll().get(0).getId())
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
        curvePoint.setId(1);
        curvePoint.setCurveID(1);
        curvePoint.setTerm(10.5);
        curvePoint.setValue(20.5);
        String body = (new ObjectMapper()).valueToTree(curvePoint).toString();

        // When
        mvc.perform(post("/curvePoint/validate/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());


        // Then
        assertThat(curvePointRepository.findAll()).hasSize(1);
        assertThat(curvePointRepository.findAll().get(0).getTerm()).isEqualTo(10.5);
    }
    @Test
    public void deleteCurvePoint() throws Exception {
        // Given
        curvePoint.setId(1);
        curvePoint.setCurveID(1);
        curvePoint.setTerm(10.5);
        curvePoint.setValue(20.5);
        String body = (new ObjectMapper()).valueToTree(curvePoint).toString();

        // When
        mvc.perform(post("/curvePoint/validate/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/curvePoint/delete/"+curvePointRepository.findAll().get(0).getId())
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(curvePointRepository.findAll()).hasSize(0);
    }

    @Test
    public void deleteCurvePointWithWrongId() throws Exception {
        // Given
        curvePoint.setId(1);
        curvePoint.setCurveID(1);
        curvePoint.setTerm(10.5);
        curvePoint.setValue(20.5);
        String body = (new ObjectMapper()).valueToTree(curvePoint).toString();

        // When
        mvc.perform(post("/curvePoint/validate/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/curvePoint/delete/10")
        )
                .andExpect(status().isOk());

        // Then
        assertThat(curvePointRepository.findAll()).hasSize(1);
    }

    @Test
    public void updateCurvePoint() throws Exception {
        // Given
        curvePoint.setCurveID(1);
        curvePoint.setTerm(10.5);
        curvePoint.setValue(20.5);
        String body = (new ObjectMapper()).valueToTree(curvePoint).toString();

        // When
        mvc.perform(post("/curvePoint/validate/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        assertThat(curvePointRepository.findAll()).hasSize(1);

        curvePoint.setCurveID(1);
        curvePoint.setTerm(15.5);
        curvePoint.setValue(20.5);
        body = (new ObjectMapper()).valueToTree(curvePoint).toString();
        mvc.perform(post("/curvePoint/update/api/"+curvePointRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(curvePointRepository.findAll()).hasSize(1);
        assertThat(curvePointRepository.findAll().get(0).getTerm()).isEqualTo(15.5);
    }

    @Test
    public void updateCurvePointWhithWrongId() throws Exception {
        // Given
        curvePoint.setCurveID(1);
        curvePoint.setTerm(10.5);
        curvePoint.setValue(20.5);
        String body = (new ObjectMapper()).valueToTree(curvePoint).toString();

        // When
        mvc.perform(post("/curvePoint/validate/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        assertThat(curvePointRepository.findAll()).hasSize(1);

        curvePoint.setCurveID(1);
        curvePoint.setTerm(15.5);
        curvePoint.setValue(20.5);
        body = (new ObjectMapper()).valueToTree(curvePoint).toString();
        mvc.perform(post("/curvePoint/update/api/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(curvePointRepository.findAll()).hasSize(1);
        assertThat(curvePointRepository.findAll().get(0).getTerm()).isEqualTo(10.5);
    }
}
