package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.exceptions.IdAlreadyExistException;
import com.nnk.springboot.controllers.exceptions.IdDoesntExistException;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * These controllers manage CRUD operations with the Trade object
 * and generate a model for listing, adding and updating
 */

@Controller
public class TradeController {

    @Autowired
    TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.loadAllTrade());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result) {
        if (tradeService.findTradeByID(trade.getTradeId()) == null) {
            if (result.hasErrors()) {
                return "trade/add";
            }
            tradeService.updateTrade(trade);
            return "redirect:/trade/list";
        }
        throw new IdAlreadyExistException(trade.getTradeId());
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findTradeByID(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result) {
        if (tradeService.findTradeByID(id) != null) {
            if (result.hasErrors()) {
                return "trade/update";
            }
            tradeService.updateTrade(trade);
            return "redirect:/trade/list";
        }
        throw new IdDoesntExistException(id);
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        Trade trade = tradeService.findTradeByID(id);
        if (trade == null) {
            throw new IdDoesntExistException(id);
        }
        tradeService.deleteTrade(trade);
        return "redirect:/trade/list";
    }
}
