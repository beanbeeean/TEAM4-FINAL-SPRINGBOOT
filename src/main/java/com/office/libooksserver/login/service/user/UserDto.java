package com.office.libooksserver.login.service.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private long u_no;
    private String u_reg_date;
    private String u_mod_date;
    private String u_email;
    private Boolean u_address;
    private String u_image;
    private String u_name;
    private String u_password;
    private String u_provider;
    private String u_phone;
    private String u_role;
    private int u_state;

    @Builder
    public UserDto(String u_name, String u_email, String u_password, String u_role, String u_provider, int u_state){
        this.u_name = u_name;
        this.u_email = u_email;
        this.u_password = u_password;
        this.u_role = u_role;
        this.u_provider = u_provider;
        this.u_state = u_state;
    }

    public void updateName(String name){
        this.u_name = name;
    }
}
//임시로 만듬 아직 사용 x
