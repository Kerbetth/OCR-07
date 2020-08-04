package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * @return all the Trade objects registered in the database
     */
    public List<Trade> loadAllTrade() {
        return tradeRepository.findAll();
    }

    /**
     * @return a specify Trade object according to the Id
     */
    public Trade findTradeByID(Integer id) {
        if (id != null) {
            Optional<Trade> optionalTrade = tradeRepository.findById(id);
            if (optionalTrade.isPresent()) {
                return optionalTrade.get();
            }
        } else
            log.error("No Trade found with id " + id);
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
    public void deleteTrade(Trade trade) {
            tradeRepository.delete(trade);
    }
}
