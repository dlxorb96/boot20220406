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

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "PRODUCTCOUNT")
@SequenceGenerator(name = "SEQ_5", sequenceName = "SEQ_PROCOUNT_NO", allocationSize = 1, initialValue = 1)
// 메모리를 통해 할당할 범위 사이즈, 시작값
public class ProductCountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_5")
    Long no;

    Long cnt; // 입고 시 10, 출고 시 -5

    @Column(length = 10)
    String type; // 입고는 I, 출고는 O

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp
    // 데이터가 변경될때도 시간이 바뀌게 됨.
    Date regdate;

    @ManyToOne // 외래키
    @JoinColumn(name = "PRODUCT_NO", referencedColumnName = "NO")
    ProductEntity product;
}
