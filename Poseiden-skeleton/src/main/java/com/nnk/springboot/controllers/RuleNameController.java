package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.exceptions.IdAlreadyExistException;
import com.nnk.springboot.controllers.exceptions.IdDoesntExistException;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * These controllers manage CRUD operations with the RuleName object
 * and generate a model for listing, adding and updating
 */
@Controller
public class RuleNameController {

    @Autowired
    RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
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
        if (ruleNameService.findRuleNameByID(ruleName.getId()) == null) {
            if (result.hasErrors()) {
                return "ruleName/add";
            }
            ruleNameService.updateRuleName(ruleName);
            return "redirect:/ruleName/list";
        }
        throw new IdAlreadyExistException(ruleName.getId());
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
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
        if (ruleNameService.findRuleNameByID(id) != null) {
            if (result.hasErrors()) {
                return "ruleName/update";
            }
            ruleNameService.updateRuleName(ruleName);
            return "redirect:/ruleName/list";
        }
        throw new IdDoesntExistException(id);
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        RuleName ruleName = ruleNameService.findRuleNameByID(id);
        if (ruleName == null) {
            throw new IdDoesntExistException(id);
        }
        ruleNameService.deleteRuleName(ruleName);
        return "redirect:/ruleName/list";
    }
}
