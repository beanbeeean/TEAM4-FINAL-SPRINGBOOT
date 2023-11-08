package com.office.libooksserver.user.controller;

import com.office.libooksserver.user.dto.ChatRoomDto;
import com.office.libooksserver.user.dto.UserListDto;
import com.office.libooksserver.user.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class ChatRoomController {

    // ChatRepository Bean 가져오기
    @Autowired
    private ChatService chatService;

    // 채팅 리스트 화면(연결)
    // / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return
    @GetMapping("/chat/list")
    @ResponseBody
    public Map<String,Object> getChatRoom(@RequestParam String user){
        Map<String,Object> returnMap = new HashMap<>();

        returnMap.put("list", chatService.findRoomByUserMail(user));
//        returnMap.put("list", chatService.findRoomAllRoom());

        return returnMap;
    }
    // 채팅방 생성(연결)
    @PostMapping("/chat/duplicate")
    @ResponseBody
    public Map<String,Integer> duplicateRoom(@RequestBody Map<String,String> roomName) {
        System.out.println("map :: "+ roomName.get("newName"));
        int isExisted = chatService.isDuplicateRoomName(roomName.get("newName"));

        Map<String, Integer> returnMap = new HashMap<>();
        returnMap.put("isDuplicate", isExisted);
        return returnMap;
    }


    // 채팅방 생성(연결)
    @PostMapping("/chat/createroom")
    @ResponseBody
    public Map<String,Object> createRoom(@RequestBody Map<String,String> roomInfo) {

        System.out.println("create ROOM CONNECT");

        ChatRoomDto room = chatService.createChatRoom(roomInfo.get("newName"), roomInfo.get("userMaxCount"), roomInfo.get("userMail"), roomInfo.get("userName"), roomInfo.get("cNo"));
        log.info("CREATE Chat Room {}", room);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("roomName",room);
        return returnMap;
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.

    @GetMapping("/chat/room")
    @ResponseBody
    public Map<String,Object> roomDetail(String roomId){
        log.info("roomId {}", roomId);
        ChatRoomDto chatRoom = chatService.findRoomByRoomId(roomId);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("room",chatRoom);
        return returnMap;
    }

    @GetMapping("/chat/userlist")
    @ResponseBody
    public Map<String, Object> getUserList(String roomId){
        log.info("roomId {}", roomId);
        Map<String, Object> returnMap = chatService.getUserList(roomId);
        return returnMap;
    }

    @GetMapping("/chat/room_cno")
    @ResponseBody
    public Map<String,Object> getRoomByCno(String cNo){
        log.info("cNo {}", cNo);
        ChatRoomDto chatRoom = chatService.findRoomByCno(cNo);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("room",chatRoom);
        return returnMap;
    }


}