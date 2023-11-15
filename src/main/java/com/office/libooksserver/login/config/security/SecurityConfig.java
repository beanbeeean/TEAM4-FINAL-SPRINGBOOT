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
                .authorizeRequests()
                .antMatchers("/", "/error","/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js")
                .permitAll()
                .antMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")
                .permitAll()
                .antMatchers("/login/**","/auth/**", "/oauth2/**")
                .permitAll()

                //USER RESERVATION
                .antMatchers("/read/**")
                .permitAll()
                .antMatchers("/read/Reservation")
                .hasRole("USER")
                .antMatchers("/study/**")
                .permitAll()
                .antMatchers("/study/Reservation")
                .hasRole("USER")

                //USER MYPAGE
                .antMatchers("/user/myPage")
                .hasAnyRole("ADMIN", "USER", "SUPER")
                .antMatchers("/user/**")
                .hasRole("USER")

                //community, chat
                .antMatchers("/community/community_modify/*","/community/write_comment")
                .hasRole("USER")
                .antMatchers("/community","/community/*", "/community/get_comments")
                .permitAll()
                .antMatchers("/community/write")
                .hasRole("USER")
                .antMatchers("/chat/room_cno/*")
                .permitAll()
                .antMatchers("/chat/room_cno")
                .permitAll()
                .antMatchers("/chat/list")
                .permitAll()
                .antMatchers("/chat/**")
                .hasRole("USER")
                .antMatchers("/ws-stomp")
                .hasRole("USER")
                .antMatchers("/ws-stomp/**")
                .hasRole("USER")

                //book
                .antMatchers("/checkout_books/**")
                .permitAll()
                .antMatchers("/checkout_books/checkout")
                .hasRole("USER")
                .antMatchers("/checkout_books/checkout_book_list")
                .hasRole("USER")


                //admin
                .antMatchers("/admin/management/memberManagement")
                .permitAll()
                .antMatchers("/admin/management/change_user_state")
                .hasRole("SUPER")
                .antMatchers("/admin/management/**")
                .hasAnyRole("ADMIN", "SUPER")



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