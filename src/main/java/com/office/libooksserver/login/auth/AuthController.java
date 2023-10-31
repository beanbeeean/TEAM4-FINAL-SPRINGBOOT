package com.office.libooksserver.login.auth;


import com.office.libooksserver.login.advice.payload.ErrorResponse;
import com.office.libooksserver.login.config.security.token.CurrentUser;
import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.login.domain.entity.user.User;
import com.office.libooksserver.login.payload.request.auth.ChangePasswordRequest;
import com.office.libooksserver.login.payload.request.auth.RefreshTokenRequest;
import com.office.libooksserver.login.payload.request.auth.SignInRequest;
import com.office.libooksserver.login.payload.request.auth.SignUpRequest;
import com.office.libooksserver.login.payload.response.AuthResponse;
import com.office.libooksserver.login.payload.response.Message;
import com.office.libooksserver.login.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Authorization", description = "Authorization API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    //    API endpoint를 정의하는 부분으로, Spring Web과 OpenAPI (이전의 Swagger) 애너테이션을 사용하여 작성되었습니다. 자세한 설명은 다음과 같습니다:
    //    summary: 이 API의 짧은 요약입니다. 여기서는 "유저 정보 확인"이라고 명시되었습니다.
    //    description: API의 상세한 설명입니다. "현제 접속된 유저정보를 확인합니다."라고 설명되어 있습니다.
    //    @Operation: 해당 API endpoint에 대한 간략한 설명을 제공합니다.
    //    @ApiResponses: 이 애너테이션은 API의 가능한 응답들에 대한 정보를 제공합니다.
    @Operation(summary = "유저 정보 확인", description = "현제 접속된 유저정보를 확인합니다.")
    @ApiResponses(value = {
    //        첫 번째 @ApiResponse는 HTTP 200 상태 코드에 대한 응답을 나타냅니다. 이는 "유저 확인 성공"이며, 응답의 형식은 User 클래스로 예상됩니다.
    //        두 번째 @ApiResponse는 HTTP 400 상태 코드에 대한 응답을 나타냅니다. 이는 "유저 확인 실패"이며, 응답의 형식은 ErrorResponse 클래스로 예상됩니다.
        @ApiResponse(responseCode = "200", description = "유저 확인 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class) ) } ),
        @ApiResponse(responseCode = "400", description = "유저 확인 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping(value = "/") //@GetMapping: 이는 HTTP GET 요청을 처리하는 endpoint를 정의합니다. 이 endpoint의 URL 경로는 "/"입니다.
    public ResponseEntity<?> whoAmI( //whoAmI 메서드: 이는 실제 로직을 처리하는 메서드입니다.
        @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    //@Parameter: 이 애너테이션은 API 문서에 파라미터에 대한 설명을 추가합니다. 여기서는 Accesstoken에 대한 설명이 포함되어 있습니다.
    //@CurrentUser: 사용자 정의 애너테이션으로, 현재 로그인된 사용자의 정보를 가져오는 데 사용될 것으로 보입니다.
    ) {
        return authService.whoAmI(userPrincipal);
    }
    //        UserPrincipal userPrincipal: 현재 로그인된 사용자의 정보를 나타내는 객체입니다.
    //        return authService.whoAmI(userPrincipal);: 현재 로그인된 사용자의 정보를 반환하는 서비스 메서드를 호출합니다.
    //        간단히 말하면, 이 endpoint는 현재 로그인된 사용자의 정보를 반환하는 기능을 가진 API입니다. OpenAPI 애너테이션을 사용하여 API 문서화를 돕습니다.

    @Operation(summary = "유저 정보 삭제", description = "현제 접속된 유저정보를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 삭제 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
        @ApiResponse(responseCode = "400", description = "유저 삭제 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @DeleteMapping(value = "/")
    public ResponseEntity<?> delete(
        @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ){
        return authService.delete(userPrincipal);
    }

    @Operation(summary = "유저 정보 갱신", description = "현제 접속된 유저의 비밀번호를 새로 지정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 정보 갱신 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
        @ApiResponse(responseCode = "400", description = "유저 정보 갱신 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PutMapping(value = "/")
    public ResponseEntity<?> modify(
        @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal, 
        @Parameter(description = "Schemas의 ChangePasswordRequest를 참고해주세요.", required = true) @Valid @RequestBody ChangePasswordRequest passwordChangeRequest
    ){
        return authService.modify(userPrincipal, passwordChangeRequest);
    }

    @Operation(summary = "유저 로그인", description = "유저 로그인을 수행합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 로그인 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class) ) } ),
        @ApiResponse(responseCode = "400", description = "유저 로그인 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(
        @Parameter(description = "Schemas의 SignInRequest를 참고해주세요.", required = true) @Valid @RequestBody SignInRequest signInRequest
    ) {
        return authService.signin(signInRequest);
    }

    @Operation(summary = "유저 회원가입", description = "유저 회원가입을 수행합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
        @ApiResponse(responseCode = "400", description = "회원가입 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(
        @Parameter(description = "Schemas의 SignUpRequest를 참고해주세요.", required = true) @Valid @RequestBody SignUpRequest signUpRequest
//      @RequestBody: HTTP 요청의 본문을 Java 객체로 변환하기 위해 사용됩니다. 여기서는 요청 본문을 SignUpRequest 클래스의 객체로 변환합니다.
//      @Valid: 해당 객체의 유효성 검사를 수행하라는 지시입니다. SignUpRequest 클래스에서 어노테이션을 사용해 정의된 유효성 규칙이 있을 경우, 이 규칙들을 검사합니다.
    ) {
        return authService.signup(signUpRequest);
    }

    @Operation(summary = "토큰 갱신", description = "신규 토큰 갱신을 수행합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "토큰 갱신 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class) ) } ),
        @ApiResponse(responseCode = "400", description = "토큰 갱신 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refresh(
        @Parameter(description = "Schemas의 RefreshTokenRequest를 참고해주세요.", required = true) @Valid @RequestBody RefreshTokenRequest tokenRefreshRequest
    ) {
        return authService.refresh(tokenRefreshRequest);
    }

    @Operation(summary = "유저 로그아웃", description = "유저 로그아웃을 수행합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
        @ApiResponse(responseCode = "400", description = "로그아웃 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value="/signout")
    public ResponseEntity<?> signout(
        @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal, 
        @Parameter(description = "Schemas의 RefreshTokenRequest를 참고해주세요.", required = true) @Valid @RequestBody RefreshTokenRequest tokenRefreshRequest
    ) {
        return authService.signout(tokenRefreshRequest);
    }

}

