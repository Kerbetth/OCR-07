package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;
    
    /**
     * @return all the Trade objects registered in the database
     */
    public List<Trade> loadAllTrade() {
        List<Trade> rating = tradeRepository.findAll();
        return rating;
    }
    
    /**
     * @return a specify Trade object according to the Id
     */
    public Trade findTradeByID(Integer id){
        if (tradeRepository.findById(id).isPresent()) {
            Trade trade = tradeRepository.findById(id).get();
            return trade;
        } else
            log.error("No Trade found with this id");
        return null;
    }
    
    /**
     * update a Trade in the database
     */
    public void updateTrade(Trade trade) {
        tradeRepository.save(trade);
    }
    
    /**
     * delete a Trade in the database
     */
    public void deleteTrade(Integer id) {
        tradeRepository.delete(tradeRepository.findById(id).get());
    }
}
