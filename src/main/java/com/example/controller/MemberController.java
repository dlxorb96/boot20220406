package com.example.controller;

import com.example.dto.MemberDTO;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    MemberService mService;

    @GetMapping(value = "/join")
    public String getJoin() {
        return "join";
    }

    @PostMapping(value = "/joinaction")
    public String postjoinaction(
            @ModelAttribute MemberDTO member) {
        System.out.println(member);
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        member.setUpw(bcpe.encode(member.getUpw()));
        member.setUrole("SELLER");
        mService.memberInsertOne(member);
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String getLogin() {
        return "login";
    }

}
