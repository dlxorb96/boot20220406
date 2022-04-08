package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.dto.MemberDTO;
import com.example.jwt.JwtUtil;
import com.example.mapper.MemberMapper;
import com.example.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// backend만 구현함, 화면구현 x , vue, react와 연동

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    MemberMapper mMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    // 암호변경(토큰, 현재암호, 변경암호)
    @RequestMapping(value = "/updatepw", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatepw(@RequestParam(name = "upw") String upw,
            @RequestParam(name = "pw1") String pw1,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        System.out.println(pw1);
        String username = jwtUtil.extractUsername(token);
        // syso

        UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

        if (bcpe.matches(upw, user.getPassword())) {
            int ret = mMapper.updatePw(username, bcpe.encode(pw1));
            if (ret == 1) {
                map.put("status", 200);
            }
        }

        return map;
    }

    // 회원정보수정(토큰,이름,전화번호)
    @RequestMapping(value = "/updateNamePhone", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateNamePhone(@RequestBody MemberDTO member,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        System.out.println(member);
        String username = jwtUtil.extractUsername(token);

        int ret = mMapper.updateNamePhone(username, member);
        if (ret == 1) {
            map.put("status", 200);

        }

        return map;

    }

    // 회원탈퇴 update 중요정보 내용만 지우기(토큰, 현재암호, 아이디를 제외한 내용 지우기)
    @RequestMapping(value = "/deleteMember", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteMember(@RequestParam(name = "upw") String upw,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        String username = jwtUtil.extractUsername(token);

        UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        System.out.println(username);
        System.out.println(upw);
        if (bcpe.matches(upw, user.getPassword())) {
            int ret = mMapper.deleteMember(username);
            if (ret == 1) {
                map.put("status", 200);
            }
        }

        return map;
    }

    // 마이페이지
    @RequestMapping(value = "/mypage", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> mypage(@RequestHeader(name = "TOKEN") String token) {

        System.out.println(token);

        String username = jwtUtil.extractUsername(token);
        System.out.println(username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        // map.put("status", 0);
        return map;
    }

    // 로그인
    // 127.0.0.1:9090/ROOT/api/customer/login
    // 로그인은 securiy로 했었는데 필요한 상황 어떻게 처리할것이냐

    @RequestMapping(value = "/login", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> loginPost(@RequestBody MemberDTO member) {
        System.out.println("HERERER" + member);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        UserDetails user = userDetailsServiceImpl.loadUserByUsername(member.getUemail());

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

        // 암호화 되지 않은 것과 암호화 된 것 비교하기
        // 앞이 암호화되지 않은 것 뒤고 암호화된 것
        if (bcpe.matches(member.getUpw(), user.getPassword())) {
            String token = jwtUtil.generatorToken(member.getUemail());
            map.put("token", token);
            map.put("status", 200);
        }

        return map;
    }

    // @RequestMapping(value = "/login", method = { RequestMethod.POST }, consumes =
    // { MediaType.ALL_VALUE }, produces = {
    // MediaType.APPLICATION_JSON_VALUE })
    // public Map<String, Object> loginPost(@RequestParam(name = "uemail") String
    // uemail,
    // @RequestParam(name = "upw") String upw) {
    // // System.out.println("HERERER" + member);
    // Map<String, Object> map = new HashMap<>();
    // map.put("status", 0);

    // UserDetails user = userDetailsServiceImpl.loadUserByUsername(uemail);

    // BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // // 암호화 되지 않은 것과 암호화 된 것 비교하기
    // // 앞이 암호화되지 않은 것 뒤고 암호화된 것
    // if (bcpe.matches(upw, user.getPassword())) {
    // String token = jwtUtil.generatorToken(uemail);
    // map.put("token", token);
    // map.put("status", 200);
    // }

    // return map;
    // }

    // 127.0.0.1:9090/ROOT/api/customer/join
    @RequestMapping(value = "/join", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, // 이미지, json 등
                                                                                                          // 모든 걸 다받을
                                                                                                          // 수있음

            produces = { MediaType.APPLICATION_JSON_VALUE }) // 반환된느 타입은 항상 json
    public Map<String, Object> customerJoinPost(@RequestBody MemberDTO member) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        member.setUpw(bcpe.encode(member.getUpw()));
        member.setUrole("CUSTOMER");
        int ret = mMapper.memberJoin(member);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        System.out.println(member);
        if (ret == 1) {
            map.put("status", 200);
        }

        return map;
    }
}

// crsf 에러남. security때문에