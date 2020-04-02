package com.nnk.springboot.services;

import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.BidList;

import java.util.List;

@Service
@Slf4j
public class BidService {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> loadAllBidList() {
        List<BidList> bidLists = bidListRepository.findAll();
        return bidLists;
    }
    public BidList findBidListbyID(Integer integer) {
        if (bidListRepository.findById(integer).isPresent()) {
            BidList bidList = bidListRepository.findById(integer).get();
            return bidList;
        } else
            log.error("No BidList found with this id");
        return null;
    }

    public void updateBidlist(BidList bidList) {
        bidListRepository.save(bidList);
    }

    public void deleteBidlist(Integer id) {
        bidListRepository.delete(bidListRepository.findById(id).get());
    }
}
