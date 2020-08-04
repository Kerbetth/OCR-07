package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column
    @NotEmpty(message = "name Must not be null")
    String name;
    @Column
    @NotEmpty(message = "description Must not be null")
    String description;
    @Column
    @NotEmpty(message = "json Must not be null")
    String json;
    @Column
    String template;
    @Column
    String sqlStr;
    @Column
    String sqlPart;

    public RuleName(Integer id, String name, String description, String json) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.json = json;
    }
}
