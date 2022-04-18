package com.example.repository;

import java.util.List;

import com.example.entity.ItemEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    // 항목 들어있는 거 조회
    List<ItemEntity> findByIcodeIn(Long[] no);
}
