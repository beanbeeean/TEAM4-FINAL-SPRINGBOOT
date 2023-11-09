package com.office.libooksserver.reservation.studyroom;

import lombok.Data;

@Data
public class StudyRoomDto {
    private int st_no;
    private String st_name;
    private int st_state;

    private int sr_no;
    private int sr_room;
    private int sr_date;
    private int sr_time;
    private int time;
    private String sr_email;
    private String sr_name;
    private String sr_reg_date;
}
