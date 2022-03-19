package com.devyu.jpa.example.domain.forObj;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member2 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID_2")
    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipcode;

    // 연관관계 거울(r의 역할만 함, 항상 일(一) 대 다(多) 중에서 일로 설정할 것)
    @OneToMany(mappedBy = "member2")
    private List<Order2> orders = new ArrayList<>();

}
