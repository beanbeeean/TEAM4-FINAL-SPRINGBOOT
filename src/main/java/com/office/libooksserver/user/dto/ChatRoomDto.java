package com.office.libooksserver.user.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Stomp 를 통해 pub/sub 를 사용하면 구독자 관리가 알아서 된다!!
// 따라서 따로 세션 관리를 하는 코드를 작성할 필도 없고,
// 메시지를 다른 세션의 클라이언트에게 발송하는 것도 구현 필요가 없다!
@Data
@DynamoDBTable(tableName="Libooks-Chat")
//@Getter
//@AllArgsConstructor
public class ChatRoomDto {

    @DynamoDBHashKey
    private String roomId; // 채팅방 아이디

    @DynamoDBAttribute
    private String roomName; // 채팅방 이름

    @DynamoDBAttribute
    private int userCount; // 채팅방 인원수

//    @DynamoDBAttribute
//    private ArrayList<Map<String, String>> userList;

    @DynamoDBAttribute
    private int userMaxCount;

    @DynamoDBAttribute
    private ArrayList<Map<String, String>> chat;

    @DynamoDBAttribute
    private int cNo;

    public ChatRoomDto create(String roomName, int userMaxCount, int cNo){
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.roomId = UUID.randomUUID().toString();
        chatRoomDto.roomName = roomName;
        chatRoomDto.userCount = 1;
        chatRoomDto.userMaxCount = userMaxCount;
        chatRoomDto.cNo = cNo;

        ArrayList<Map<String, String>> chat = new ArrayList<>();
        Map<String,String> map = new HashMap<>();

        map.put("idx", "0");
        map.put("user", "ADMIN");
        map.put("msg", (LocalDate.now().toString().substring(0,4) + "년 " +  LocalDate.now().toString().substring(5,7) + "월 " + LocalDate.now().toString().substring(8,10)+"일"));
        map.put("date", LocalDate.now().toString());
        map.put("time", LocalTime.now().toString());
        map.put("type", ChatDto.MessageType.NOTICE.toString());

        chat.add(map);
        chatRoomDto.chat = chat;

        return chatRoomDto;
    }

}