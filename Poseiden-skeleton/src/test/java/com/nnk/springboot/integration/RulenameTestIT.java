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

@EnableAutoConfiguration
public class RulenameTestIT extends AbstractIT {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setup() {
        ruleNameRepository.deleteAll();
        when(result.hasErrors()).thenReturn(false);
    }

    @Test
    public void addGoodRuleName() throws Exception {
        // Given

        // When
        mvc.perform(post("/ruleName/validate/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id","1")
                .param("name", "name")
                .param("json", "jsonFeature")
                .param("description", "usual description")
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
        )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void generateUpdateRuleNameFormWithSuccess() throws Exception {
        // Given

        // When
        mvc.perform(post("/ruleName/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("name", "name")
                .param("json", "jsonFeature")
                .param("description", "usual description")
        )
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/ruleName/update/" + ruleNameRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void deleteRuleName() throws Exception {
        // Given

        // When
        mvc.perform(post("/ruleName/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("name", "name")
                .param("json", "jsonFeature")
                .param("description", "usual description")
        )
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/ruleName/delete/" + ruleNameRepository.findAll().get(0).getId())
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ruleNameRepository.findAll()).hasSize(0);
    }

    @Test
    public void deleteRuleNameWithWrongId() throws Exception {
        // Given

        // When
        mvc.perform(post("/ruleName/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("name", "name")
                .param("json", "jsonFeature")
                .param("description", "usual description")
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

        // When
        mvc.perform(post("/ruleName/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("name", "name")
                .param("json", "jsonFeature")
                .param("description", "usual description")
        )
                .andExpect(status().is3xxRedirection());

        assertThat(ruleNameRepository.findAll()).hasSize(1);

        mvc.perform(post("/ruleName/update/" + ruleNameRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("name", "name")
                .param("json", "jsonFeature")
                .param("description", "another usual description")
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ruleNameRepository.findAll()).hasSize(1);
        assertThat(ruleNameRepository.findAll().get(0).getDescription()).isEqualTo("usual description");
    }

    @Test
    public void updateRuleNameWithWrongId() throws Exception {
        // Given
        // When
        mvc.perform(post("/ruleName/validate/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("name", "name")
                .param("json", "jsonFeature")
                .param("description", "usual description")
        )
                .andExpect(status().is3xxRedirection());

        assertThat(ruleNameRepository.findAll()).hasSize(1);

        mvc.perform(get("/ruleName/update/10")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection());

        // Then
        assertThat(ruleNameRepository.findAll()).hasSize(1);
        assertThat(ruleNameRepository.findAll().get(0).getDescription()).isEqualTo("usual description");
    }
}
