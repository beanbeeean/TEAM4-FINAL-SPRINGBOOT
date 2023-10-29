package com.office.libooksserver.user.service;

import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.service.implement.IBookDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class BookService {

    @Autowired
    IBookDaoMapper iBookDaoMapper;

    public void insertBooks(String str) {
        log.info("[BookService] insertBooks()");
        log.info("--------------->str: "+str);



        String key = "ttbtldud43461055001";

        try {
            for(int i=1;i<=4;i++){
                String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbtldud43461055001&QueryType="+
                        str+
                        "&MaxResults=50&start=" +i + "&SearchTarget=Book&Cover=Big&output=js&Version=20131101";

                StringBuilder result = new StringBuilder();
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader br;

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String returnLine;

                while ((returnLine = br.readLine()) != null) {
                    result.append(returnLine);
                }

                JSONParser jsonParser = new JSONParser();

                JSONObject jsonObj = (JSONObject) jsonParser.parse(String.valueOf(result));

                JSONArray array = (JSONArray)jsonObj.get("item");
                log.info(array.size());

                BookDto bookDto = new BookDto();

                int category = 0;
                switch (str) {
                    case "ItemNewAll":
                        category = 1;
                        break;
                    case "ItemNewSpecial":
                        category = 2;
                        break;
                    case "Bestseller":
                        category = 3;
                        break;
                }

                log.info("category-------->"+category);

                for (int j = 0; j < array.size(); j++) {
                    JSONObject jObj = (JSONObject) array.get(j);

                    bookDto.setB_title((String) jObj.get("title"));
                    bookDto.setB_cover((String) jObj.get("cover"));
                    bookDto.setB_author((String) jObj.get("author"));
                    bookDto.setB_publish_date((String) jObj.get("pubDate"));
                    bookDto.setB_description((String) jObj.get("description"));
                    bookDto.setB_link((String) jObj.get("link"));
                    bookDto.setB_isbn((String) jObj.get("isbn"));
                    bookDto.setB_publisher((String) jObj.get("publisher"));
                    bookDto.setB_category(category);

                    int isBook = -1;
                    isBook = iBookDaoMapper.isBook(bookDto.getB_isbn());

                    if(isBook < 1) {
                        iBookDaoMapper.insertNewBook(bookDto);
                    }
                }

                urlConnection.disconnect();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Map<String, Object> showBooks() {
        log.info("[BookService] insertBooks()");

        Map<String, Object> map = new HashMap<>();
        List<BookDto> dtos = iBookDaoMapper.selectBooks();
        log.info("dtos" + dtos.size());

        map.put("dtos",dtos);

        return map;
    }

    public Map<String, Object> showBooks(String category) {
        log.info("[BookService] insertBooks()");
        log.info("category : " + category);

        Map<String, Object> map = new HashMap<>();
        List<BookDto> dtos = iBookDaoMapper.selectBooksByCategory(category);
        log.info("dtos" + dtos.size());

        map.put("dtos",dtos);

        return map;
    }

    public BookDto getBookDetail(int bNo) {
        log.info("[BookService] getBookDetail()");

        BookDto dto =  iBookDaoMapper.getBookDetail(bNo);

        return dto;

    }

    public int checkoutBook(int bNo) {
        log.info("[BookService] checkoutBook()");

        int result = iBookDaoMapper.insertCheckoutBook(bNo);
        if (result > 0) {
            iBookDaoMapper.decreaseBookStock(bNo);
        }

        return result;
    }

//    public Map<String, Object> searchBooks(String keyword) {
//        log.info("[BookService] searchBooks()");
//
//        Map<String, Object> map = new HashMap<>();
//        List<BookDto> dtos = iBookDaoMapper.selectBooks(keyword);
//
//        map.put("productDtos", dtos);
//        return map;
//    }
}
