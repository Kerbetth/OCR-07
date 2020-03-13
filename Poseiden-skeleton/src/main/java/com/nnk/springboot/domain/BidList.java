package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    Integer BidListId;
    @Column
    String account;
    @Column
    String type;
    @Column
    Double bidQuantity;
    @Column
    Double askQuantity;
    @Column
    Double bid;
    @Column
    Double ask;
    @Column
    String benchmark;
    @Column
    Timestamp bidListDate;
    @Column
    String commentary;
    @Column
    String security;
    @Column
    String status;
    @Column
    String trader;
    @Column
    String book;
    @Column
    String creationName;
    @Column
    Timestamp creationDate;
    @Column
    String revisionName;
    @Column
    Timestamp revisionDate;
    @Column
    String dealName;
    @Column
    String dealType;
    @Column
    String sourceListId;
    @Column
    String side;
}
