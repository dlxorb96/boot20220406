package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

@WebFilter(urlPatterns = { "/api/customer/mypage" })
// @Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 프론트에서 token이 있어야 되는 것 체크
            String token = request.getHeader("TOKEN");
            System.out.println(token);

            if (token != null) {
                if (token.length() > 0) {
                    // 여기서 토큰 검사 함. 실패시 자동으로 오류를 던져준다.
                    String username = jwtUtil.extractUsername(token);
                    System.out.println("username: " + username);
                    System.out.println("token: " + token);
                    System.out.println("token null 아님 통과");
                    // 컨트롤러 진입
                    filterChain.doFilter(request, response);
                }
            }
            // 토큰체크가 필요없는 페이지
            else {
                // 강제로 오류를 발생시킴,
                // 아래 소스 실행 안됨.여기서 끝!!!
                System.out.println("토큰이없는경우");
                throw new Exception("토큰없음");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(-1, "토큰오류");
        }
    }

}