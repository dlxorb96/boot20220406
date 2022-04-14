package com.example.entity;

import java.util.Date;

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

@Entity
@Data
@Table(name = "BUY")

@SequenceGenerator(name = "SEQ_BUY", sequenceName = "SEQ_BUY_NO", allocationSize = 1, initialValue = 1)
public class BuyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BUY")
    private long bno;

    private long bcnt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date bregdate;

    @ManyToOne
    // @JsonBackReference
    @JoinColumn(name = "uemail")
    private MemberEntity member;

    // 물품테이블
    @ManyToOne
    // @JsonBackReference
    @JoinColumn(name = "icode")
    private ItemEntity item;
}
