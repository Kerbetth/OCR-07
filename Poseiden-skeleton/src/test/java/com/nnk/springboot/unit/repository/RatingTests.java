package com.nnk.springboot.unit.repository;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;
	private Rating rating;

	@BeforeEach
	public void setup() {
		rating = new Rating("10", "10d", "10d", 2);
	}

	@org.junit.jupiter.api.Test
	public void bidListSavTest() {
		// Save
		rating = ratingRepository.save(rating);
		assertThat(rating.getId()).isNotNull();
		assertThat(rating.getSandPRating()).isEqualTo("10d");
	}

	@org.junit.jupiter.api.Test
	public void bidListUpdateTest() {
		// Update
		rating.setFitchRating("20d");
		rating = ratingRepository.save(rating);
		assertThat(rating.getFitchRating()).isEqualTo("20d");
	}

	@org.junit.jupiter.api.Test
	public void bidListFindTest() {
		// Find
		rating = ratingRepository.save(rating);
		List<Rating> listResult = ratingRepository.findAll();
		assertThat(listResult).hasSizeGreaterThan(0);
	}

	@Test
	public void bidListDeleteTest() {
		// Delete
		rating = ratingRepository.save(rating);
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> bidList = ratingRepository.findById(id);
		assertThat(bidList).isEmpty();
	}
}
