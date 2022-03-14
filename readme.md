### JPA
 - Java Persistence API
 - 자바 진영의 ORM 기술 표준
 - EJB(엔티티 빈)는 과거 자바 표준 ORM이었지만 성능이 워낙 떨어져 Hibernate(오픈소스)가 급부상하면서 자바 진영의 표준을 내어주게 된다.
 
### ORM
 - Object-Relational Mapping(객체-관계형 DB 매핑)
 - 객체와 관계형 데이터베이스의 패러다임 불일치를 ORM 프레임워크가 중간에서 매핑
 
### JPA 사용하는 이유
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
----
### JPA에 대한 이해
#### 영속성 컨텍스트
 - `Entity` 를 영구 저장하는 환경을 의미
 - `EntityManagerFactory`는 client의 요청 당 하나의 `EntityManager`를 생성하고 `EntityManager`는 DB connection을 맺어 DB관련 처리를 수행한다.
 - `EntityManager` 를 통해 논리적인 개념인 영속성 컨텍스트에 접근한다.

#### 엔티티의 생명주기
 - 비영속(new/transient)
    - 영속성 컨텍스트에 접근하지 못한 상태
    ```
   // 객체가 생성까지만 된 상태
   Member m = new Member();
   m.setId(1L);
   m.setName("devyu");
   ```
 - 영속(managed)
    ```
    // 객체가 생성까지만 된 상태
    Member m = new Member();
    m.setId(1L);
    m.setName("devyu");
   
    EntityManager em = emf.createEntityManager();
    em.getTransaction.begin();
    em.persist(m); // 영속상태
    em.commit(); // 트랜잭션 커밋되는 순간에 SQL이 실행된다.
    ```
 - 준영속(detached)
     ```
     // 객체가 생성까지만 된 상태
     Member m = new Member();
     m.setId(1L);
     m.setName("devyu");
    
     EntityManager em = emf.createEntityManager();
     em.getTransaction.begin();
     em.persist(m); // 영속상태
     em.detach(m); // 준영속상태(회원 엔티티를 영속성 컨텍스트에서 분리)
     ```
 - 삭제(removed)
 
 #### 영속성 컨텍스트가 존재하는 이유
 - 1차 캐시
     ```
     // 비영속상태, 객체가 생성까지만 된 상태
     Member m = new Member();
     m.setId(1L);
     m.setName("devyu");
    
     EntityManager em = emf.createEntityManager();
     em.getTransaction.begin();
     em.persist(m); // 영속상태 -> 1차 캐시에 맵(Map)타입으로 저장됨(key : pk, value : entity)
     
     // db가 아니라 먼저 1차 캐시에서 조회해서 반환
     Member findMember1 = em.find(Member.class, "1L");
   
     // 1차 캐시에 없으므로 db를 조회하고 1차 캐시에 저장해놓고 반환
     Member findMember2 = em.find(Member.class, "2L");
     
     > 보통 트랜잭션 단위로 영속성컨텍스트(1차 캐시)가 생성되므로 사실 성능의 이점을 얻지는 못함.
     ```
    - 영속성 컨텍스트는 내부에 1차캐시가 존재한다.
    - 
 - 영속된 엔티티의 동일성 보장
 - 트랜잭션을 지원하는 쓰기 지연
    - 영속성 컨텍스트 내부에 쓰기 지연 SQL저장소가 존재함
    - `commit()` 을 하는 순간 쓰기 지연 SQL저장소에 쌓여있던 SQL이 `flush` 된다.
    ```
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
   
    //엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
    transaction.begin();
   
    em.persist(memberA);
    em.persist(memberB);
    //여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
   
    //커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
    transaction.commit();
    ```
 - 변경감지(dirty cheching)
    - 변경감지는 `flush()` 가 호출되면 1차 캐시에 저장되어 있는 Entity와 Snapshot을 비교하여 변경된 부분이 있는지 검사하고 변경된 것이 있으면 업데이트
    ```
   EntityManager em = emf.createEntityManager();
   EntityTransaction transaction = em.getTransaction();
   
   transaction.begin();
   
   // 영속 엔티티 조회
   Member memberA = em.find(Member.class, "memberA");
   // 영속 엔티티 데이터 수정
   memberA.setUsername("hi");
   memberA.setAge(10);
   //em.update(member) 이런 코드가 필요없다. 자바 컬렉션 다루듯이...
   
   transaction.commit();
    ```
 - 지연로딩
 
 #### flush
 - 영속성 컨텍스트의 변경내용을 DB에 전달(commit 전까지 실제로 변경되진 않음)
 - 보통 DB 트랜잭션이 commit 될 때, flush가 호출됨
 - 변경감지 / 수정된 엔티티 쓰기지연 SQL 저장소 등록 -> 쓰기 지연 SQL 저장소에 등록된 쿼리를 DB에 전달
 - 언제 flush 되니?
    - `emtityManager.flush()`, 직접호출
    - 트랜잭션 커밋, 자동호출
    - JPQL 쿼리 실행, 자동호출
 - flush()는 영속성 컨텍스트의 1차 캐시를 비우는 것이 아니라 쓰기 지연 SQL 저장소에 쌓여있는 SQL을 반영시키는 역할을 하는 것...