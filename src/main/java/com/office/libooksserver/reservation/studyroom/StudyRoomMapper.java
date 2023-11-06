package com.office.libooksserver.reservation.studyroom;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyRoomMapper {
    List<StudyRoomDto> checkRoom(int sr_date, int sr_room);

    void reservationRoom(StudyRoomDto studyRoomDto);
}
