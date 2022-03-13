JPA
 - Java Persistence API
 - 자바 진영의 ORM 기술 표준
 - EJB(엔티티 빈)는 과거 자바 표준 ORM이었지만 성능이 워낙 떨어져 Hibernate(오픈소스)가 급부상하면서 자바 진영의 표준을 내어주게 된다.
 
ORM
 - Object-Relational Mapping(객체-관계형 DB 매핑)
 - 객체와 관계형 데이터베이스의 패러다임 불일치를 ORM 프레임워크가 중간에서 매핑
 
 JPA 사용하는 이유
 - 생산성 
 - 객체와 관계형 데이터베이스의 패러다임 불일치를 해결
   - 상속관계  
        - 개발 영역   
        ```jpa.persist(delivery);```  
        - jpa의 영역  
        ```INSERT INTO ORDER ...```  
        ```INSERT INTO DELIVERY ...```
    - 연관관계, 객체 그래프 탐색
        ```
        Member member = jpa.find(Member.class, memberId);
        Team team = member.getTeam();
        ```
 - JPA 성능 최적화 기능
    - 1차 캐시와 동일성 보장
        - 같은 트랙잭션 안에서는 같은 엔티티 반환
    - 트랜잭션을 지원하는 쓰기 지연 (INSERT)
    - 지연 로딩과 즉시 로딩