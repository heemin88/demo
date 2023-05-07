package com.backend.back.service;

import com.backend.back.Auth.GoogleOAuth;
import com.backend.back.api.dto.GoogleOAuthTokenDto;
import com.backend.back.api.dto.GoogleUserInfoDto;
import com.backend.back.domain.member.Member;
import com.backend.back.model.response.SingleResult;
import com.backend.back.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final ResponseService responseService;

    private GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException{
        ResponseEntity<String> accessTokenResponse = googleOAuth.requestAccessToken(code);
        GoogleOAuthTokenDto oAuthToken = googleOAuth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleOAuth.getUserInfo(userInfoResponse);
        return googleUser;
    }
    private Boolean validateDuplicateMember(String mail) {
        // Exception 발생
        Optional<Member> findMembers = memberRepository.findByMail(mail);
        if (!findMembers.isEmpty()) { //만약 존재하는 회원이면
            return false;
        }
        return true; //처음 가입하는 회원이면
    }
    public SingleResult<Member> googlelogin(String code) throws IOException{
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(code);
        System.out.println("googleUser.getEmail() = " + googleUser.getEmail());
        if (validateDuplicateMember(googleUser.getEmail())){//중복 확인 , 신규회원이면
            memberRepository.save(
                    Member.builder()
                            .mail(googleUser.getEmail())
                            .problem_count(0)
                            .problem_current(0)
                            .build()
            );
            return responseService.getSingleResult(memberRepository.findByMail(
                    googleUser.getEmail()
            ).orElse(null)); /*오류 처리로 고칠 필요 있음.*/
        }
        else{ // 이미 가입된 회원이면
            return responseService.getSingleResult(memberRepository.findByMail(googleUser.getEmail()).orElseThrow(null));
        }

    }
}
