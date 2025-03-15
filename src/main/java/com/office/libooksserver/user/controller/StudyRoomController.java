package com.office.libooksserver.user.controller;

import com.office.libooksserver.login.config.security.token.CurrentUser;
import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.user.dto.StudyRoomDto;
import com.office.libooksserver.user.service.StudyRoomService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/study/*")
public class StudyRoomController {

    @Autowired
    StudyRoomService studyRoomService;

    @PostMapping("/room")
    public ResponseEntity<?> checkRoom(@RequestBody Map<Object, Integer> room) {

        System.out.println("room : " + room);
        System.out.println("space : " + room.get("space"));


        List<StudyRoomDto> roomTime = studyRoomService.checkRoom(room.get("date"),(room.get("space")-1)*2);

        System.out.println("roomTime : " + roomTime);

        int[][] time;
        LocalDateTime localDateTime = LocalDateTime.now();
        int hour = localDateTime.getHour();
        int day = localDateTime.getDayOfMonth();

        int today = room.get("date");
        int now = today%100;

        time = new int[6][24];

        for(int i = 8; i <= hour; i++){
            if(hour > 22)
                break;
            for(int j = 0; j < 6; j++){
                if(day==now) {
                    time[j][i] = 1;
                }
            }
        }

        for(int i = 0; i < roomTime.size(); i++){
            time[roomTime.get(i).getSr_room()][roomTime.get(i).getSr_time()]=1;

            System.out.println(" time[roomTime.get(i).getSr_room()][roomTime.get(i).getSr_time()] : " +  time[roomTime.get(i).getSr_room()][roomTime.get(i).getSr_time()]);
        }

        return ResponseEntity.ok(time);
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> reservationRoom(@RequestBody StudyRoomDto studyRoomDto, @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {

        studyRoomDto.setSr_email(userPrincipal.getU_email());

        System.out.println("studyRoomDto : " + studyRoomDto);

        for(int i=0; i<studyRoomDto.getTime();i++) {
            studyRoomService.reservationRoom(studyRoomDto);
            studyRoomDto.setSr_time(studyRoomDto.getSr_time()+1);
        }

        return ResponseEntity.ok("");
    }

}
