package com.office.libooksserver.login.config.docs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class OpenApiConfig {

    private final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI openAPI(@Value("OpenAPI") String appVersion) {

        log.info("openAPI[]");

        Info info = new Info().title("Demo API").version(appVersion)
                .description("Spring Boot를 이용한 Demo 웹 애플리케이션 API입니다.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("name").url("https://name.name.name/").email("name@name.name"))
                .license(new License().name("Apache License Version 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                    new Components()
                        .addSecuritySchemes(securitySchemeName,
                            new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                )
                .info(info);
    }
}


//    Spring Boot 웹 애플리케이션에서 Swagger를 사용하여 API 문서를 생성하기 위한 설정을 제공합니다. Swagger (현재 OpenAPI로도 불림)는 RESTful API의 설계, 구축, 문서화 및 소비를 도와주는 프레임워크입니다. 주요 부분들을 설명하겠습니다.
//
//@Configuration:
//
//        해당 클래스가 Spring의 Java 기반 설정 파일임을 나타내는 애너테이션입니다.
//private final String securitySchemeName = "bearerAuth";:
//
//        보안 스키마의 이름을 bearerAuth로 설정합니다. JWT를 사용하는 API 인증에 사용됩니다.
//@Bean public OpenAPI openAPI(...) { ... }:
//
//        OpenAPI 타입의 빈을 생성하는 메서드입니다. 이 빈은 Swagger의 전반적인 설정을 포함합니다.
//
//@Value("OpenAPI") String appVersion: Spring의 @Value 애너테이션을 사용하여 appVersion 매개변수에 "OpenAPI" 문자열을 주입합니다.
//
//        Info: API 문서의 기본 정보를 설정합니다. (API의 제목, 버전, 설명, 사용 조건, 연락처 정보 및 라이선스 정보 포함)
//
//        .addSecurityItem(...): API에 보안 요구 사항을 추가합니다. 여기서는 "bearerAuth" 보안 스키마를 사용합니다.
//
//        .components(...): 보안 스키마 및 기타 컴포넌트를 추가합니다. 보안 스키마는 JWT를 사용한 Bearer 인증을 위해 설정되었습니다.
//
//        이 설정을 통해 생성된 API 문서는 웹 브라우저에서 Swagger UI를 통해 볼 수 있습니다. 그리고 해당 문서에는 API에 대한 정보, 엔드포인트, 인증 방법 등이 포함됩니다.