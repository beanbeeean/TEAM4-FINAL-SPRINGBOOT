package com.office.libooksserver.user.dto;

import lombok.Data;

@Data
public class CheckoutBookDto {
    private int chk_b_no;
    private int b_no;
    private int u_no;
    private String chk_b_start_date;
    private String chk_b_end_date;
    private int chk_b_state;
    private String chk_b_mod_date;
    private String chk_b_reg_date;
}
