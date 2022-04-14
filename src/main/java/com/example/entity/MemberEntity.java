package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "MEMBER")
public class MemberEntity {

    // 이메일
    @Id
    private String uemail;
    // 암호
    @Column(nullable = false)
    private String upw;
    // 이름
    private String uname;
    // 전화번호
    private String uphone;
    // 권한
    private String urole;
    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date uregdate;
    // 회원주소테이블

}
