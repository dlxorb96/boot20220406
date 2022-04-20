package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Immutable // view 인경우 추가, 읽기만 가능 findBy만 된다.
@Table(name = "PRODUCT_VIEW")
public class ProductViewEntity {
    @Id
    @Column(name = "no")
    Long no;

    @Column(length = 250)
    String name;

    Long price;

    Long cnt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp
    // 데이터가 변경될때도 시간이 바뀌게 됨.
    Date uptdate;

}
