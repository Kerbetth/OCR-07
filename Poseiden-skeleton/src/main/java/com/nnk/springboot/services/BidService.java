package com.nnk.springboot.services;

import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.BidList;

import java.util.List;

/**
 * BidService manage CRUD service for BidList Object
 */

@Service
@Slf4j
public class BidService {

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * @return all the BidList objects registered in the database
     */
    public List<BidList> loadAllBidList() {
        return bidListRepository.findAll();
    }

    /**
     * @return a specify BidList object according to the Id
     */
    public BidList findBidListbyID(Integer integer) {
        if (integer != null) {
            if (bidListRepository.findById(integer).isPresent()) {
                return bidListRepository.findById(integer).get();
            }
        } else
            log.error("No BidList found with this id");
        return null;
    }

    /**
     * update a bidList in the database
     */
    public void updateBidlist(BidList bidList) {
        bidListRepository.save(bidList);
    }

    /**
     * delete a bidList in the database
     * @param bidList
     */
    public void deleteBidlist(BidList bidList) {
        bidListRepository.delete(bidList);
    }
}
