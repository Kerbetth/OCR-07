package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TradeRepository extends JpaRepository<Trade, Integer> {
    List<Trade> findAll();
    Trade findFirstBytradeId(Integer ratingID);
}
