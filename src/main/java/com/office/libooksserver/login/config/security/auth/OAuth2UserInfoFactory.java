package com.office.libooksserver.login.config.security.auth;

import com.office.libooksserver.login.advice.assertThat.DefaultAssert;
import com.office.libooksserver.login.config.security.auth.company.*;
import com.office.libooksserver.login.domain.entity.user.Provider;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {

        log.info("getOAuth2UserInfo[]");

        if(registrationId.equalsIgnoreCase(Provider.google.toString())) {log.info("getOAuth2UserInfo[]1");
            return new Google(attributes);
        } else if (registrationId.equalsIgnoreCase(Provider.facebook.toString())) {log.info("getOAuth2UserInfo[]2");
            return new Facebook(attributes);
        } else if (registrationId.equalsIgnoreCase(Provider.github.toString())) {log.info("getOAuth2UserInfo[]3");
            return new Github(attributes);
        } else if (registrationId.equalsIgnoreCase(Provider.naver.toString())) {log.info("getOAuth2UserInfo[]4");
            return new Naver(attributes);
        } else if (registrationId.equalsIgnoreCase(Provider.kakao.toString())) {log.info("getOAuth2UserInfo[]5");
            return new Kakao(attributes);
        } else {
            DefaultAssert.isAuthentication("해당 oauth2 기능은 지원하지 않습니다.");
        }
        return null;
    }
}
