package com.mao.demo.common.response;

import com.mao.demo.common.annotation.ExceptionCode;
import lombok.Getter;

@Getter
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private final int code;

    /**
     * 响应信息
     */
    private final String msg;

    /**
     * 响应的具体数据
     */
    private final T data;

    public ResponseResult(T data) {
        this(ResponseCode.SUCCESS, data);
    }

    public ResponseResult(ResponseCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public ResponseResult(ExceptionCode annotation, T data) {
        this.code = annotation.value();
        this.msg = annotation.message();
        this.data = data;
    }

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
