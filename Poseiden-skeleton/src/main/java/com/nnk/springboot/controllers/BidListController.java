package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.exceptions.IdAlreadyExistException;
import com.nnk.springboot.controllers.exceptions.IdDoesntExistException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * These controllers manage CRUD operations with the BidList object
 * and generate a model for listing, adding and updating
 */

@Controller
public class BidListController {

    // TODO: Inject Bid service
    @Autowired
    BidService bidService;

    @RequestMapping("/bidList/list")
    public String listAllBidlists(Model model) {
        // TODO: call service find all bids to show to the view
        model.addAttribute("bidLists", bidService.loadAllBidList());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result) {
        if (bidService.findBidListbyID(bidList.getBidListId()) == null) {
            if (result.hasErrors()) {
                return "bidList/add";
            }
            bidService.updateBidlist(bidList);
            return "redirect:/bidList/list";
        }
        throw new IdAlreadyExistException(bidList.getBidListId());
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = bidService.findBidListbyID(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result) {
        if (bidService.findBidListbyID(id) != null) {
            if (result.hasErrors()) {
                return "bidList/update";
            }
            bidService.updateBidlist(bidList);
            return "redirect:/bidList/list";
        }
        throw new IdDoesntExistException(id);
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        BidList bidList = bidService.findBidListbyID(id);
        if (bidList == null) {
            throw new IdDoesntExistException(id);
        }
        bidService.deleteBidlist(bidList);
        return "redirect:/bidList/list";
    }
}
