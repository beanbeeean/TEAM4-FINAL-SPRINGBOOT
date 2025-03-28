package com.office.libooksserver.user.service;

import com.office.libooksserver.user.dto.StudyRoomDto;
import com.office.libooksserver.user.service.implement.StudyRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyRoomService {
    @Autowired
    StudyRoomMapper studyRoomMapper;

    public List<StudyRoomDto> checkRoom(int sr_date, int sr_room){

        return studyRoomMapper.checkRoom(sr_date, sr_room);

    }

    public void reservationRoom(StudyRoomDto studyRoomDto){

        studyRoomMapper.reservationRoom(studyRoomDto);

    }

    public List<StudyRoomDto> adminStudyRoom(String keyword){

        return studyRoomMapper.adminStudyRoom(keyword);
    }

}
