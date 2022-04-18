package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "PRODUCT")
@Data
@SequenceGenerator(name = "SEQ_3", sequenceName = "SEQ_PRO_NO", allocationSize = 1, initialValue = 1001)
// 메모리를 통해 할당할 범위 사이즈, 시작값
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_3")
    Long no;

    @Column(length = 250)
    String name;

    Long price;

    @Lob
    // 널을 포함하려면
    @Column(nullable = true)
    byte[] imagedata;

    String imagename;

    String imagetype;

    Long imagesize = 0L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp
    // 데이터가 변경될때도 시간이 바뀌게 됨.
    Date uptdate;
}
