package com.office.libooksserver.common.dto;

import lombok.Data;

@Data
public class BookDto {
    private int b_no;
    private String b_isbn;
    private String  b_cover;
    private String b_title;
    private String b_author;
    private String b_publisher;
    private String b_publish_date;
    private String b_description;
    private String b_link;
    private int b_stock;
    private int b_state;
    private String b_reg_date;
    private String b_mod_date;
}
