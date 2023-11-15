package com.office.libooksserver.user.service.implement;

import com.office.libooksserver.user.dto.ReadRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReadRoomMapper {
    List<ReadRoomDto> checkSeat();

    void reservationSeat(ReadRoomDto readRoomDto);

    void userReservation(ReadRoomDto readRoomDto);

    List<ReadRoomDto> adminReadRoom(String keyword);

    void adminSeat(int re_room_no, int re_seat,int re_state);

    int checkDuplicate(String l_email);
}
