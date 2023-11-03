package com.office.libooksserver.reservation.studyroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyRoomService {
    @Autowired
    StudyRoomMapper studyRoomMapper;

    public List<StudyRoomDto> checkRoom(int sr_date){

        return studyRoomMapper.checkRoom(sr_date);

    }

}
