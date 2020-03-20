package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> loadAllRating() throws UsernameNotFoundException {
        List<Rating> rating = ratingRepository.findAll();
        return rating;
    }
    public Rating findBidListbyID(Integer integer) throws UsernameNotFoundException {
        Rating rating = ratingRepository.findFirstByid(integer);
        return rating;
    }
    public void updateBidlist(Rating rating) throws UsernameNotFoundException {
        ratingRepository.save(rating);
    }
    public void deleteBidlist(Integer id) throws UsernameNotFoundException {
        ratingRepository.delete(ratingRepository.findFirstByid(id));
    }
}
