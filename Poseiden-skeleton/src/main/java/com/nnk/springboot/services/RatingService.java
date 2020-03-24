package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> loadAllRating() {
        List<Rating> rating = ratingRepository.findAll();
        return rating;
    }
    public Rating findBidListbyID(Integer integer){
        Rating rating = ratingRepository.findById(integer).get();
        return rating;
    }
    public void updateRating(Rating rating) {
        ratingRepository.save(rating);
    }
    public void deleteRating(Integer id) {
        ratingRepository.delete(ratingRepository.findById(id).get());
    }
}
