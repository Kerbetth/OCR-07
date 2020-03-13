package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    Integer id;
    @Column
    String moodysRating;
    @Column
    String sandPRating;
    @Column
    String fitchRating;
    @Column
    Integer orderNumber;
}
