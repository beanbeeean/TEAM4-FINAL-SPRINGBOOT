package com.office.libooksserver.admin.controller;

import com.office.libooksserver.reservation.readroom.ReadRoomDto;
import com.office.libooksserver.reservation.readroom.ReadRoomService;
import com.office.libooksserver.reservation.studyroom.StudyRoomService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/admin/reservation/*")
public class AdminReservation {

    @Autowired
    ReadRoomService readRoomService;

    @Autowired
    StudyRoomService studyRoomService;
    @PostMapping("/readRoom")
    public ResponseEntity<?> readRoom(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword ) {

        log.info("keyword : ",keyword);
        log.info(readRoomService.adminReadRoom(keyword));

        return ResponseEntity.ok(readRoomService.adminReadRoom(keyword));
    }

    @PostMapping("/studyRoom")
    public ResponseEntity<?> studyRoom(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword ) {

        log.info("keyword : ",keyword);
        log.info(readRoomService.adminReadRoom(keyword));

        return ResponseEntity.ok(studyRoomService.adminStudyRoom(keyword));
    }

    @PostMapping("/seat")
    @ResponseBody
    public ResponseEntity<?> adminSeat(@RequestBody ReadRoomDto readRoomDto) {

        System.out.println("seat : " +readRoomDto);

        readRoomService.adminSeat(readRoomDto.getRe_room_no(), readRoomDto.getRe_seat(), readRoomDto.getRe_state());

        return ResponseEntity.ok("");
    }

    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<?> adminRoom(@RequestBody ReadRoomDto readRoomDto) {

        System.out.println("seat : " +readRoomDto);

        readRoomService.adminSeat(readRoomDto.getRe_room_no(), readRoomDto.getRe_seat(), readRoomDto.getRe_state());

        return ResponseEntity.ok("");
    }
}
