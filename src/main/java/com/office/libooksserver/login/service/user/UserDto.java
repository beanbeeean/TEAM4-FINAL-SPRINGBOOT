package com.office.libooksserver.login.service.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String created_date;
    private String modified_date;
    private String email;
    private Boolean emailVerified;
    private String image_url;
    private String name;
    private String password;
    private String provider;
    private String providerId;
    private String role;

    @Builder
    public UserDto(String name, String email, String password, String role, String provider, String providerId){
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
//임시로 만듬 아직 사용 x
