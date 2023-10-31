package com.office.libooksserver.user.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.office.libooksserver.user.dto.ChatDto;
import com.office.libooksserver.user.dto.ChatRoomDto;
import com.office.libooksserver.user.service.implement.IChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public ChatRoomDto createChatRoom(String roomName, String userMaxCount, String userName){
        try{
            ChatRoomDto chatRoom = new ChatRoomDto().create(roomName, Integer.parseInt(userMaxCount)); // 채팅룸 이름으로 채팅 룸 생성 후

            // Save Lyric To DynamoDB
//            Map<String, String> user = userList.get(0);
//            String userName = user.get("userName");
            DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
//            System.out.println("RommID : "+  chatRoom.getRoomId());
            int result = iChatMapper.insertChatRoomForUser(userName, chatRoom.getRoomId());
            if(result > 0){
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
    public List<ChatRoomDto> findRoomByUserMail(String userName){

        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);

        List<String> roomIds = iChatMapper.getUsersChatRooms(userName);
        List<ChatRoomDto> chatRooms = new ArrayList<>();
        for (String id: roomIds) {
            ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, id);
            chatRooms.add(chatRoom);
        }

//        System.out.println("chatRooms + "+ chatRooms);

        return chatRooms;
    }

    public ChatRoomDto findRoomByRoomId(String roomId){
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, roomId);
        return chatRoom;
    }

    // 채팅방 인원+1
    public int plusUserCnt(String roomId, String u_mail){

        int result = iChatMapper.isAlreadyJoined(roomId, u_mail);
        if(result < 1){
            DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
            ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, roomId);
            if(chatRoom.getUserMaxCount() == chatRoom.getUserCount()){
                result = -1;
                return result;
            }else{
                int insertSQL = iChatMapper.insertChatRoomForUser(u_mail,roomId);
                if(insertSQL > 0){
                    chatRoom.setUserCount(chatRoom.getUserCount()+1);
                    mapper.save(chatRoom);
                }
            }


        }
        return result;
    }

    public void saveChatList(ChatDto chatDto){
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, chatDto.getRoomId());
        ArrayList<Map<String,String>> oriMap = chatRoom.getChat();
        Map<String, String> map = new HashMap<>();
        map.put("user", chatDto.getSender());
        map.put("msg", chatDto.getMessage());
        map.put("time", LocalDateTime.now().toString());

        oriMap.add(map);
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

//    채팅방 인원-1 & 나가기 따로 만들기
    public int minusUserCnt(String roomId, String u_mail){

        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        ChatRoomDto chatRoom = mapper.load(ChatRoomDto.class, roomId);
        int result = iChatMapper.deleteChatRoomForUser(u_mail,roomId);
        if(result > 0){
            chatRoom.setUserCount(chatRoom.getUserCount()-1);
            mapper.save(chatRoom);
        }
        return result;
    }
}
