package com.office.libooksserver.reservation.readroom;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReadRoomMapper {
    List<ReadRoomDto> checkSeat();
    void reservationSeat();
}
