package com.office.libooksserver.reservation.readroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/read/*")
public class ReadRoomController {

    @Autowired
    ReadRoomService readService;

    @GetMapping("/seat")
    @ResponseBody
    public List<ReadRoomDto> checkSeat() {

        return readService.checkSeat();
    }

    @PostMapping("/reservation")
    @ResponseBody
    public void reservationSeat(@RequestBody ReadRoomDto readRoomDto) {

        System.out.println(readRoomDto.getRe_room_no());
        System.out.println(readRoomDto.getRe_reservation());
        System.out.println(readRoomDto.getRe_seat());

        readService.reservationSeat(readRoomDto);
    }
}
