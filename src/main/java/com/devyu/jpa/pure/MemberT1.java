package com.devyu.jpa.pure;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class MemberT1 {

    @Id
    private Long id;

    private String name;

}
