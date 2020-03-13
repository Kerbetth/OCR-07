package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Table(name = "trade")
public class Trade {
    @Id
    Integer tradeId;
    @Column
    String account;
    @Column
    String type;
    @Column
    Double buyQuantity;
    @Column
    Double sellQuantity;
    @Column
    Double butPrice;
    @Column
    String benchmark;
    @Column
    Timestamp tradeDate;
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
