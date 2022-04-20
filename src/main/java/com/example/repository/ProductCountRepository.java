package com.example.repository;

import com.example.entity.ProductCountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCountRepository extends JpaRepository<ProductCountEntity, Long> {

    @Query(value = "SELECT SUM(CNT) FROM PRODUCTCOUNT WHERE PRODUCT_NO=:no GROUP BY PRODUCT_NO", nativeQuery = true)
    public long selectProductCountGroup(@Param(value = "no") Long productNo);

    // native에서 inset, update, delete은 데이터의 변화가 생김. >> modifying 어노테이션을 붙여줘야 작동함.
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM PRODUCT WHERE CNT>= :cnt", nativeQuery = true)
    public long deleteProductCountGroup(@Param(value = "cnt") Long cnt);

    @Modifying(clearAutomatically = true)
    int deleteByCnt(long cnt);

    // findBy... SELECT

    // deleteBy... DELETE

    // INSERT, UPDATE는 사용x
}
