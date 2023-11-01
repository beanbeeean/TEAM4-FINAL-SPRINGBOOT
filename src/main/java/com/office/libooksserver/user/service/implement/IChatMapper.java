package com.office.libooksserver.user.service.implement;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IChatMapper {

    int insertChatRoomForUser(String u_mail, String roomId, String now);

    List<String> getUsersChatRooms(String u_mail);

    int isAlreadyJoined(String roomId, String u_mail);

    int deleteChatRoomForUser(String u_mail, String roomId);

    String getUserJoinDate(String roomId, String u_mail);
}
