package com.study.boardproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * https://docs.spring.io/spring-security/reference/servlet/architecture.html
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(Customizer.withDefaults()) // csrf 공격을 방어하기 위한 설정
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 모든 요청에 대해 인증 절차 없이 접근 허용
                .httpBasic(Customizer.withDefaults()) // 헤더에 사용자 이름과 비밀번호를 인코딩하여 전송
                .formLogin(Customizer.withDefaults()); // 폼 기반 로그인 설정

        return httpSecurity.build();
    }

}
