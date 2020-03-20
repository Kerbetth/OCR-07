package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
    List<RuleName> findAll();
    RuleName findFirstByid(Integer ratingID);
}
