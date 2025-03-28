package com.office.libooksserver.user.service.implement;

import com.office.libooksserver.user.dto.StudyRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyRoomMapper {
    List<StudyRoomDto> checkRoom(int sr_date, int sr_room);

    void reservationRoom(StudyRoomDto studyRoomDto);

    List<StudyRoomDto> adminStudyRoom(String keyword);

}
