package com.backend.back.Auth;

import com.backend.back.api.dto.GoogleOAuthTokenDto;
import com.backend.back.api.dto.GoogleUserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Getter
public class GoogleOAuth {
    private final String googleLoginUrl="https://accounts.google.com";
    private final String GOOGLE_TOKEN_REQUEST_URL ="https://oauth2.googleapis.com/token";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/vI/userinfo";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${google.cliendId}")
    private String googleClientId;
    @Value("${google.redirect}")
    private String googleRedirectUrl;
    @Value("${google.secret}")
    private String googleClientSecret;

    public ResponseEntity<String> requestAccessToken(String code){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        params.put("client_id",googleClientId);
        params.put("client_secret",googleClientSecret);
        params.put("redirect_uri",googleRedirectUrl);
        params.put("grant_type","authorization_code");

        ResponseEntity<String> reponseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL,params,String.class);
        if (reponseEntity.getStatusCode()== HttpStatus.OK){
            return reponseEntity;
        }
        return null;
    }
    public GoogleOAuthTokenDto getAccessToken(ResponseEntity<String> response) throws JsonProcessingException{ // accessToken에 대한 정보를 자바 객체로 저장후 리턴
        System.out.println("respone.getBody()= "+response.getBody());
        GoogleOAuthTokenDto googleOAuthTokenDto = objectMapper.readValue(response.getBody(), GoogleOAuthTokenDto.class);
        return googleOAuthTokenDto;
    }

    public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenDto oAuthToken){// 토큰을 가지고 사용자 정보를 요청
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET,request,String.class);
        System.out.println("response.getBody() = "+response.getBody());
        return response;
    }
    public GoogleUserInfoDto getUserInfo(ResponseEntity<String> response) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleUserInfoDto googleUserInfoDto = objectMapper.readValue(response.getBody(),GoogleUserInfoDto.class);
        return googleUserInfoDto;
    }
}
