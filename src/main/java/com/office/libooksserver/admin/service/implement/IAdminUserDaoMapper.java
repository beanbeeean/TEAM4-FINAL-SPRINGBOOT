package com.office.libooksserver.admin.service.implement;

import com.office.libooksserver.login.service.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminUserDaoMapper {
//    List<BookDto> selectBooksByFiltering(String category, String keyword);
//
//    List<BookDto> selectByChkUserList(int bNo);
//
//    int updateBookInfo(int no, int stock, int state);
//
//    int changeChkBookState(int chkBNo);
//
//    void increaseBookStock(int bNo);

    List<UserDto> selectUsersByFiltering(String keyword);
}
