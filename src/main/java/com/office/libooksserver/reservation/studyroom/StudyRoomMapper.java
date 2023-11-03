package com.office.libooksserver.reservation.studyroom;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyRoomMapper {
    List<StudyRoomDto> checkRoom(int sr_date);
}
