package com.nnk.springboot.domain;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
   @Id
   Integer id;
   @Column
   Integer curveID;
   @Column
   Timestamp asOfDate;
   @Column
   Double term;
   @Column
   Double value;
   @Column
   Timestamp creationDate;
}
