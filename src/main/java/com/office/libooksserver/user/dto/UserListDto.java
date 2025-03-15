package com.office.libooksserver.user.dto;

import lombok.Data;

@Data
public class UserListDto {
    private int uc_no;
    private String u_mail;
    private String u_name;
    private String room_id;
    private int join_idx;
    private String uc_reg_date;
    private String uc_mod_date;

    private long u_no;
    private String u_reg_date;
    private String u_mod_date;
    private String u_email;
    private Boolean u_address;
    private String u_image;
    private String u_password;
    private String u_provider;
    private String u_phone;
    private String u_role;
    private String u_state;

}
