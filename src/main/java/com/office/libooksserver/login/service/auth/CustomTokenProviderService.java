package com.office.libooksserver.login.service.auth;

import com.office.libooksserver.login.config.security.OAuth2Config;
import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.login.domain.mapping.TokenMapping;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class CustomTokenProviderService {

    @Autowired
    private OAuth2Config oAuth2Config;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public TokenMapping refreshToken(Authentication authentication, String refreshToken) {

        log.info("refreshToken[]" );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();

        Date accessTokenExpiresIn = new Date(now.getTime() + oAuth2Config.getAuth().getAccessTokenExpirationMsec());

        String secretKey = oAuth2Config.getAuth().getTokenSecret();
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String accessToken = Jwts.builder()
                                .setSubject(Long.toString(userPrincipal.getU_no()))
                                .setIssuedAt(new Date())
                                .setExpiration(accessTokenExpiresIn)
                                .signWith(key, SignatureAlgorithm.HS512)
                                .compact();

        return TokenMapping.builder()
                        .userEmail(userPrincipal.getU_email())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
    }

    public TokenMapping createToken(Authentication authentication) {

        log.info("createToken[] : ");

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();

        Date accessTokenExpiresIn = new Date(now.getTime() + oAuth2Config.getAuth().getAccessTokenExpirationMsec());
        Date refreshTokenExpiresIn = new Date(now.getTime() + oAuth2Config.getAuth().getRefreshTokenExpirationMsec());

        String secretKey = oAuth2Config.getAuth().getTokenSecret();

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String accessToken = Jwts.builder()
                                .setSubject(userPrincipal.getU_email())
                                .setIssuedAt(new Date())
                                .setExpiration(accessTokenExpiresIn)
                                .signWith(key, SignatureAlgorithm.HS512)
                                .compact();

        String refreshToken = Jwts.builder()
                                .setExpiration(refreshTokenExpiresIn)
                                .signWith(key, SignatureAlgorithm.HS512)
                                .compact();

        System.out.println("refreshToken : " + refreshToken);

        return TokenMapping.builder()
                    .userEmail(userPrincipal.getU_email())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
    }

    public String getUserIdFromToken(String token) {

        log.info("getUserIdFromToken[]" );

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(oAuth2Config.getAuth().getTokenSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public UsernamePasswordAuthenticationToken getAuthenticationById(String token){

        log.info("getAuthenticationById[]" );

        String userEmail = getUserIdFromToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserById(userEmail);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        return authentication;
    }

    public UsernamePasswordAuthenticationToken getAuthenticationByEmail(String email){

        log.info("getAuthenticationByEmail[]" );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        return authentication;
    }

    public Long getExpiration(String token) {

        log.info("getExpiration[]" );

        // accessToken 남은 유효시간
        Date expiration = Jwts.parserBuilder().setSigningKey(oAuth2Config.getAuth().getTokenSecret()).build().parseClaimsJws(token).getBody().getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        //시간 계산
        return (expiration.getTime() - now);
    }

    public boolean validateToken(String token) {

        log.info("validateToken[]" );

        try {

            Jwts.parserBuilder().setSigningKey(oAuth2Config.getAuth().getTokenSecret()).build().parseClaimsJws(token);

           return true;
        } catch (io.jsonwebtoken.security.SecurityException ex) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (MalformedJwtException ex) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException ex) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException ex) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException ex) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
