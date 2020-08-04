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

    /**
     * @return all the Rating objects registered in the database
     */
    public List<Rating> loadAllRating() {
        return ratingRepository.findAll();
    }

    /**
     * @return a specify RuleName object according to the Id
     */
    public Rating findRatingByID(Integer id) {
        if (id != null) {
            if (ratingRepository.findById(id).isPresent()) {
                return  ratingRepository.findById(id).get();
            }
        } else
            log.error("No Rating found with this id");
        return null;
    }

    /**
     * update a RuleName in the database
     */
    public void updateRating(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     * delete a Rating in the database
     * @param rating
     */
    public void deleteRating(Rating rating) {
        ratingRepository.delete(rating);
    }
}
