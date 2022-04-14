package com.example.repository;

import java.util.List;

import com.example.entity.BuyEntity;
import com.example.entity.BuyProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRepository extends JpaRepository<BuyEntity, Long> {

    BuyProjection findByBno(Long Bno);

    // 고객용 주문내역
    List<BuyProjection> findByMember_uemail(String uemail);

    // 판매자용 주문내역
    List<BuyProjection> findByItem_icodeIn(List<Long> code);

    // SELECT * FROM 테이블명 WHERE 1 ORDER BY BNO ASC
    List<BuyProjection> findByOrderByBnoAsc();

    // SELECT * FROM 테이블 명 WHERE BCNT >= ?
    List<BuyProjection> findByBcntGreaterThanEqual(long bcnt);

    // SELECT * FROM 테이블명 WHERE BNO=? AND BCNT=?
    BuyProjection findByBnoAndBcnt(Long bno, Long bcnt);

    // SELECT * FROM 테이블명 WHERE BNO IN (1,3,5)
    // SELECT * FROM 테이블명 WHERE BNO = 1 OR BNO=3 OR BNO =5
    List<BuyProjection> findByBnoIn(List<Long> bno);

    // native는 오라클 ,mybatis에 따라 달라짐
    // jpa로 할 때 native는 비권장이다
    @Query(value = "SELECT * FROM BUY", nativeQuery = true)
    public List<BuyProjection> selectBuyList();

    @Query(value = "SELECT * FROM BUY WHERE BNO=:no", nativeQuery = true)
    public BuyProjection selectBuy(@Param(value = "no") long bno);

    // #{obj.bno} == JPA+hibernate :#{#obj.bno}
    @Query(value = "SELECT * FROM BUY WHERE BNO=:#{#obj.bno} AND BCNT= :#{#obj.bcnt}", nativeQuery = true)
    public BuyProjection selectBuyOneAnd(@Param(value = "obj") BuyEntity buy);

}
