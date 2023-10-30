package com.office.libooksserver.login.advice.error;


import com.office.libooksserver.login.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class DefaultException extends RuntimeException{
    
    private ErrorCode errorCode;

    public DefaultException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public DefaultException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
