package com.nnk.springboot.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class RuleNameApiController {

    @Autowired
    RuleNameService ruleNameService;


    @PostMapping("/ruleNameAPI")
    public String validateApi(@Valid @RequestBody RuleName ruleName, BindingResult result) {
        if (result.hasErrors()) {
            return result.toString();
        }
        ruleNameService.updateRuleName(ruleName);
        return "A new RuleName has been saved";
    }

    @GetMapping("/ruleNameAPI/{id}")
    public String readRuleName(@PathVariable("id") Integer id) throws JsonProcessingException {
        if (ruleNameService.findRuleNameByID(id) == null) {
            return "No RuleName with this ID";
        }
        ObjectMapper Obj = new ObjectMapper();
        return Obj.writeValueAsString(ruleNameService.findRuleNameByID(id));
    }

    @PutMapping("/ruleNameAPI/{id}")
    public String updateRuleNameApi(@PathVariable("id") Integer id, @Valid @RequestBody RuleName curve, BindingResult result) {
        if (ruleNameService.findRuleNameByID(id) == null) {
            return "No RuleName with this ID";
        } else if (result.hasErrors()) {
            return result.toString();
        } else {
            curve.setId(id);
            ruleNameService.updateRuleName(curve);
            return "Ok";
        }
    }

    @DeleteMapping("/ruleNameAPI/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        if (ruleNameService.findRuleNameByID(id) == null) {
            return "No RuleName with this ID";
        }
        ruleNameService.deleteRuleName(id);
        return "RuleName " + id + " has been deleted";
    }
}
