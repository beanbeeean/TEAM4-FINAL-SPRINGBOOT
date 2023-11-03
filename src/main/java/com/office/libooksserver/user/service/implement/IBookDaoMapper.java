package com.office.libooksserver.user.service.implement;

import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.dto.CheckoutBookDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBookDaoMapper {
    int isBook(String bIsbn);

    int insertNewBook(BookDto bookDto);

//    List<BookDto> selectBooks();
//    List<BookDto> selectBooks(String keyword);
    List<BookDto> selectBooksByFiltering(String category, String keyword);

    BookDto getBookDetail(int bNo);

    int insertCheckoutBook(int bNo, String uMail);

    void decreaseBookStock(int bNo);

    List<BookDto> selectChkBookList();
}
