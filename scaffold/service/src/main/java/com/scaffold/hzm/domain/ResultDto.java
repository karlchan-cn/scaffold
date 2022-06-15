package com.scaffold.hzm.domain;

import lombok.Data;

@Data
public class ResultDto<T> {
    public static final String CODE_SUCCESS = "SUCCESS";
    private String code;
    private String message;
    private T responseData;
    private String jwt;


}
