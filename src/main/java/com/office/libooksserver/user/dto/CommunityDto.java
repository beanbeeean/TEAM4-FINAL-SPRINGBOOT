package com.office.libooksserver.user.dto;

import lombok.Data;

@Data
public class CommunityDto {
    private int c_no;
    private int u_id;
    private int c_category;
    private String c_title;
    private String c_content;
    private int c_hit;
    private int c_state;
    private String c_reg_date;
    private String c_mod_date;
}
