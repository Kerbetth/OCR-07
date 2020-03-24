package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TradeRepository extends JpaRepository<Trade, Integer> {
    List<Trade> findAll();
    Optional<Trade> findById(Integer ratingID);
}
