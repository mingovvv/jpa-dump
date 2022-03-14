package com.devyu.jpa.pure;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Member {

    @Id
    private Long id;

    private String name;

}
