package com.office.libooksserver.login.service.auth;

import com.office.libooksserver.login.advice.assertThat.DefaultAssert;
import com.office.libooksserver.login.config.security.auth.OAuth2UserInfo;
import com.office.libooksserver.login.config.security.auth.OAuth2UserInfoFactory;
import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.login.domain.entity.user.Role;
import com.office.libooksserver.user.dto.UserDto;
import com.office.libooksserver.user.service.implement.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomDefaultOAuth2UserService extends DefaultOAuth2UserService{

    @Autowired
    UserMapper userMapper;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        log.info("loadUser[]" );

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (Exception e) {
            DefaultAssert.isAuthentication(e.getMessage());
        }
        return null;
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        log.info("processOAuth2User[]" );

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        DefaultAssert.isAuthentication(!oAuth2UserInfo.getEmail().isEmpty());

        UserDto userOptional = userMapper.findByEmail(oAuth2UserInfo.getEmail());
        UserDto user;

        log.info("userOptional[] : " + userOptional);
        if(userOptional != null) {
            user = userOptional;
            DefaultAssert.isAuthentication(user.getU_provider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private UserDto registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        log.info("registerNewUser[] : xx" );

        UserDto user = UserDto.builder()
                    .u_name(oAuth2UserInfo.getName())
                    .u_email(oAuth2UserInfo.getEmail())
                    .u_provider(oAuth2UserRequest.getClientRegistration().getRegistrationId())
//                    .imageUrl(oAuth2UserInfo.getImageUrl())

                    .u_role(Role.USER.getValue())
                    .u_state("1")
                    .build();

        userMapper.save(user);

        return user;
    }

    private UserDto updateExistingUser(UserDto user, OAuth2UserInfo oAuth2UserInfo) {

        log.info("updateExistingUser[]" );

        user.updateName(oAuth2UserInfo.getName());
//        user.updateImageUrl(oAuth2UserInfo.getImageUrl());

        userMapper.update(user);
        return user;
    }
}
