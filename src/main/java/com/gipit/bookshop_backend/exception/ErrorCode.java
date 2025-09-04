package com.gipit.bookshop_backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    USER_EXISTS(1001,"User existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(1002,"Email existed", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized exception",HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1111,"Invalid key", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003,"Username must be at least 6 characters",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004,"Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(1005,"User not existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"Unauthorized",HttpStatus.FORBIDDEN),
    BOOK_NOT_FOUND(1008,"Book was not found",HttpStatus.BAD_REQUEST),
    AUTHOR_NOT_FOUND(1009,"Author was not found",HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1010,"Role was not found",HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(1011,"Invalid token",HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(1012,"Expired token",HttpStatus.UNAUTHORIZED),
    TOKEN_NOT_EXPIRED_YET(1013,"Token expired yet",HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(1014,"User was not found",HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1015,"Email is invalid",HttpStatus.BAD_REQUEST),
    PHONENUMBER_INVALID(1016,"Phone number is invalid",HttpStatus.BAD_REQUEST),
    NAME_INVALID(1017,"Name is invalid",HttpStatus.BAD_REQUEST),
    USERNAME_OR_PASSWORD_INVALID(1018,"Username or password is invalid",HttpStatus.BAD_REQUEST),
    ;


    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code=code;
        this.message=message;
        this.httpStatusCode=httpStatusCode;
    }
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
