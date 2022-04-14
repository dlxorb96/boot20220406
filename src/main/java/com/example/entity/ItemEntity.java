package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "ITEM")
@SequenceGenerator(name = "SEQ_ITEM", sequenceName = "SEQ_ITEM_ICODE", allocationSize = 1, initialValue = 1)
public class ItemEntity {
    // 물품코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM")
    private Long icode;
    // 물품명
    private String iname;
    // 물품내용
    @Lob
    private String icontent;
    // 물품가격
    private Long iprice;
    // 재고수량
    private Long iquantity;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp // CURRENT_DATE
    private Date iregdate;
    // 대표이미지
    @Lob
    private byte[] iimage;
    // 이미지크기
    private Long iimagesize;
    // 이미지타입
    private String iimagetype;
    // 이미지명
    private String iimagename;

    // 회원테이블
    // @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "uemail")
    private MemberEntity member;

}
