package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer tradeId;
    @Column
    @NotEmpty(message = "account Must not be null")
    String account;
    @Column
    @NotEmpty(message = "type Must not be null")
    String type;
    @Column
    @NotNull(message = "buyQuantity Must not be null")
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

    public Trade(Integer id, String account, String type, Double buyQuantity) {
        this.tradeId = id;
        this.account = account;
        this.type = type;
        this.buyQuantity=buyQuantity;
    }
}
