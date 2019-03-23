package com.cherlshall.wechat.util.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseVO<T> {

    private Boolean success;
    private T data;

    public static <T> ResponseVO<T> newInstance() {
        return new ResponseVO<>();
    }

    public static <T> ResponseVO<T> ofSuccess(T data) {
        return new ResponseVO<T>().setSuccess(true).setData(data);
    }

    public static <T> ResponseVO<T> ofFailure(T data) {
        return new ResponseVO<T>().setSuccess(false).setData(data);
    }
}
