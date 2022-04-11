package com.example.repository;

import java.util.List;

import com.example.entity.BoardEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 엔티티, 기본키 타입

    // findeBy컬럼명Containing
    // WHERE TITLE LIKE '%' || '검색어' || '%'
    List<BoardEntity> findByTitleContaining(String title);

    // findBy컬럼명ContainingOrderByNoDesc
    // SELECT * FROM 테이블명
    // WHERE TITLE LIST '%' || '검색어' || '%' ORDER BY NO DESC
    List<BoardEntity> findByTitleContainingOrderByNoDesc(String title);

    List<BoardEntity> findByTitleLike(String title);

    @Query(value = " SELECT * FROM BOARD10 B WHERE BTITLE LIKE %:ti% ", nativeQuery = true)
    List<BoardEntity> selectBoardList(@Param(value = "ti") String title);

    BoardEntity findDistinctByNo(Long no);

    // 검색어가 포함된 전체 개수
    // SELECT COUNT(*) FROM BOARD10 WHERE TITLE LIKE '%' || '검색어' || '%'
    long countByTitleContaining(String title);

    // SELECT B.*, ROW_NUMBER() OVER( ORDER BY BNO DESC) FROM 테이블명
    // SELECT COUNT(*) FROM BOARD10 WHERE TITLE LIKE '%' || '검색어' || '%'
    // ORDER BY BNO DESC
    // ROW_NUMBER
    List<BoardEntity> findByTitleContainingOrderByNoDesc(String title, Pageable pageable);

    // 이전 글 ex)20 이면 작은 것중에서 가장 큰 값 19
    BoardEntity findTop1ByNoLessThanOrderByNoDesc(long no);

    // 다음 글 ex)20이면 큰 것중에서 가장 작은것 1개
    BoardEntity findTop1ByNoGreaterThanOrderByNoAsc(long no);
}
