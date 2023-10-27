package com.office.libooksserver.login.config.security.token;

import com.office.libooksserver.login.service.auth.CustomTokenProviderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CustomOncePerRequestFilter extends OncePerRequestFilter{

    @Autowired
    private CustomTokenProviderService customTokenProviderService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("doFilterInternal[]");

        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && customTokenProviderService.validateToken(jwt)) {

            UsernamePasswordAuthenticationToken authentication = customTokenProviderService.getAuthenticationById(jwt);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {

        log.info("getJwtFromRequest[]");

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            log.info("bearerToken = {}", bearerToken.substring(7, bearerToken.length()));
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    
}
