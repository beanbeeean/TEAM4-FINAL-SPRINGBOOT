package com.office.libooksserver.login.config.security.auth.company;

import com.office.libooksserver.login.config.security.auth.OAuth2UserInfo;
import com.office.libooksserver.login.domain.entity.user.Provider;

import java.util.Map;

public class Naver extends OAuth2UserInfo{

    public Naver(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("id");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("email");
    }

//    @Override
//    public String getImageUrl() {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        if (response == null) {
//            return null;
//        }
//
//        return (String) response.get("profile_image");
//    }

    @Override
    public String getProvider(){
        return Provider.naver.toString();
    }
}
