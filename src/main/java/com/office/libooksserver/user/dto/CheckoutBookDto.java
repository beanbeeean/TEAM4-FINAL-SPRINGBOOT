package com.office.libooksserver.user.dto;

import lombok.Data;

@Data
public class CheckoutBookDto {
    private int c_no;
    private int b_no;
    private int u_no;
    private String c_start_date;
    private String c_end_date;
    private int c_state;
    private String c_mod_date;
    private String c_reg_date;
}
