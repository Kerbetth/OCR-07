package com.nnk.springboot.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class TradeApiController {

    @Autowired
    TradeService tradeService;


    @PostMapping("/tradeAPI")
    public String validateApi(@Valid @RequestBody Trade trade, BindingResult result) {
        if (result.hasErrors()) {
            return result.toString();
        }
        tradeService.updateTrade(trade);
        return "A new Trade has been saved";
    }

    @GetMapping("/tradeAPI/{id}")
    public String readTrade(@PathVariable("id") Integer id) throws JsonProcessingException {
        if (tradeService.findTradeByID(id) == null) {
            return "No Trade with this ID";
        }
        ObjectMapper Obj = new ObjectMapper();
        return Obj.writeValueAsString(tradeService.findTradeByID(id));
    }

    @PutMapping("/tradeAPI/{id}")
    public String updateTradeApi(@PathVariable("id") Integer id, @Valid @RequestBody Trade trade, BindingResult result) {
        if (tradeService.findTradeByID(id) == null) {
            return "No Trade with this ID";
        } else if (result.hasErrors()) {
            return result.toString();
        } else {
            trade.setTradeId(id);
            tradeService.updateTrade(trade);
            return "Ok";
        }
    }

    @DeleteMapping("/tradeAPI/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        if (tradeService.findTradeByID(id) == null) {
            return "No Trade with this ID";
        }
        tradeService.deleteTrade(id);
        return "Trade " + id + " has been deleted";
    }
}
