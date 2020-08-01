package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RuleNameController {
    // TODO: Inject RuleName service

    @Autowired
    RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        // TODO: find all RuleName, add to model
        model.addAttribute("ruleNameList", ruleNameService.loadAllRating());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.addRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        RuleName ruleName = ruleNameService.findRuleNameByID(id);
        if (ruleName != null) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        } else {
            return "redirect:/ruleName/list";
        }
    }


    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        if (result.hasErrors() || ruleNameService.findRuleNameByID(id) == null) {
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        if (ruleNameService.findRuleNameByID(id) == null) {
            return "redirect:/ruleName/list";
        }
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
