package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;
	private RuleName rating;

	@BeforeEach
	public void setup() {
		rating = new RuleName(10,"test","test","test");
	}

	@Test
	public void bidListSavTest() {
		// Save
		rating = ruleNameRepository.save(rating);
		assertThat(rating.getId()).isNotNull();
		assertThat(rating.getDescription()).isEqualTo("test");
	}

	@Test
	public void bidListUpdateTest() {
		// Update
		rating.setDescription("20d");
		rating = ruleNameRepository.save(rating);
		assertThat(rating.getDescription()).isEqualTo("20d");
	}

	@Test
	public void bidListFindTest() {
		// Find
		rating = ruleNameRepository.save(rating);
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertThat(listResult).hasSizeGreaterThan(0);
	}

	@Test
	public void bidListDeleteTest() {
		// Delete
		rating = ruleNameRepository.save(rating);
		Integer id = rating.getId();
		ruleNameRepository.delete(rating);
		Optional<RuleName> bidList = ruleNameRepository.findById(id);
		assertThat(bidList).isEmpty();
	}
}
