package com.mao.demo.common.exception;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException {
    private final int code;
    private final String msg;

    public APIException() {
        this(400, "Bad Request");
    }

    public APIException(String msg) {
        this(400, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
