package com.office.libooksserver.admin.service.implement;

import com.office.libooksserver.user.dto.BookDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminBookDaoMapper {
    List<BookDto> selectBooksByFiltering(String category, String keyword);

    List<BookDto> selectByChkUserList(int bNo);

    int updateBookInfo(int no, int stock, int state);

    int changeChkBookState(int no);
}
