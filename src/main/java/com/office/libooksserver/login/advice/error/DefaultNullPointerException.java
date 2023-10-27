package com.office.libooksserver.login.advice.error;

import com.office.libooksserver.login.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class DefaultNullPointerException extends NullPointerException{
    
    private ErrorCode errorCode;

    public DefaultNullPointerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
