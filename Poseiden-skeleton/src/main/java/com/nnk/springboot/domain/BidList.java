package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer bidListId;
    @Column
    @NotBlank(message = "Name of account is mandatory")
    String account;
    @Column
    @NotBlank(message = "Type of account is mandatory")
    String type;
    @Column
    @NotNull(message = "bidQuantity must not be null")
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

    public BidList(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

}
