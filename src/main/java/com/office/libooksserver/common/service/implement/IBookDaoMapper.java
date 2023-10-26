package com.office.libooksserver.common.service.implement;

import com.office.libooksserver.common.dto.BookDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IBookDaoMapper {
    int isBook(String bIsbn);

    int insertNewBook(BookDto bookDto);

    List<BookDto> selectBooks();

    BookDto getBookDetail(int bNo);
}
