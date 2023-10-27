package com.office.libooksserver.login.advice.assertThat;

import com.office.libooksserver.login.advice.error.DefaultAuthenticationException;
import com.office.libooksserver.login.advice.error.DefaultException;
import com.office.libooksserver.login.advice.error.DefaultNullPointerException;
import com.office.libooksserver.login.advice.error.InvalidParameterException;
import com.office.libooksserver.login.advice.payload.ErrorCode;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

public class DefaultAssert extends Assert{

    //DefaultAssert라는 유틸리티 클래스입니다. 이 클래스는 특정 조건들이 충족되지 않을 때 에러를 발생시키는 메서드들로 구성되어 있습니다.

    //isTrue(boolean value): 값이 true가 아닐 경우 DefaultException을 발생시킵니다.
    public static void isTrue(boolean value){
        if(!value){
            throw new DefaultException(ErrorCode.INVALID_CHECK);
        }
    }

    //isTrue(boolean value, String message): 값이 true가 아닐 경우 주어진 메시지와 함께 DefaultException을 발생시킵니다.
    public static void isTrue(boolean value, String message){
        if(!value){
            throw new DefaultException(ErrorCode.INVALID_CHECK, message);
        }
    }

    //isValidParameter(Errors errors): 주어진 에러 객체에 에러가 있을 경우 InvalidParameterException을 발생시킵니다.
    public static void isValidParameter(Errors errors){
        if(errors.hasErrors()){
            throw new InvalidParameterException(errors);
        }
    }

    //isObjectNull(Object object): 객체가 null일 경우 DefaultNullPointerException을 발생시킵니다.
    public static void isObjectNull(Object object){
        if(object == null){
            throw new DefaultNullPointerException(ErrorCode.INVALID_CHECK);
        }
    }

    //isListNull(List<Object> values): 주어진 리스트가 비어 있을 경우 DefaultException을 발생시킵니다.
    public static void isListNull(List<Object> values){
        if(values.isEmpty()){
            throw new DefaultException(ErrorCode.INVALID_FILE_PATH);
        }
    }

    //isListNull(Object[] values): 주어진 배열이 null일 경우 DefaultException을 발생시킵니다.
    public static void isListNull(Object[] values){
        if(values == null){
            throw new DefaultException(ErrorCode.INVALID_FILE_PATH);
        }
    }

    //isOptionalPresent(Optional<?> value): 주어진 Optional 객체가 값을 포함하고 있지 않을 경우 DefaultException을 발생시킵니다.
    public static void isOptionalPresent(Optional<?> value){
        if(!value.isPresent()){
            throw new DefaultException(ErrorCode.INVALID_PARAMETER);
        }
    }

    //isAuthentication(String message): 주어진 메시지와 함께 DefaultAuthenticationException을 발생시킵니다.
    public static void isAuthentication(String message){
        throw new DefaultAuthenticationException(message);
    }

    //isAuthentication(boolean value): 값이 true가 아닐 경우 DefaultAuthenticationException을 발생시킵니다.
    public static void isAuthentication(boolean value){
        if(!value){
            throw new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION);
        }
    }
}
