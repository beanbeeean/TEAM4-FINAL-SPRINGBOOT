package com.office.libooksserver.login.config.security.handler;

import com.office.libooksserver.login.advice.assertThat.DefaultAssert;
import com.office.libooksserver.login.config.security.OAuth2Config;
import com.office.libooksserver.login.config.security.util.CustomCookie;
import com.office.libooksserver.login.domain.mapping.TokenMapping;
import com.office.libooksserver.login.redis.service.RedisService;
import com.office.libooksserver.login.repository.auth.CustomAuthorizationRequestRepository;
import com.office.libooksserver.login.service.auth.CustomTokenProviderService;
import com.office.libooksserver.login.service.token.TokenMapper;
import com.office.libooksserver.user.dto.UserDto;
import com.office.libooksserver.user.service.implement.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.office.libooksserver.login.repository.auth.CustomAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Log4j2
@RequiredArgsConstructor
@Component
public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Autowired
    TokenMapper tokenMapper;
    @Autowired
    UserMapper userMapper;

    private final RedisService redisService;
    private final CustomTokenProviderService customTokenProviderService;
    private final OAuth2Config oAuth2Config;
    private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("onAuthenticationSuccess[]");

        DefaultAssert.isAuthentication(!response.isCommitted());

        String targetUrl = determineTargetUrl(request, response, authentication);

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        log.info("determineTargetUrl[]");

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();

        System.out.println("ip :" +  ip);

        Optional<String> redirectUri = CustomCookie.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

        DefaultAssert.isAuthentication( !(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) );

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);

        UserDto userDto = userMapper.findByEmail(tokenMapping.getUserEmail());

        if(userDto.getU_state().equals("0")) {
            System.out.println("targetUrl : " + targetUrl);
            return UriComponentsBuilder.fromUriString("http://localhost:3000/")
                    .queryParam("error", "error")
                    .build().toUriString();
        }

        Map<String, String> values = new HashMap<>();
        values.put("ip", ip);
        values.put("email", tokenMapping.getUserEmail());


        redisService.setValuesWithTimeout(tokenMapping.getRefreshToken(),values,1209600);

        Cookie refreshToken = new Cookie("refreshToken", tokenMapping.getRefreshToken());
        refreshToken.setPath("/");
        refreshToken.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(refreshToken);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", tokenMapping.getAccessToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {

        log.info("clearAuthenticationAttributes[]");

        super.clearAuthenticationAttributes(request);
        customAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {

        log.info("isAuthorizedRedirectUri[]");

        URI clientRedirectUri = URI.create(uri);

        return oAuth2Config.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
