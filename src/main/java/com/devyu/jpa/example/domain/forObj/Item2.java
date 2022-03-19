package com.devyu.jpa.example.domain.forObj;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item2 {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID_2")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "item2")
    private List<OrderItem2> orderItems = new ArrayList<>();
}
