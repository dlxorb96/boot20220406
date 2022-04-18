package com.example.service;

import java.util.List;

import com.example.entity.ItemEntity;

import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    // 일괄추가
    public int insertItemBatch(List<ItemEntity> list);

    // 수정시 해당하는 항목만 조회하기
    public List<ItemEntity> selectItemEntityIn(Long[] no);

    // 일괄수정
    public int updateItemBatch(List<ItemEntity> list);

    // 일괄수정
    public int deletItemBatch(Long[] no);
}
