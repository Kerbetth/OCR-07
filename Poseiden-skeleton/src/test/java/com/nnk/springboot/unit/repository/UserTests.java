package com.nnk.springboot.unit.repository;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class UserTests {

	@Autowired
	private TradeRepository tradeRepository;
	private Trade trade;

	@BeforeEach
	public void setup() {
		trade = new Trade(10,"test","test",5d);
	}

	@Test
	public void bidListSavTest() {
		// Save
		trade = tradeRepository.save(trade);
		assertThat(trade.getTradeId()).isNotNull();
		assertThat(trade.getAccount()).isEqualTo("test");
	}

	@Test
	public void bidListUpdateTest() {
		// Update
		trade.setType("20d");
		trade = tradeRepository.save(trade);
		assertThat(trade.getType()).isEqualTo("20d");
	}

	@Test
	public void bidListFindTest() {
		// Find
		trade = tradeRepository.save(trade);
		List<Trade> listResult = tradeRepository.findAll();
		assertThat(listResult).hasSizeGreaterThan(0);
	}

	@Test
	public void bidListDeleteTest() {
		// Delete
		trade = tradeRepository.save(trade);
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> trades = tradeRepository.findById(id);
		assertThat(trades).isEmpty();
	}
}
