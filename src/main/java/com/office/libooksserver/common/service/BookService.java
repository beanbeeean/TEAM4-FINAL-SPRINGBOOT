package com.office.libooksserver.common.service;

import com.office.libooksserver.common.dto.BookDto;
import com.office.libooksserver.common.service.implement.IBookDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Log4j2
public class BookService {

    @Autowired
    IBookDaoMapper iBookDaoMapper;

    public void insertBooks() {
        log.info("[BookService] insertBooks()");

        StringBuilder result = new StringBuilder();

        String key = "ttbtldud43461055001";

        try {

            String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbtldud43461055001&QueryType=ItemNewAll&MaxResults=20&start=1&SearchTarget=Book&Cover=Big&output=js&Version=20131101";

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
            for (int i = 0; i < array.size(); i++) {
                log.info(array.get(i));

                JSONObject jObj = (JSONObject) array.get(i);

                bookDto.setB_title((String) jObj.get("title"));
                bookDto.setB_cover((String) jObj.get("cover"));
                bookDto.setB_author((String) jObj.get("author"));
                bookDto.setB_publish_date((String) jObj.get("pubDate"));
                bookDto.setB_description((String) jObj.get("description"));
                bookDto.setB_link((String) jObj.get("link"));
                bookDto.setB_isbn((String) jObj.get("isbn"));
                bookDto.setB_publisher((String) jObj.get("publisher"));

                int isBook = -1;
                isBook = iBookDaoMapper.isBook(bookDto.getB_isbn());

                if(isBook < 1) {
                    iBookDaoMapper.insertNewBook(bookDto);
                }
            }

            urlConnection.disconnect();

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

    public BookDto getBookDetail(int bNo) {
        log.info("[BookService] getBookDetail()");

        BookDto dto =  iBookDaoMapper.getBookDetail(bNo);

        return dto;

    }
}
