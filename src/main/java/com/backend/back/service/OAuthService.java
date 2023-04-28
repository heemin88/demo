package com.backend.back.service;

import com.backend.back.Auth.GoogleOAuth;
import com.backend.back.api.dto.GoogleOAuthTokenDto;
import com.backend.back.api.dto.GoogleUserInfoDto;
import com.backend.back.domain.user.User;
import com.backend.back.model.response.SingleResult;
import com.backend.back.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class OAuthService {
    private final GoogleOAuth googleOAuth;
    private final UserRepository userRepository;
    private final ResponseService responseService;

    private GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException{
        ResponseEntity<String> accessTokenResponse = googleOAuth.requestAccessToken(code);
        GoogleOAuthTokenDto oAuthToken = googleOAuth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleOAuth.getUserInfo(userInfoResponse);
        return googleUser;
    }
    private void validateDuplicateMember(String mail) {
        // Exception 발생
        Optional<User> findMembers = userRepository.findByMail(mail);
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    public SingleResult<User> googlelogin(String code) throws IOException{
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(code);
        validateDuplicateMember(googleUser.getEmail()); //중복 확인
        userRepository.save(
                User.builder()
                        .mail(googleUser.getEmail())
                        .password("google")
                        .problem_count(0)
                        .problem_current(0)
                        .build()
        );
        return responseService.getSingleResult(userRepository.findByMail(
                googleUser.getEmail()
        ).orElse(null)); /*오류 처리로 고칠 필요 있음.*/
    }
}
