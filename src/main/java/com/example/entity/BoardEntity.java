package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "BOARD10") // 테이블 명

// 생성할 시퀀스
@SequenceGenerator(name = "SEQ_BOARD", sequenceName = "SEQ_BOARD10_BNO", initialValue = 1, allocationSize = 1)
public class BoardEntity {

    @Id // 기본키
    @Column(name = "BNO") // 컬럼명
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD")
    // 컬럼명을 생략하면 변수명과 같아진다
    private Long no; // 타입변수

    @Column(name = "BTITLE", length = 200) // varchar2(200)
    private String title;

    @Lob // CLOB
    @Column(name = "BCONTENT") // varchar2(200)
    private String content;

    @Column(name = "BWRITER", length = 100)
    private String writer;

    @Column(name = "BHIT")
    private Long hit = 1L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp // CURRENT_DATE가 default로 잡힌다.
    @Column(name = "BREGDATE")
    private Date regdate;

    @OneToMany(mappedBy = "board")
    private List<BoardReplyEntity> replyList = new ArrayList<>();

}
