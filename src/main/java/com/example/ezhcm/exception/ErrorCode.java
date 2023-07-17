package com.example.ezhcm.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(),"ECBR400","Lỗi yêu cầu "),
    METHOD_ARGUMENT_TYPE_ENUM_BINDING_MISMATCH(HttpStatus.BAD_REQUEST.value(), "ECEBM400", "Bad Request (Method Argument Type Mismatch Error) "),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "ECU401", "Lỗi xác thực : "),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "ECF403", "Yêu cầu quyền truy cập : "),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ECN404", "Không tìm thấy :"),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY.value(), "UNPROCESSABLE_ENTITY", "Không thể xử lý : "),
    CONFLICT(HttpStatus.CONFLICT.value(), "ECN409", "Lỗi xung đột : ") ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
