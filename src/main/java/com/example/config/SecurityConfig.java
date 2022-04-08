package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig
                extends WebSecurityConfigurerAdapter {

        // 1. 직접 만든 detailService 객체 가져오기
        @Autowired
        UserDetailsService uService;

        // 회원가입시 암호화 했던 방법의 객체생성
        // 환경설정에서는 객체를 만들기 위해서는 bean을 써야 한다.
        // 2. 암호화 방법 객체 생성 @Bean은 서버 구동시 자동으로 객체 생성됨
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // 3. 직접만든 dtailsService에 암호화 방법 적용
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(uService)
                                .passwordEncoder(bCryptPasswordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                // super.configure(http);

                // 페이지별 접근 권한 설정
                http.authorizeRequests()
                                .antMatchers("/admin", "/admin/**")
                                .hasAuthority("ADMIN")
                                .antMatchers("/seller", "/seller/**")
                                .hasAnyAuthority("ADMIN", "SELLER")
                                .antMatchers("/customer", "/customer/**")
                                .hasAuthority("CUSTOMER")
                                .anyRequest().permitAll();

                // 로그인 페이지 설정, 단 POST는 직접 만들지 않음
                http.formLogin()
                                .loginPage("/member/login")
                                .loginProcessingUrl("/member/loginaction")
                                .usernameParameter("uemail")
                                .passwordParameter("upw")
                                .defaultSuccessUrl("/home")
                                .permitAll();

                http.logout()
                                .logoutUrl("/member/logout")
                                // .logoutSuccessHandler(new MylogoutSuccessHandler())
                                .logoutSuccessUrl("/home")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .permitAll();

                // 접근권한불가 403
                http.exceptionHandling().accessDeniedPage("/403page");

                // h2-console을 사용하기 위해서
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.csrf().ignoringAntMatchers("/api/**");
                http.headers().frameOptions().sameOrigin();

                // rest controller 사용
                http.csrf().ignoringAntMatchers("/api/**");
        }

}
