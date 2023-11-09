package com.office.libooksserver.admin.controller;

import com.office.libooksserver.reservation.readroom.ReadRoomDto;
import com.office.libooksserver.reservation.readroom.ReadRoomService;
import com.office.libooksserver.reservation.studyroom.StudyRoomDto;
import com.office.libooksserver.reservation.studyroom.StudyRoomService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> adminRoom(@RequestBody Map<String, Object> date) {

        StudyRoomDto studyRoomDto = new StudyRoomDto();


        System.out.println("date : " +date);

        String roomName = (String)date.get("roomName");

        if(roomName.equals("SPACE1 - A")) studyRoomDto.setSr_room(0);
        else if(roomName.equals("SPACE1 - B")) studyRoomDto.setSr_room(1);
        else if(roomName.equals("SPACE2 - A")) studyRoomDto.setSr_room(2);
        else if(roomName.equals("SPACE2 - B")) studyRoomDto.setSr_room(3);
        else if(roomName.equals("SPACE3 - A")) studyRoomDto.setSr_room(4);
        else if(roomName.equals("SPACE3 - B")) studyRoomDto.setSr_room(5);

        int j =  Integer.parseInt(((String) date.get("sDateTime"))) + 9;
        int end = 22;

        for(int i=Integer.parseInt(((String) date.get("sDateYMD"))); i <= Integer.parseInt(((String) date.get("eDateYMD"))); i++){

            if(i==Integer.parseInt(((String) date.get("eDateYMD"))))
                end=Integer.parseInt(((String)date.get("eDateTime")))+ 9;

            for(;j<=end;j++){
                System.out.println("date : " +date);
                studyRoomDto.setSr_date(i);
                studyRoomDto.setSr_email("admin");
                studyRoomDto.setSr_time(j);
                studyRoomDto.setSr_name((String)date.get("roomName"));
                studyRoomService.reservationRoom(studyRoomDto);
            }
            j=8;
        }

        return ResponseEntity.ok("");
    }

}
