package com.nnk.springboot.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class RatingApiController {

    @Autowired
    RatingService ratingService;


    @PostMapping("/ratingAPI")
    public String validateApi(@Valid @RequestBody Rating rating, BindingResult result) {
        if (result.hasErrors()) {
            return result.toString();
        }
        ratingService.updateRating(rating);
        return "A new Rating has been saved";
    }

    @GetMapping("/ratingAPI/{id}")
    public String readRating(@PathVariable("id") Integer id) throws JsonProcessingException {
        if (ratingService.findRatingByID(id) == null) {
            return "No Rating with this ID";
        }
        ObjectMapper Obj = new ObjectMapper();
        return Obj.writeValueAsString(ratingService.findRatingByID(id));
    }

    @PutMapping("/ratingAPI/{id}")
    public String updateRatingApi(@PathVariable("id") Integer id, @Valid @RequestBody Rating curve, BindingResult result) {
        if (ratingService.findRatingByID(id) == null) {
            return "No Rating with this ID";
        } else if (result.hasErrors()) {
            return result.toString();
        } else {
            curve.setId(id);
            ratingService.updateRating(curve);
            return "Ok";
        }
    }

    @DeleteMapping("/ratingAPI/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        if (ratingService.findRatingByID(id) == null) {
            return "No Rating with this ID";
        }
        ratingService.deleteRating(id);
        return "Rating " + id + " has been deleted";
    }
}
