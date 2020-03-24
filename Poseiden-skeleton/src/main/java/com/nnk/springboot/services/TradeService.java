package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> loadAllTrade() {
        List<Trade> rating = tradeRepository.findAll();
        return rating;
    }
    public Trade findTradebyID(Integer integer){
        Trade trade = tradeRepository.findById(integer).get();
        return trade;
    }
    public void updateTrade(Trade trade) {
        tradeRepository.save(trade);
    }
    public void deleteTrade(Integer id) {
        tradeRepository.delete(tradeRepository.findById(id).get());
    }
}
