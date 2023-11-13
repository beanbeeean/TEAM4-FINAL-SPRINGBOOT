package com.office.libooksserver.login.service.auth;

import com.office.libooksserver.login.advice.assertThat.DefaultAssert;
import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.login.domain.entity.user.Role;
import com.office.libooksserver.login.domain.entity.user.Token;
import com.office.libooksserver.login.domain.mapping.TokenMapping;
import com.office.libooksserver.login.payload.request.auth.ChangePasswordRequest;
import com.office.libooksserver.login.payload.request.auth.RefreshTokenRequest;
import com.office.libooksserver.login.payload.request.auth.SignInRequest;
import com.office.libooksserver.login.payload.request.auth.SignUpRequest;
import com.office.libooksserver.login.payload.response.ApiResponse;
import com.office.libooksserver.login.payload.response.AuthResponse;
import com.office.libooksserver.login.payload.response.Message;
import com.office.libooksserver.login.redis.service.RedisService;
import com.office.libooksserver.login.service.token.TokenMapper;
import com.office.libooksserver.login.service.user.UserDto;
import com.office.libooksserver.login.service.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenMapper tokenMapper;

    private final RedisService redisService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomTokenProviderService customTokenProviderService;

    public ResponseEntity<?> whoAmI(UserPrincipal userPrincipal){

        log.info("whoAmI[]" );

        UserDto user = userMapper.findByEmail(userPrincipal.getU_email());

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email : " + userPrincipal.getU_email());
        }

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(user).build();

        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> delete(UserPrincipal userPrincipal){

        log.info("delete[]" );

        UserDto user = userMapper.findByid(userPrincipal.getU_no());
        if (user == null) {
            throw new UsernameNotFoundException("유저가 올바르지 않습니다. : " + userPrincipal.getU_no());
        }

        Token token = tokenMapper.findByUserEmail(userPrincipal.getU_email());

        if (user == null) {
            throw new UsernameNotFoundException("토큰이 유효하지 않습니다. : " + userPrincipal.getU_email());
        }

        userMapper.delete(user.getU_no());
        //tokenMapper.delete(token);


        ApiResponse apiResponse = ApiResponse.builder().check(true).information(Message.builder().message("회원 탈퇴하셨습니다.").build()).build();

        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> modify(UserPrincipal userPrincipal, ChangePasswordRequest passwordChangeRequest){

        log.info("modify[]" );

        UserDto user = userMapper.findByid(userPrincipal.getU_no());

        boolean passwordCheck = passwordEncoder.matches(passwordChangeRequest.getOldPassword(),user.getU_password());
        DefaultAssert.isTrue(passwordCheck, "잘못된 비밀번호 입니다.");

        boolean newPasswordCheck = passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getReNewPassword());
        DefaultAssert.isTrue(newPasswordCheck, "신규 등록 비밀번호 값이 일치하지 않습니다.");


        passwordEncoder.encode(passwordChangeRequest.getNewPassword());

        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> signin(SignInRequest signInRequest){

        log.info("signin[]" );

        // 인증 authenticationManager.authenticate을 통하여 UsernamePasswordAuthenticationToken을 사용하여 인증 시도
        // 성공하면 authentication 반환 실패하면 Spring Security의 필터 체인에 에 의해 사용자가 적절한 오류 반환

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword()
            )
        );
        UserDto userDto = userMapper.findByEmail(authentication.getName());

        if(userDto.getU_state().equals("0")) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }

        System.out.println("authentication : " + authentication.toString());

        // 사용자가 성공적으로 인증된 후에 그 사용자의 인증 정보를 현재 보안 컨텍스트에 저장하여, 이후에 다른 부분에서 현재 인증된 사용자의 정보를 쉽게 사용할 수 있게 합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);
        Token token = Token.builder()
                            .refreshToken(tokenMapping.getRefreshToken())
                            .userEmail(tokenMapping.getUserEmail())
                            .build();

        redisService.setValuesWithTimeout(tokenMapping.getRefreshToken(),tokenMapping.getUserEmail(),1209600);

        AuthResponse authResponse = AuthResponse.builder().accessToken(tokenMapping.getAccessToken()).build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .path("/")               // Path 설정
                .domain("localhost")     // Domain 설정
                .maxAge(7 * 24 * 60 * 60) // Max Age 설정 (7일)
                .httpOnly(true)          // HttpOnly 설정
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString()).body(authResponse);
    }

    public ResponseEntity<?> signup(SignUpRequest signUpRequest){

        log.info("signup[]");

        boolean chk = userMapper.existsByEmail(signUpRequest.getEmail());
        if(chk)
            chk=false;
        else
            chk=true;

        DefaultAssert.isTrue(chk, "해당 이메일이 존재합니다.");

        UserDto user = UserDto.builder()
                        .u_email(signUpRequest.getEmail())
                        .u_password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .u_provider("local")
                        .u_role(Role.ADMIN.getValue())
                        .u_state("0")
                        .build();

        userMapper.save(user);

//       ServletUriComponentsBuilder는 Spring에서 제공하는 유틸리티로, URI를 쉽게 구축할 수 있게 도와줍니다.
//      .fromCurrentContextPath(): 현재 요청의 context path를 가져옵니다. 예를 들면, 애플리케이션이 http://localhost:8080/myapp에서 실행되면 myapp이 context path가 됩니다.
//      .path("/auth/"): 주어진 경로 세그먼트를 URI에 추가합니다.
//      .buildAndExpand(user.getId()): URI 템플릿에 변수를 삽입합니다. 여기서는 user.getId()의 값으로 변수를 삽입하려 했지만, 실제 경로 템플릿에는 변수를 위한 placeholder가 보이지 않습니다. 따라서 이 부분은 실제로 경로에 아무런 영향을 주지 않을 것입니다.
//      .toUri(): 최종적으로 구성된 URI를 반환합니다.

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/auth/")
                .buildAndExpand(user.getU_no()).toUri();

        //ApiResponse 객체를 구축합니다. 이 객체는 응답 메시지와 성공 여부를 포함합니다.
        //.check(true): API의 성공 여부를 나타냅니다. 여기서는 true로 설정하여 성공적임을 표시합니다.
        //.information(...): 응답에 포함될 추가 정보를 설정합니다. 여기서는 Message 객체를 사용하여 "회원가입에 성공하였습니다."라는 메시지를 포함시킵니다.

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(Message.builder().message("회원가입에 성공하였습니다.").build()).build();

        //ResponseEntity는 Spring에서 HTTP 응답을 나타내는 객체입니다.
        //.created(location): HTTP 응답의 상태 코드를 201 CREATED로 설정하며, Location 헤더에 location 변수의 URI를 설정합니다.
        //.body(apiResponse): 응답 본문으로 apiResponse 객체를 설정합니다.

        return ResponseEntity.created(location).body(apiResponse);
    }

    public ResponseEntity<?> refresh(RefreshTokenRequest tokenRefreshRequest){

        log.info("refresh[]" );

        System.out.println("tokenRefreshRequest : "+tokenRefreshRequest);

        //1차 검증
        boolean checkValid = valid(tokenRefreshRequest.getRefreshToken());
        DefaultAssert.isAuthentication(checkValid);

        String email = redisService.getValues(tokenRefreshRequest.getRefreshToken());
        System.out.println("email : "+email);

        Authentication authentication = customTokenProviderService.getAuthenticationByEmail(email);


        System.out.println("authentication : "+authentication);


        //4. refresh token 정보 값을 업데이트 한다.
        //시간 유효성 확인
        TokenMapping tokenMapping;

        tokenMapping = customTokenProviderService.createToken(authentication);



        System.out.println("setValuesWithTimeout : " + tokenMapping.getRefreshToken());
        System.out.println("deleteValues : " + tokenRefreshRequest);

        redisService.setValuesWithTimeout(tokenMapping.getRefreshToken(),email,1209600);
        redisService.deleteValues(tokenRefreshRequest.getRefreshToken());

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokenMapping.getRefreshToken())
                .path("/")               // Path 설정
                .domain("localhost")     // Domain 설정
                .maxAge(7 * 24 * 60 * 60) // Max Age 설정 (7일)
                .httpOnly(true)          // HttpOnly 설정
                .build();

        //tokenMapper.update(updateToken);

        //AuthResponse authResponse = AuthResponse.builder().accessToken(tokenMapping.getAccessToken()).refreshToken(updateToken.getRefreshToken()).build();

        AuthResponse authResponse = AuthResponse.builder().accessToken(tokenMapping.getAccessToken()).build();  // refreshToken을 응답 본문에서 제거

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString()).body(authResponse);

    }

    public ResponseEntity<?> signout(RefreshTokenRequest tokenRefreshRequest){

        log.info("signout[]" );

        boolean checkValid = valid(tokenRefreshRequest.getRefreshToken());
        DefaultAssert.isAuthentication(checkValid);

        //4 token 정보를 삭제한다.
        redisService.deleteValues(tokenRefreshRequest.getRefreshToken());

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .path("/")               // Path 설정
                .domain("localhost")     // Domain 설정
                .maxAge(0) // Max Age 설정 (7일)
                .httpOnly(true)          // HttpOnly 설정
                .build();

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(Message.builder().message("로그아웃 하였습니다.").build()).build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString()).body(apiResponse);
    }

    private boolean valid(String refreshToken){

        log.info("valid[]" );

        //1. 토큰 형식 물리적 검증
        boolean validateCheck = customTokenProviderService.validateToken(refreshToken);
        DefaultAssert.isTrue(validateCheck, "Token 검증에 실패하였습니다.");

        //2. refresh token 값을 불러온다.
        //Token token = tokenMapper.findByRefreshToken(refreshToken);
        String token = redisService.getValues(refreshToken);
        log.info("token[]",token );
        boolean chk=false;
        if (token!=null) {
            chk=true;
        }
        DefaultAssert.isTrue(chk, "탈퇴 처리된 회원입니다.");


        //3. email 값을 통해 인증값을 불러온다
//        Authentication authentication = customTokenProviderService.getAuthenticationByEmail(token.getUserEmail());
//        DefaultAssert.isTrue(token.getUserEmail().equals(authentication.getName()), "사용자 인증에 실패하였습니다.");

        return true;
    }


}
