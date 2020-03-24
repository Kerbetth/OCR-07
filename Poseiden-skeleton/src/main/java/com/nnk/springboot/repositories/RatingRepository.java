package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findAll();
    Optional<Rating> findById(Integer ratingID);
}
