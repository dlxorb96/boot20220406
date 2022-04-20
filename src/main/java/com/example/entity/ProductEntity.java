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

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @JsonBackReference
    // 여기서 toString을 찍으면 계속 반복되는걸 볼 수 있음
    // 이걸 방지하기 위해서 한쪽은 막아야함
    @OneToMany(mappedBy = "product")
    List<ProductCountEntity> list = new ArrayList<>();
    // 새로운 컬럼을 만들게 아니기 때문에 mappedBy로 지정해준다.

    // 이건 원하는대로 조회가 안됨, (페이지네이션, 조회)
    // 없는 게 나을 수도 있다.

}
