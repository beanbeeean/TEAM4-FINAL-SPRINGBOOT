package com.office.libooksserver.user.dto;

import lombok.Data;

@Data
public class ReplyDto {
    private int r_no;
    private int c_no;
    private String r_comment;
    private int u_no;
    private int r_target_r_no;
    private String r_reg_date;
    private String r_mod_date;
}
