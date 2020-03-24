package com.nnk.springboot.services;

import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.BidList;
import java.util.List;

@Service
public class BidService {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> loadAllBidList() {
        List<BidList> bidLists = bidListRepository.findAll();
        return bidLists;
    }
    public BidList findBidListbyID(Integer integer){
        BidList bidList = bidListRepository.findById(integer).get();
        return bidList;
    }
    public void updateBidlist(BidList bidList) {
        bidListRepository.save(bidList);
    }

    public void deleteBidlist(Integer id) {
        bidListRepository.delete(bidListRepository.findById(id).get());
    }
}
