package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.dto.MemberDTO;
import com.example.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// backend만 구현함, 화면구현 x , vue, react와 연동

@RestController
@RequestMapping("/api/rest_customer")
public class CustomerRestController {

    @Autowired
    MemberMapper mMapper;

    // 127.0.0.1:9090/ROOT/api/rest_customer/join
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