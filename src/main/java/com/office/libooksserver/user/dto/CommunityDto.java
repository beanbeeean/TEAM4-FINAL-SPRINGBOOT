package com.office.libooksserver.user.dto;

import lombok.Data;

@Data
public class CommunityDto {
    private int c_no;
    private String u_email;
    private int c_category;
    private String c_title;
    private String c_content;
    private int c_hit;
    private int c_state;
    private String c_reg_date;
    private String c_mod_date;

    private long u_no;
    private String u_reg_date;
    private String u_mod_date;
    private Boolean u_address;
    private String u_image;
    private String u_name;
    private String u_password;
    private String u_provider;
    private String u_phone;
    private String u_role;
    private String u_state;
}
