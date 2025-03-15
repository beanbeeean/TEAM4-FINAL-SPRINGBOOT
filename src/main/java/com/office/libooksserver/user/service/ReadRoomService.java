package com.office.libooksserver.user.service;

import com.office.libooksserver.user.dto.ReadRoomDto;
import com.office.libooksserver.user.service.implement.ReadRoomMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ReadRoomService {

    @Autowired
    ReadRoomMapper readMapper;

    public List<ReadRoomDto> checkSeat(){

        return readMapper.checkSeat();

    }
    public void reservationSeat(ReadRoomDto readRoomDto){

        readMapper.reservationSeat(readRoomDto);

    }
    public void userReservation(ReadRoomDto readRoomDto){

        readMapper.userReservation(readRoomDto);

    }
    public List<ReadRoomDto> adminReadRoom(String keyword){

        return readMapper.adminReadRoom(keyword);

    }

    public void adminSeat(int re_room_no, int re_seat, int re_state){

        readMapper.adminSeat(re_room_no, re_seat, re_state);

    }

    public int checkDuplicate(ReadRoomDto readRoomDto) {
        return readMapper.checkDuplicate(readRoomDto.getL_email());
    }
}
