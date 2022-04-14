package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BuyEntity;
import com.example.entity.BuyProjection;
import com.example.entity.ItemEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.BuyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/buy")
public class BuyRestController {
    @Autowired
    BuyRepository buyRepo;

    @Autowired
    JwtUtil jwtUtil;

    // {bcnt : 2 , item : {icode:2}}
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBoard(@RequestBody Map<String, Object> buy) {
        System.out.println(buy.toString());
        Map<String, Object> map = new HashMap<>();

        BuyEntity buyEntity = new BuyEntity();
        buyEntity.setBcnt(Long.parseLong(buy.get("icode").toString()));
        // buyEntity.setBcnt(Long.valueOf(buy.get("bcnt").toString()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setIcode(Long.parseLong(buy.get("icode").toString()));
        // itemEntity.setIcode(Long.valueOf(buy.get("icode").toString()));
        buyEntity.setItem(itemEntity);

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUemail((String) buy.get("uemail"));
        // memberEntity.setUemail(String.valueOf(buy.get("uemail")));
        buyEntity.setMember(memberEntity);
        System.out.println("여기요~~" + buyEntity);
        try {
            buyRepo.save(buyEntity);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 0);
            e.printStackTrace();
        }

        return map;
    }

    @RequestMapping(value = "/insert1", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBoard2(@RequestBody BuyEntity buy) {
        System.out.println(buy.toString());
        Map<String, Object> map = new HashMap<>();

        System.out.println("여기요~~" + buy);
        try {
            buyRepo.save(buy);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 0);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/insert2", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBoard3(
            @RequestHeader String token,
            @RequestBody BuyEntity buy) {
        System.out.println(buy.toString());
        Map<String, Object> map = new HashMap<>();

        // 토큰에서 이메일 추출
        String username = jwtUtil.extractUsername(token);

        // 회원엔티티 객체
        MemberEntity member = new MemberEntity();
        member.setUemail(username);

        // 주문엔티티에 추가
        buy.setMember(member);

        System.out.println("여기요~~" + buy);
        try {
            buyRepo.save(buy);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 0);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/selectList", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectList(
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        String username = jwtUtil.extractUsername(token);
        List<BuyProjection> list = buyRepo.findByMember_uemail(username);
        if (!list.isEmpty()) {
            map.put("status", 200);
            map.put("result", list);
        }

        return map;
    }

    @RequestMapping(value = "/selectOne", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOne(@RequestParam(name = "bno") long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        // BuyEntity buyEntity = buyRepo.findById(bno).orElse(null);
        BuyProjection buyEntity = buyRepo.findByBno(bno);

        if (buyEntity != null) {
            map.put("status", 200);
            map.put("result", buyEntity);
        }

        return map;
    }
}
