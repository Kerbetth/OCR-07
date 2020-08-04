package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.exceptions.IdAlreadyExistException;
import com.nnk.springboot.controllers.exceptions.IdDoesntExistException;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
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
    @Autowired
    RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
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
        if (ratingService.findRatingByID(rating.getId()) == null) {
            if (result.hasErrors()) {
                return "rating/add";
            }
            ratingService.updateRating(rating);
            return "redirect:/rating/list";
        }
        throw new IdAlreadyExistException(rating.getId());
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rating", ratingService.findRatingByID(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result) {
        if (ratingService.findRatingByID(id) != null) {
            if (result.hasErrors()) {
                return "rating/update";
            }
            ratingService.updateRating(rating);
            return "redirect:/rating/list";
        }
        throw new IdDoesntExistException(id);
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        Rating rating = ratingService.findRatingByID(id);
        if (rating == null) {
            throw new IdDoesntExistException(id);
        }
        ratingService.deleteRating(rating);
        return "redirect:/rating/list";
    }
}
