package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private BidService bidService;
    private BidList bid;
    @Mock
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setup() {
        bid = new BidList("Account Test", "Type Test", 10d);
    }

    @Test
    public void bidListSavTest() {
        // Save
        bidService.updateBidlist(bid);
        assertThat(bid.getBidListId()).isNotNull();
        assertThat(bid.getBidQuantity()).isEqualTo(10d);
    }


}
