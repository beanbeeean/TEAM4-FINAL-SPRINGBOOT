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
}
