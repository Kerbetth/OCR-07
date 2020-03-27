package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findAll();
    Optional<Rating> findById(Integer ratingID);
}
