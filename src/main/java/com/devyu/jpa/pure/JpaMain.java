package com.devyu.jpa.pure;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Slf4j
public class JpaMain {

    public static void main(String[] args) {
        // EntityManagerFactory는 하나만 생성해서 애플리케이션 전체에 공유함
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

        // Thread간 공유하지 않고 사용 후 자원 회수해야 함
        EntityManager em = emf.createEntityManager(); // db connection을 물고 있음

        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행되어야 함함
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // [insert]
//            Member member = new Member();
//            member.setId(1L);
//            em.persist(member);

            // [select]
            Member findMember = em.find(Member.class, 1L);
            log.info("findMember id = {}, name = {}", findMember.getId(), findMember.getName());

            findMember.setName("change1");
//            em.flush(); 준영속 상태로 바뀌기전에 flush를 호출하고 더티체킹 & 쓰기지연 SQL 저장소 등록 & DB SQL 전달까지 완료해버림
            em.detach(findMember);

            // [update]
//            findMember.setName("devyu"); // java collection을 다루는 것과 유사함. 더티체킹

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

}
