package com.backend.back.Auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// cors 처리를 위함.
@Configuration
public class WebConfig implements WebMvcConfigurer { // cors설정
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(final CorsRegistry registry){//CORS 정책을 허용해줄 url을 지정함.
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))//허용하는 메서드
                .exposedHeaders(HttpHeaders.LOCATION); //서버에 반환해 줄 헤더를 지정
    }
}
