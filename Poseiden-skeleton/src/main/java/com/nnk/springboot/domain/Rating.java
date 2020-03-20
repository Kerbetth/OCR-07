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
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column
    String moodysRating;
    @Column
    String sandPRating;
    @Column
    String fitchRating;
    @Column
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, int id) {
        this.moodysRating = moodysRating;
        this.id = id;
        this.fitchRating = fitchRating;
        this.sandPRating = sandPRating;
    }
}
