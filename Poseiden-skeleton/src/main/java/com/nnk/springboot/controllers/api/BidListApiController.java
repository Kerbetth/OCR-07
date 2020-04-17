package com.nnk.springboot.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class BidListApiController {

    @Autowired
    BidService bidService;


    @PostMapping("/bidListAPI")
    public String validateApi(@Valid @RequestBody BidList bid, BindingResult result) {
        if (result.hasErrors()) {
            return result.toString();
        }
        bidService.updateBidlist(bid);
        return "A new BidList has been saved";
    }

    @GetMapping("/bidListAPI/{id}")
    public BidList readBid(@PathVariable("id") Integer id) {
        return bidService.findBidListbyID(id);
    }

    @PutMapping("/bidListAPI/{id}")
    public String updateBidApi(@PathVariable("id") Integer id, @Valid @RequestBody BidList bidList, BindingResult result) {
        if (bidService.findBidListbyID(id) == null) {
            return "No bibList with this ID";
        } else if (result.hasErrors()) {
            return result.toString();
        } else {
            bidList.setBidListId(id);
            bidService.updateBidlist(bidList);
            return "Ok";
        }
    }

    @DeleteMapping(value = "/bidListAPI/{id}")
    public String deleteBid(@PathVariable Integer id) {
        if (bidService.findBidListbyID(id) == null) {
            return "No bibList with this ID";
        }
        bidService.deleteBidlist(id);
        return id + " has been deleted";
    }
}
