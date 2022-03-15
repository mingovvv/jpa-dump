package com.devyu.jpa.pure_2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    /**
     * @GeneratedValue(strategy = GenerationType.AUTO)       : db dialect에 따라 자동 생성 (오라클이면 시퀀스, mysql이라면 auto_increment ..) (default)
     * @GeneratedValue(strategy = GenerationType.IDENTITY)   : 기본 키 생성을 db에 위임, mysql
     *                                                         사실 db에 값이 들어가봐야 pk의 값을 알 수 있음.
     *                                                         영속성 컨텍스트(1차 캐시)에서 관리되려면 pk값이 필요하잖아?..
     *                                                         -> IDENTITY 전략만 예외적으로 영속상태가 되자마자 db query가 수행된다.
     *                                                         -> jpa가 내부적으로 pk값을 select 해와서 영속성 컨텍스트(1차 캐시)에 저장한다.
     *                                                         -> 쓰기지연 SQL 저장소의 기능을 사용하지 못함 (사실 버퍼링 기능이 큰 장점은 아니니깐..)
     * @GeneratedValue(strategy = GenerationType.SEQUENCE)   : db 시퀀스 오브젝트 사용, oracle, Entity 레벨에서 @SequenceGenerator 필요
     * @GeneratedValue(strategy = GenerationType.TABLE)      : 키 생성 전용 테이블을 만들어 시퀀스를 흉내내는 전략, Entity 레벨에서 @TableGenerator 필요
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * insertable, updatable    : 등록과 변경 가능 여부 (default : true)
     * updatable                : null 값의 허용 여부를 설정, false의 경우 DDL 생성 시에 not null 제약조건 추가
     * unique                   : unique 제약조건 여부
     * columnDefinition         : 컬럼을 직접 정의
     */
    @Column(name = "name", insertable = true, updatable = false, nullable = false, unique = false, columnDefinition = "varchar(100)") // db column name
    private String username;

    private Integer age;

    /**
     * EnumType.ORDINAL     : enum의 순서를 저장(기본 값)
     * EnumType.STRING      : enum의 name()을 저장(권장)
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDateTime createdDate2;

    private LocalDateTime lastModifiedDate2;

    @Lob
    private String description;

    @Transient // 필드 매핑 X
    private String gender;

}
