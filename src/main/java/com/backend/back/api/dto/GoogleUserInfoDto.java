package com.backend.back.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleUserInfoDto {
    private String id;
    private String email;
    private String picture;//프로필 사진
    private Boolean verified_email;
}
