package com.backend.back.Auth;

import com.backend.back.domain.member.Role;
import com.backend.back.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security설정들을 활성화 시킴
public class SecurityConfig extends Exception {
    private final OAuthService oAuthService;
    @Bean
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable().headers().frameOptions().disable() // h2-console화면을 사용하기 위해 해당 옵션 disable
                .and().authorizeHttpRequests()
                .requestMatchers("/","/css/**","image/**","/js/**","/h2-console/**").permitAll()
                .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint();
//                .userService(oAuthService);
    }
}
