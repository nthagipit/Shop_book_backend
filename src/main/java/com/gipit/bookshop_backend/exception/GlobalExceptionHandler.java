package com.gipit.bookshop_backend.exception;

import com.gipit.bookshop_backend.dto.response.ApiResponse;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value= Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception){
        log.error(exception.getMessage());
        ErrorCode errorCode= ErrorCode.UNCATEGORIZED_EXCEPTION;
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }
    @ExceptionHandler(value= AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException){
        ErrorCode errorCode= appException.getErrorCode();
        ApiResponse apiRes=ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiRes);
    }
    @ExceptionHandler(value= AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse> handlingAuthorizationDeniedException(AuthorizationDeniedException authorizationDeniedException){
        ErrorCode errorCode= ErrorCode.UNAUTHORIZED;
        ApiResponse apiResponse=ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey= exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode= ErrorCode.INVALID_KEY;
        try{
            errorCode= ErrorCode.valueOf(enumKey);

        }catch (IllegalArgumentException e){
            throw  e;
        }
        ApiResponse aiApiResponse= ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(aiApiResponse);
    }
    @ExceptionHandler(value= BadCredentialsException.class)
    ResponseEntity<ApiResponse> handlingBadCredentialsException(BadCredentialsException badCredentialsException){
            ErrorCode error= ErrorCode.USERNAME_OR_PASSWORD_INVALID;
            ApiResponse apiResponse= ApiResponse.builder()
                    .code(error.getCode())
                    .message(error.getMessage())
                    .build();
            return ResponseEntity.status(error.getHttpStatusCode()).body(apiResponse);
    }

}
