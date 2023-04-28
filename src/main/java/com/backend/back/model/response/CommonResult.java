package com.backend.back.model.response;

import lombok.Data;

@Data
public class CommonResult { //api실행결과를 담는 공통 모델
    private boolean success;
    private int code;
    private String msg;

}