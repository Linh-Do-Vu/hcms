package com.example.ezhcm.exception;

import lombok.Builder;
import lombok.Getter;

public class CustomException extends RuntimeException{
@Getter
private final String httpStatus;

@Builder
    public CustomException (ErrorCode errorCode,String detailMessage) {
    super(errorCode.getMessage().concat(detailMessage));
    this.httpStatus = errorCode.getCode();
}

}
