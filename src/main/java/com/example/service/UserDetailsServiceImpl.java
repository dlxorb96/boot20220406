package com.example.service;

import java.util.Collection;

import com.example.dto.MemberDTO;
import com.example.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 로그인에서 버튼을 누르면 서비스로 이메일 전달됨.
@Service
public class UserDetailsServiceImpl implements
        UserDetailsService {

    @Autowired
    MemberMapper mMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("UserDetailsService : " + username);
        MemberDTO member = mMapper.memberEmail(username);
        String[] strRole = { member.getUrole() };

        // String 배열 권한을 collection<Granted ...> 타입으로 변환함
        Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);

        User user = new User(member.getUemail(), member.getUpw(), role);
        return user;
    }

}