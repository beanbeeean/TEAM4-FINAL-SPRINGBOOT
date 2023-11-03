package com.office.libooksserver.reservation.studyroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/study/*")
public class StudyRoomController {

    @Autowired
    StudyRoomService studyRoomService;

    @PostMapping("/room")
    public ResponseEntity<?> checkRoom(@RequestBody int chosenDay) {

        System.out.println("chosenDay : " + chosenDay);

        System.out.println(studyRoomService.checkRoom(chosenDay));
        return ResponseEntity.ok("");
    }

}
