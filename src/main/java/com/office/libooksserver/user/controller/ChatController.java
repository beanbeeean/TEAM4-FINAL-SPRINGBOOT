package com.office.libooksserver.user.controller;

import com.office.libooksserver.user.dto.ChatDto;
import com.office.libooksserver.user.dto.ChatRoomDto;
import com.office.libooksserver.user.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;

    @Autowired
    ChatService chatService;

    // MessageMapping 을 통해 webSocket 로 들어오는 메시지를 발신 처리한다.
    // 이때 클라이언트에서는 /pub/chat/message 로 요청하게 되고 이것을 controller 가 받아서 처리한다.
    // 처리가 완료되면 /sub/chat/room/roomId 로 메시지가 전송된다.
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("엔터유저");

        String user = chat.getSender();
        String userName = chat.getSendName();
        // 채팅방 유저+1
        int result = chatService.plusUserCnt(chat.getRoomId(), chat.getSender(),chat.getSendName());
        chat.setCurrentUser(chat.getSender());

        // 채팅방 유저 개설, 입장 시
        if(result == 0){
            headerAccessor.getSessionAttributes().put("userUUID", chat.getSender());
            headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

            chat.setType(ChatDto.MessageType.ENTER);
            chat.setMessage(userName + " 님이 채팅방에 입장하셨습니다.");
            chat.setSender("ADMIN");
            chat.setFirst(true);

            chatService.saveChatList(chat);


            template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);


        } else if (result > 0) { // 개설된 채팅방에 입장 시

            ChatRoomDto chatRoom = chatService.findRoomByRoomId(chat.getRoomId());
            int userJoinIdx = chatService.getUserJoinIdx(chat.getRoomId(), chat.getSender());

            headerAccessor.getSessionAttributes().put("userUUID", chat.getSender());
            headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());
            for (Map<String, String> ct: chatRoom.getChat()) {

                if(userJoinIdx > Integer.parseInt(ct.get("idx"))){
                    continue;
                }
                chat.setMessage(ct.get("msg"));
                chat.setSender(ct.get("user"));
                chat.setDate(ct.get("date"));
                chat.setTime(ct.get("time"));

                chat.setFirst(false);
                template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
                System.out.println("chat : " + chat);
            }
        }else{
            chat.setType(ChatDto.MessageType.REJECT);
            template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
        }
    }

    @MessageMapping("/chat/leaveUser")
    public void leaveUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("LEAVE 유저");
        System.out.println("cccc"+chat.getSendName());
        int result = chatService.minusUserCnt(chat.getRoomId(), chat.getSender());

        if(result > 0){
            chat.setType(ChatDto.MessageType.LEAVE);
            chat.setMessage(chat.getSendName() + " 님이 채팅방에서 나가셨습니다.");
            chat.setSender("ADMIN");
            template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
            chatService.saveChatList(chat);
        }

    }

//    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat) {
        log.info("CHAT {}", chat);
        String user = chat.getSender();
        ChatDto.MessageType type = chat.getType();
        String msg = chat.getMessage();

        ChatRoomDto chatRoom = chatService.findRoomByRoomId(chat.getRoomId());
        String lastDay = chatRoom.getChat().get(chatRoom.getChat().size() -1).get("date");
        if(!lastDay.equals(LocalDate.now().toString())){
            chat.setMessage((LocalDate.now().toString().substring(0,4) + "년 " +  LocalDate.now().toString().substring(5,7) + "월 " + LocalDate.now().toString().substring(8,10)+"일"));
            chat.setSender("ADMIN");
            chat.setType(ChatDto.MessageType.NOTICE);
            template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
        }


        chat.setSender(user);
        chat.setMessage(msg);
        chat.setDate(LocalDate.now().toString());
        chat.setType(type);
        chatService.saveChatList(chat);
        String half = "";
        if(LocalTime.now().getHour() / 12 >= 1){
            half = "PM";
        }else {
            half = "AM";
        }
        chat.setTime(half + " " + (LocalTime.now().getHour()%12 < 10 ? "0"+ String.valueOf(LocalTime.now().getHour()%12): LocalTime.now().getHour()%12) + ":" + (LocalTime.now().getMinute() < 10 ? "0" + String.valueOf(LocalTime.now().getMinute()) : LocalTime.now().getMinute()));
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

//    // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);

    }
//
//    // 채팅에 참여한 유저 리스트 반환
//    @GetMapping("/chat/userlist")
//    @ResponseBody
//    public ArrayList<String> userList(String roomId) {
//
//        return repository.getUserList(roomId);
//    }
//
//    // 채팅에 참여한 유저 닉네임 중복 확인
//    @GetMapping("/chat/duplicateName")
//    @ResponseBody
//    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {
//
//        // 유저 이름 확인
//        String userName = repository.isDuplicateName(roomId, username);
//        log.info("동작확인 {}", userName);
//
//        return userName;
//    }
}