package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.BidService;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class CurvePointControllerTest {

    @MockBean
    private CurvePointService curvePointService;

    @Autowired
    protected MockMvc mvc;


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

        mvc.perform(get("/curvePoint/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("curvePoint")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void addGoodCurvePoint() throws Exception {

        mvc.perform(post("/curvePoint/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","10.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void deleteCurvePoint() throws Exception {

        mvc.perform(get("/curvePoint/delete/1")
        )
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void updateCurvePoint() throws Exception {


        mvc.perform(post("/curvePoint/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","15.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());
    }
}
