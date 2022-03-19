package com.devyu.jpa.example.domain.forObj;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem2 {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID_2")
    private Long id;

    // 연관관계 주인(c,r,u의 역할, 항상 일(一) 대 다(多) 중에서 다로 설정할 것)
    @ManyToOne
    @JoinColumn(name = "ORDER_ID_2")
    private Order2 order2;

    // 연관관계 주인(c,r,u의 역할, 항상 일(一) 대 다(多) 중에서 다로 설정할 것)
    @ManyToOne
    @JoinColumn(name = "ITEM_ID_2")
    private Item2 item2;

    private int orderPirce;

    private int count;

}
