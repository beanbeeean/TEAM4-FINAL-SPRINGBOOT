package com.office.libooksserver.reservation.readroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadRoomService {
    @Autowired
    ReadRoomMapper readMapper;

    public List<ReadRoomDto> checkSeat(){
        return readMapper.checkSeat();
    }
    public void reservationSeat(){ readMapper.reservationSeat(); }

}
