package com.office.libooksserver.login.config.security;

import com.office.libooksserver.login.config.security.handler.CustomSimpleUrlAuthenticationFailureHandler;
import com.office.libooksserver.login.config.security.handler.CustomSimpleUrlAuthenticationSuccessHandler;
import com.office.libooksserver.login.config.security.token.CustomAuthenticationEntryPoint;
import com.office.libooksserver.login.config.security.token.CustomOncePerRequestFilter;
import com.office.libooksserver.login.repository.auth.CustomAuthorizationRequestRepository;
import com.office.libooksserver.login.service.auth.CustomDefaultOAuth2UserService;
import com.office.libooksserver.login.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomDefaultOAuth2UserService customOAuth2UserService;
    private final CustomSimpleUrlAuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomSimpleUrlAuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

    @Bean
    public CustomOncePerRequestFilter customOncePerRequestFilter() {
        return new CustomOncePerRequestFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() //Cross-Origin Resource Sharing을 활성화합니다.
                    .and()
                .sessionManagement() // 세션 관리 정책을 설정하며, 이 경우 Stateless로 설정되었기 때문에 서버가 세션 상태를 유지하지 않습니다.
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf() //CSRF 보호를 비활성화하고, 기본 Form Login과 HTTP Basic 인증도 비활성화하였습니다.
                    .disable()
                .formLogin()//
                    .disable()
                .httpBasic()
                    .disable()
                .exceptionHandling()  // 인증 예외가 발생할 경우 CustomAuthenticationEntryPoint를 사용하여 예외를 처리합니다.
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .and()
                .authorizeRequests() // 다양한 경로의 권한을 설정합니다. 예를 들어, 이미지, CSS, JS 파일 경로 및 Swagger 문서, 로그인, 인증 경로는 모두 접근이 허용됩니다. /blog/** 경로도 모두 허용됩니다. 그 외의 요청은 모두 인증이 필요합니다.
                    .antMatchers("/", "/error","/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js")
                        .permitAll()
                    .antMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")
                        .permitAll()
                    .antMatchers("/login/**","/auth/**", "/oauth2/**")
                        .permitAll()
                    .antMatchers("/blog/**")
                        .permitAll()
                    .antMatchers("/read/**")
                    .permitAll()
                    .antMatchers("/test/**")
                    .permitAll()
                    .antMatchers("/test")
                    .permitAll()
                    .antMatchers("/checkout_books/**")
                    .permitAll()
                    .antMatchers("/community/**")
                    .permitAll()
                    //.antMatchers("/auth/**").hasRole("USER")
                    .anyRequest()
                        .authenticated()
                    .and()
                .oauth2Login() // OAuth2 Login: OAuth2 인증 로그인 설정을 담당합니다.
                    .authorizationEndpoint() // Authorization Endpoint: OAuth2 인증을 위한 엔드포인트를 설정합니다.
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(customAuthorizationRequestRepository)
                        .and()
                    .redirectionEndpoint()  // Redirection Endpoint: OAuth2 인증 후 리다이렉션될 엔드포인트를 설정합니다.
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()  // User Info Endpoint: 사용자 정보를 가져올 때 사용하는 서비스를 지정합니다.
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)  // Success and Failure Handlers: OAuth2 인증 성공 및 실패 시 처리할 핸들러를 지정합니다.
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        //  UsernamePasswordAuthenticationFilter 클래스 전에 customOncePerRequestFilter() 필터를 추가합니다.
        http.addFilterBefore(customOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
