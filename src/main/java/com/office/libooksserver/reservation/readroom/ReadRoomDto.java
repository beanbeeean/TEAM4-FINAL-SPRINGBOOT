package com.office.libooksserver.reservation.readroom;

import lombok.Data;

@Data
public class ReadRoomDto {
    private int re_no;
    private int re_room_no;
    private int re_seat;
    private String re_reservation;
    private int re_state;
}
