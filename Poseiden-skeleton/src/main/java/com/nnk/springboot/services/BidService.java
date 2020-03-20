package com.nnk.springboot.services;

import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.BidList;
import java.util.List;

@Service
public class BidService {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> loadAllBidList() throws UsernameNotFoundException {
        List<BidList> bidLists = bidListRepository.findAll();
        return bidLists;
    }
    public BidList findBidListbyID(Integer integer) throws UsernameNotFoundException {
        BidList bidList = bidListRepository.findFirstByBidListId(integer);
        return bidList;
    }
    public void updateBidlist(BidList bidList) throws UsernameNotFoundException {
        bidListRepository.save(bidList);
    }

    public void deleteBidlist(Integer id) throws UsernameNotFoundException {
        bidListRepository.delete(bidListRepository.findFirstByBidListId(id));
    }
}
