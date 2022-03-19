package com.devyu.jpa.example.domain.forObj;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ORDERS2")
public class Order2 {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID_2")
    private Long id;

    // 연관관계 주인(c,r,u의 역할, 항상 일(一) 대 다(多) 중에서 다로 설정할 것)
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID_2")
    private Member2 member2;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus2 status;

    // 연관관계 거울(r의 역할만 함, 항상 일(一) 대 다(多) 중에서 일로 설정할 것)
    @OneToMany(mappedBy = "order2")
    private List<OrderItem2> orderItems = new ArrayList<>();

}
