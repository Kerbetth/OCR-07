package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RulenameTestIT {

    @Autowired
    private RuleNameRepository ruleNameRepository;
    @Autowired
    private MockMvc mvc;

    private RuleName ruleName;

    BindingResult result = mock(BindingResult.class);

    @BeforeEach
    public void setup() {
        ruleNameRepository.deleteAll();
        ruleName = new RuleName();
        when(result.hasErrors()).thenReturn(false);
    }

    @Test
    public void addGoodRuleName() throws Exception {
        // Given
        ruleName.setJson("jsonFeature");
        ruleName.setDescription("usual description");
        ruleName.setName("name");
        String body = (new ObjectMapper()).valueToTree(ruleName).toString();

        // When
        mvc.perform(post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());


        // Then
        assertThat(ruleNameRepository.findAll()).hasSize(1);
        assertThat(ruleNameRepository.findAll().get(0).getJson()).isEqualTo("jsonFeature");
    }

    @Test
    public void generateAddRuleNameFormWithSuccess() throws Exception {
        mvc.perform(get("/ruleName/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("ruleName")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void generateUpdateRuleNameFormWithSuccess() throws Exception {
        // Given
        ruleName.setJson("jsonFeature");
        ruleName.setDescription("usual description");
        ruleName.setName("name");
        String body = (new ObjectMapper()).valueToTree(ruleName).toString();

        // When
        mvc.perform(post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/ruleName/update/"+ ruleNameRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("ruleName")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void deleteRuleName() throws Exception {
        // Given
        ruleName.setJson("jsonFeature");
        ruleName.setDescription("usual description");
        ruleName.setName("name");
        String body = (new ObjectMapper()).valueToTree(ruleName).toString();

        // When
        mvc.perform(post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/ruleName/delete/"+ ruleNameRepository.findAll().get(0).getId())
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ruleNameRepository.findAll()).hasSize(0);
    }

    @Test
    public void deleteRuleNameWithWrongId() throws Exception {
        // Given
        ruleName.setJson("jsonFeature");
        ruleName.setDescription("usual description");
        ruleName.setName("name");
        String body = (new ObjectMapper()).valueToTree(ruleName).toString();

        // When
        mvc.perform(post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/ruleName/delete/10")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ruleNameRepository.findAll()).hasSize(1);
    }

    @Test
    public void updateRuleName() throws Exception {
        // Given
        ruleName.setJson("jsonFeature");
        ruleName.setDescription("usual description");
        ruleName.setName("name");
        String body = (new ObjectMapper()).valueToTree(ruleName).toString();

        // When
        mvc.perform(post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        assertThat(ruleNameRepository.findAll()).hasSize(1);

        ruleName.setJson("jsonFeature");
        ruleName.setDescription("another usual description");
        ruleName.setName("name");
        body = (new ObjectMapper()).valueToTree(ruleName).toString();
        mvc.perform(post("/ruleName/update/"+ ruleNameRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ruleNameRepository.findAll()).hasSize(1);
        assertThat(ruleNameRepository.findAll().get(0).getDescription()).isEqualTo("another usual description");
    }

    @Test
    public void updateRuleNameWhithWrongId() throws Exception {
        // Given
        ruleName.setJson("jsonFeature");
        ruleName.setDescription("usual description");
        ruleName.setName("name");
        String body = (new ObjectMapper()).valueToTree(ruleName).toString();

        // When
        mvc.perform(post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        assertThat(ruleNameRepository.findAll()).hasSize(1);

        ruleName.setJson("jsonFeature");
        ruleName.setDescription("another usual description");
        ruleName.setName("name");
        body = (new ObjectMapper()).valueToTree(ruleName).toString();
        mvc.perform(post("/ruleName/update/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ruleNameRepository.findAll()).hasSize(1);
        assertThat(ruleNameRepository.findAll().get(0).getDescription()).isEqualTo("usual description");
    }
}
