package com.mao.demo.common.response;

import lombok.Getter;

/**
 *  响应码枚举
 */
@Getter
public enum ResponseCode {

    SUCCESS(200, "OK"),

    CREATED(201, "Created"),

    FAILED(400, "Bad Request"),

    UNAUTHORIZED(401, "Unauthorized"),

    FORBIDDEN(403,"Forbidden"),

    NOT_FOUND(404, "Not Found"),

    VALIDATE_FAILED(405, "Validate Failed"),

    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
