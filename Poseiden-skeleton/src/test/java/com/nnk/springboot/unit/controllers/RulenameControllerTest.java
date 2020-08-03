package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
public class RulenameControllerTest {
    @MockBean
    private RuleNameService ruleNameService;

    @Autowired
    protected MockMvc mvc;


    @Test
    public void generateAddRulenameFormWithSuccess() throws Exception {
        mvc.perform(get("/rulename/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("rulename/add"));
    }

    @Test
    public void generateUpdateRulenameFormWithSuccess() throws Exception {

        mvc.perform(get("/rulename/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("rulename")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("rulename/update"));
    }

    @Test
    public void addGoodRulename() throws Exception {

        mvc.perform(post("/rulename/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("curveID","1")
                .param("term","10.5")
                .param("value", "20.5")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void deleteRulename() throws Exception {

        mvc.perform(get("/rulename/delete/1")
        )
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void updateRulename() throws Exception {


        mvc.perform(post("/rulename/update/1")
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
