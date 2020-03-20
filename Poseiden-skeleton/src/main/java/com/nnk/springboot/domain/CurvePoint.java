package com.nnk.springboot.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column
    @NotEmpty(message = "curveID Must not be null")
    Integer curveID;
    @Column
    Timestamp asOfDate;
    @Column
    @NotNull(message = "term Must not be null")
    Double term;
    @Column
    @NotNull(message = "Value Must not be null")
    Double value;
    @Column
    Timestamp creationDate;

    public CurvePoint(int id, double curveID, double value) {
    }
}
