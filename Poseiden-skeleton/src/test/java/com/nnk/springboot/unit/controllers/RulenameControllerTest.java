package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

import static org.mockito.ArgumentMatchers.anyInt;
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
public class RulenameControllerTest {
    @MockBean
    private RuleNameService ruleNameService;

    @Autowired
    protected MockMvc mvc;

    @BeforeEach
    void setup(){
        when(ruleNameService.findRuleNameByID(anyInt())).thenReturn(new RuleName(1,"test","test","1"));
    }

    @Test
    public void generateAddRulenameFormWithSuccess() throws Exception {
        mvc.perform(get("/ruleName/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void generateUpdateRulenameFormWithSuccess() throws Exception {

        mvc.perform(get("/ruleName/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("rulename")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void addGoodRulename() throws Exception {

        mvc.perform(post("/ruleName/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("name","1")
                .param("description","10.5")
                .param("json", "20.5")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void deleteRulename() throws Exception {

        mvc.perform(get("/ruleName/delete/1")
        )
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void updateRulename() throws Exception {


        mvc.perform(post("/ruleName/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("name","1")
                .param("description","10.5")
                .param("json", "20.5")
        )
                .andExpect(status().is3xxRedirection());
    }
}
