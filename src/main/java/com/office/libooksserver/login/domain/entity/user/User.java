package com.office.libooksserver.login.domain.entity.user;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.office.libooksserver.login.domain.entity.time.DefaultTime;
//import lombok.Builder;
//import lombok.Getter;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//
//@DynamicUpdate
//@Entity
//@Getter
//@Table(name = "user")
//public class User extends DefaultTime{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Email
//    @Column(nullable = false)
//    private String email;
//
////    private String imageUrl;
//
//    @Column(nullable = false)
//    private Boolean emailVerified = false;
//
//    @JsonIgnore
//    private String password;
//
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private Provider provider;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    private String providerId;
//
//    public User(){}
//
//    @Builder
//    public User(String name, String email, String password, Role role, Provider provider, String providerId){
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.provider = provider;
//        this.role = role;
//    }
//
//    public void updateName(String name){
//        this.name = name;
//    }
//
////    public void updateImageUrl(String imageUrl){
////        this.imageUrl = imageUrl;
////    }
//}


import lombok.Builder;
import lombok.Data;

@Data
public class User {

    private Long id;
    private String name;
    private String email;
    private Boolean emailVerified;
    private String password;  // 비밀번호를 DTO에 포함시키는 것은 보안상 좋지 않을 수 있습니다. 필요에 따라 제거하는 것을 고려하세요.
    private Provider provider;
    private Role role;
    private String providerId;
    // private String imageUrl;  // 주석 처리된 imageUrl도 필요하다면 포함시킬 수 있습니다.

    // 기본 생성자, 모든 필드를 인자로 받는 생성자, getter, setter 등이 포함될 수 있습니다.
    @Builder
    public User(String name, String email, String password, Role role, Provider provider, String providerId){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void updateName(String name){
        this.name = name;
    }
}