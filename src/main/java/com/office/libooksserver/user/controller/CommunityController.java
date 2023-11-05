package com.office.libooksserver.user.controller;

import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.dto.CommunityDto;
import com.office.libooksserver.user.service.CommunityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@RestController
@Log4j2
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    String UPLOAD_PATH = "C:\\finalpjt\\myUpload"; // 업로드 할 위치

    @GetMapping({"", "/"})
    @ResponseBody
    public Map<String, Object> getCommunity(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                            @RequestParam(name = "category", defaultValue = "1") int category,
                                            @RequestParam(name = "searchOption", defaultValue = "1") int searchOption){
        log.info("[CommunityController] getCommunity()");

        return communityService.getCommunity(keyword,category,searchOption);
    }

    @GetMapping("/write")
    @ResponseBody
    public Object writeCommunity(@RequestParam(name = "selection", required = false) int category,
                                 @RequestParam(name = "title", required = false) String title,
                                 @RequestParam(name = "content", required = false) String content,
                                 @RequestParam(name = "u_email", required = false) String u_email
    ) {
        log.info("[CommunityController] writeCommunity()");
        log.info("category" + category);
        log.info("title" + title);
        log.info("content" + content);

        int result = communityService.writeCommunity(category, title, content, u_email);

        return result;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CommunityDto getCommunityByNo(@PathVariable String id) {
        log.info("[CommunityController] getCommunityByNo()");
        log.info("id: "+id);

        CommunityDto communityDto = communityService.getCommunityDetail(Integer.parseInt(id));

        log.info(communityDto);
        return communityDto;
    }

    @GetMapping("/community_modify/{id}")
    @ResponseBody
    public Object modifyCommunity(@PathVariable int id,
                                  @RequestParam(name = "selection", required = false) int category,
                                 @RequestParam(name = "title", required = false) String title,
                                 @RequestParam(name = "content", required = false) String content
    ) {
        log.info("[CommunityController] modifyCommunity()");
        log.info("category" + category);
        log.info("title" + title);
        log.info("content" + content);

        int result = communityService.modifyCommunity(id, category, title, content);

        return result;
    }

    @PostMapping("/delete{id}")
    public Object deleteCommunity(@PathVariable int id) {
        log.info("[CommunityController] deleteCommunity()");

        int result = communityService.deleteCommunity(id);

        return result;
    }

    // 이미지 불러오기
    @GetMapping("/getImage/{fileId}/{fileType}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable String fileId, @PathVariable String fileType) {

        try {
            FileInputStream fis = new FileInputStream(UPLOAD_PATH + "\\" + fileId + "." + fileType);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte buffer[] = new byte[1024];
            int length = 0;

            while((length = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }

            return new ResponseEntity<byte[]>(baos.toByteArray(), HttpStatus.OK);

        } catch(IOException e) {
            return new ResponseEntity<byte[]>(new byte[] {}, HttpStatus.CONFLICT);
        }
    }

    // 이미지 업로드
    @PostMapping("/uploadImage")
    public ResponseEntity<Object> uploadImage(MultipartFile multipartFiles[]) {
        try {
            MultipartFile file = multipartFiles[0];

            String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
            String originName = file.getOriginalFilename(); // ex) 파일.jpg
            String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
            originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
            long fileSize = file.getSize(); // 파일 사이즈

            File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
            if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
                fileSave.mkdirs();
            }

            file.transferTo(fileSave); // fileSave의 형태로 파일 저장

            System.out.println("fileId= " + fileId);
            System.out.println("originName= " + originName);
            System.out.println("fileExtension= " + fileExtension);
            System.out.println("fileSize= " + fileSize);

            return new ResponseEntity<Object>("http://localhost:3000/getImage/" + fileId + "/" + fileExtension, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }
    }
}
