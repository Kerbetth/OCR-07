package com.nnk.springboot.unit.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BidTests {

    @Autowired
    private BidListRepository bidListRepository;
    private BidList bid;

    @BeforeEach
    public void setup() {
        bid = new BidList("Account Test", "Type Test", 10d);
    }

    @Test
    public void bidListSavTest() {
        // Save
        bid = bidListRepository.save(bid);
        assertThat(bid.getBidListId()).isNotNull();
        assertThat(bid.getBidQuantity()).isEqualTo(10d);
    }

    @Test
    public void bidListUpdateTest() {
        // Update
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        assertThat(bid.getBidQuantity()).isEqualTo(20d);
    }

    @Test
    public void bidListFindTest() {
        // Find
        bid = bidListRepository.save(bid);
        List<BidList> listResult = (List<BidList>) bidListRepository.findAll();
        assertThat(listResult).hasSizeGreaterThan(0);
    }

    @Test
    public void bidListDeleteTest() {
        // Delete
        bid = bidListRepository.save(bid);
        Integer id = bid.getBidListId();
        bidListRepository.delete(bid);
        Optional<BidList> bidList = bidListRepository.findById(id);
        assertThat(bidList).isEmpty();
    }
}
