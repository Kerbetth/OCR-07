package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * These controllers manage CRUD operations with the Rating object
 * and generate a model for listing, adding and updating
 */
@Controller
public class RatingController {
    // TODO: Inject Rating service
    @Autowired
    RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        model.addAttribute("ratings", ratingService.loadAllRating());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.updateRating(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        model.addAttribute("rating", ratingService.findRatingByID(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result) {
        if (result.hasErrors()|| ratingService.findRatingByID(id)==null) {
            return "redirect:/rating/list";
        }
        rating.setId(id);
        ratingService.updateRating(rating);
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        if (ratingService.findRatingByID(id)==null) {
            return "redirect:/rating/list";
        }
        ratingService.deleteRating(id);
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        return "redirect:/rating/list";
    }
}
