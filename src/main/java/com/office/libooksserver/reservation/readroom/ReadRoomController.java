package com.office.libooksserver.reservation.readroom;

import com.office.libooksserver.login.config.security.token.CurrentUser;
import com.office.libooksserver.login.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Parameter;
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
    public void reservationSeat(@RequestBody ReadRoomDto readRoomDto, @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {


        System.out.println(readRoomDto);
        readRoomDto.setL_email(userPrincipal.getU_email());

        readService.reservationSeat(readRoomDto);
        readService.userReservation(readRoomDto);
    }
}
