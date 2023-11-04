package com.office.libooksserver.user.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.office.libooksserver.user.dto.ChatDto;
import com.office.libooksserver.user.dto.ChatRoomDto;
import com.office.libooksserver.user.dto.UserListDto;
import com.office.libooksserver.user.service.implement.IChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final AmazonDynamoDBClient amazonDynamoDBClient;

    @Autowired
    IChatMapper iChatMapper;

    @Autowired
    public ChatService(AmazonDynamoDBClient amazonDynamoDBClient) {
        this.amazonDynamoDBClient = amazonDynamoDBClient;
    }

    // 채팅방 생성
    public ChatRoomDto createChatRoom(String roomName, String userMaxCount, String userMail, String userName){
        try{
            ChatRoomDto chatRoom = new ChatRoomDto().create(roomName, Integer.parseInt(userMaxCount)); // 채팅룸 이름으로 채팅 룸 생성 후

            DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
            int result = iChatMapper.insertChatRoomForUser(userMail, userName, chatRoom.getRoomId(), "0");

                if(result > 0){
                    Thread.sleep(1000);
                    mapper.save(chatRoom);
                    return chatRoom;
                }else{
                    return null;
                }

        } catch(Exception e){
            System.out.println("e:: " + e);;
            return null;
        }
    }


    // 유저가 포함된 채팅방 조회
    public List<ChatRoomDto> findRoomByUserMail(String u_mail){

        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);

        List<String> roomIds = iChatMapper.getUsersChatRooms(u_mail);
        List<ChatRoomDto> chatRooms = new ArrayList<>();
        for (String id: roomIds) {
            ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, id);
            chatRooms.add(chatRoom);
        }

//        System.out.println("chatRooms + "+ chatRooms);

        return chatRooms;
    }

    // 채팅방 ID로 채팅방 찾기
    public ChatRoomDto findRoomByRoomId(String roomId){
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, roomId);
        return chatRoom;
    }

    public int getUserJoinIdx(String roomId, String u_mail){
        return Integer.parseInt(iChatMapper.getUserJoinIdx(roomId,u_mail));
    }

    // 채팅방 인원+1
    public int plusUserCnt(String roomId, String u_mail, String u_name){

        int result = 0;
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, roomId);

        try{
            result = iChatMapper.isAlreadyJoined(roomId, u_mail);
            if(result < 1){
                if(chatRoom.getUserMaxCount() == chatRoom.getUserCount()){
                    result = -1;
                    return result;
                }else{
                    int insertSQL = iChatMapper.insertChatRoomForUser(u_mail,u_name, roomId, String.valueOf(Integer.parseInt(chatRoom.getChat().get(chatRoom.getChat().size()-1).get("idx")) + 1));
                    Thread.sleep(1000);
                    if(insertSQL > 0){
                        chatRoom.setUserCount(chatRoom.getUserCount()+1);
                        mapper.save(chatRoom);
                        result = 0;
                    }
                }

            }
            return result;
        }catch(Exception e){
            System.out.println("e " + e);
            return result;
        }

    }

    // 채팅 저장
    public void saveChatList(ChatDto chatDto){
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, chatDto.getRoomId());
        ArrayList<Map<String,String>> oriMap = chatRoom.getChat();

        if(!oriMap.get(chatRoom.getChat().size()-1).get("date").equals(LocalDate.now().toString())){
            Map<String, String> map = new HashMap<>();

            map.put("idx", String.valueOf(Integer.parseInt(oriMap.get(chatRoom.getChat().size()-1).get("idx")) + 1));
            map.put("user", "ADMIN");
            map.put("msg", (LocalDate.now().toString().substring(0,4) + "년 " +  LocalDate.now().toString().substring(5,7) + "월 " + LocalDate.now().toString().substring(8,10)+"일"));
            map.put("date", LocalDate.now().toString());
            map.put("time", LocalTime.now().toString());
            map.put("type", ChatDto.MessageType.NOTICE.toString());
            oriMap.add(map);
            chatRoom.setChat(oriMap);
            mapper.save(chatRoom);
        }


        Map<String, String> talkMap = new HashMap<>();
        String half = "";
        if(LocalTime.now().getHour() / 12 >= 1){
            half = "PM";
        }else {
            half = "AM";
        }
        String time = (half + " " + (LocalTime.now().getHour()%12 < 10 ? "0"+ String.valueOf(LocalTime.now().getHour()%12): LocalTime.now().getHour()%12) + ":" + (LocalTime.now().getMinute() < 10 ? "0" + String.valueOf(LocalTime.now().getMinute()) : LocalTime.now().getMinute()));

        talkMap.put("idx",String.valueOf(Integer.parseInt(oriMap.get(chatRoom.getChat().size()-1).get("idx")) + 1) );
        talkMap.put("user", chatDto.getSender());
        talkMap.put("msg", chatDto.getMessage());
        talkMap.put("date", LocalDate.now().toString());
        talkMap.put("time", time);
        talkMap.put("type", chatDto.getType().toString());

        oriMap.add(talkMap);
        chatRoom.setChat(oriMap);
        mapper.save(chatRoom);

    }

    // 전체 조회(임시)
    public List<ChatRoomDto> findRoomAllRoom() {
//
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);

        List<ChatRoomDto> chatRooms = mapper.scan(ChatRoomDto.class, new DynamoDBScanExpression());
        return chatRooms;
    }

//    채팅방 인원-1 & 나가기
    public int minusUserCnt(String roomId, String u_mail){

        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, roomId);
        if(chatRoom.getUserCount() == 0){
            iChatMapper.deleteChatRoomForUser(u_mail,roomId);
            mapper.delete(roomId);
            return -1;
        }else{
            int result = iChatMapper.deleteChatRoomForUser(u_mail,roomId);
            if(result > 0){
                chatRoom.setUserCount(chatRoom.getUserCount()-1);
                mapper.save(chatRoom);
            }
            return result;
        }

    }

    public ArrayList<UserListDto> getUserList(String roomId) {
        return iChatMapper.getUserList(roomId);
    }
}
