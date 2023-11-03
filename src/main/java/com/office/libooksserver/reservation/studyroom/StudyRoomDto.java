package com.office.libooksserver.reservation.studyroom;

import lombok.Data;

@Data
public class StudyRoomDto {
    private int st_no;
    private String st_name;
    private int st_state;

    private int sr_no;
    private int sr_time;
    private String sr_user;
    private int sr_date;
    private String sr_reg_date;
}
