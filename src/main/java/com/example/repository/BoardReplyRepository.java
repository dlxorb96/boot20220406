package com.example.repository;

import java.util.List;

import com.example.entity.BoardReplyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Long> {

    List<BoardReplyEntity> findByBoard_noOrderByNoDesc(long bno);
}
