package com.office.libooksserver.login.service.user;

import com.office.libooksserver.reservation.readroom.ReadRoomDto;
import com.office.libooksserver.reservation.studyroom.StudyRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDto findByid(Long u_no);

    UserDto findByEmail(String u_email);

    void userUpdate(UserDto userDto);

    void imgUpdate(String u_email,String u_image);

    void save(UserDto userDto);

    void update(UserDto userDto);

    void delete(Long u_no);

    Boolean existsByEmail(String u_email);

    List<ReadRoomDto> myReadReservation(String start, String end, String u_email);

    List<StudyRoomDto>  myStudyReservation(int start, int end, String u_email);
}
