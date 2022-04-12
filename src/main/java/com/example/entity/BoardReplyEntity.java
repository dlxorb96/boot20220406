
package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "BOARD10_REPLY") // 테이블명
@SequenceGenerator(name = "SEQ1", sequenceName = "SEQ_BOARD10_REPLY_RNO", allocationSize = 1, initialValue = 1) // 생성할
                                                                                                                // 시퀀스
public class BoardReplyEntity {

    @Id
    @Column(name = "RNO") // 기본키, 테이블명 RNO, 시퀀스 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ1")
    private long no;

    @Column(name = "RCONTENT", length = 300) // VARCHAR2(300)
    private String content;

    @Column(name = "RWRITER", length = 50) // VARCHAR(50)
    private String writer;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp // CURRENT_DATE
    @Column(name = "RREGDATE")
    private Date regdate;

    @ToString.Exclude
    @ManyToOne // 외래키 BOARD10테이블의 기본키 컬럼만..
    @JoinColumn(name = "BOARD")
    private BoardEntity board;

}
