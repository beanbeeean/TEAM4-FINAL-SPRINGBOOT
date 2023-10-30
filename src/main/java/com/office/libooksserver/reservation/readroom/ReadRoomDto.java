package com.office.libooksserver.reservation.readroom;

import lombok.Data;

@Data
public class ReadRoomDto {
    private int s_no;
    private int re_room_no;
    private int s_name;
    private String s_reservation;
    private int s_state;
}
