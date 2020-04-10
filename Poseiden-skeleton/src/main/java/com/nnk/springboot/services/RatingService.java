package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> loadAllRating() {
        List<Rating> rating = ratingRepository.findAll();
        return rating;
    }
    public Rating findRatingByID(Integer id){
        if (ratingRepository.findById(id).isPresent()) {
            Rating rating = ratingRepository.findById(id).get();
            return rating;
        } else
            log.error("No Rating found with this id");
        return null;
    }

    public void updateRating(Rating rating) {
        ratingRepository.save(rating);
    }
    public void deleteRating(Integer id) {
        ratingRepository.delete(ratingRepository.findById(id).get());
    }
}
