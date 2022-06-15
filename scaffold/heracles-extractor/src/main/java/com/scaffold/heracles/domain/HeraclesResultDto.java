package com.scaffold.heracles.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Karl on 2022/3/4
 **/
@ToString
@Data
public class HeraclesResultDto<T> {
    private Integer status;
    private String msg;
    private boolean success;
    private T data;
}
