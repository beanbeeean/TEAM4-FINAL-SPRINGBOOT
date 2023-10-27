package com.office.libooksserver.login.advice.error;

import com.office.libooksserver.login.advice.payload.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;


@Getter
public class DefaultAuthenticationException extends AuthenticationException{

    // 이 코드는 DefaultAuthenticationException이라는 사용자 정의 예외 클래스입니다.
    // 이 클래스는 Spring Security의 AuthenticationException을 확장하여 사용합니다.
    // 주요 특징 및 기능은 다음과 같습니다:

    //errorCode: 이 예외에 연관된 ErrorCode를 보관하는 멤버 변수입니다.
    private ErrorCode errorCode;


    //    생성자들:
    //    DefaultAuthenticationException(String msg, Throwable t): 메시지와 예외 원인(Throwable)을 받아 예외를 생성합니다. 동시에 errorCode를 INVALID_REPRESENTATION으로 초기화합니다.
    public DefaultAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        this.errorCode = ErrorCode.INVALID_REPRESENTATION;
    }

    //    DefaultAuthenticationException(String msg): 오직 메시지만을 받아 예외를 생성합니다.
    public DefaultAuthenticationException(String msg) {
        super(msg);
    }

    //    DefaultAuthenticationException(ErrorCode errorCode): ErrorCode 객체를 받아 예외를 생성하며,
    //    해당 ErrorCode의 메시지를 상위 AuthenticationException의 메시지로 설정하고, errorCode를 초기화합니다.
    public DefaultAuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }



}
