package com.office.libooksserver.user.controller;

import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.dto.CommunityDto;
import com.office.libooksserver.user.dto.ReplyDto;
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
import java.util.*;

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
                                            @RequestParam(name = "category", defaultValue = "0") int category,
                                            @RequestParam(name = "searchOption", defaultValue = "1") int searchOption){
        log.info("[CommunityController] getCommunity()");

        log.info("keyword :: " + keyword);
        log.info("category :: " + category);
        log.info("searchOption :: " + searchOption);

        return communityService.getCommunity(keyword,category,searchOption);
    }

    @PostMapping("/write")
    @ResponseBody
    public Object writeCommunity(@RequestBody Map<String, String> map) {
        log.info("[CommunityController] writeCommunity()");
        log.info("category" + map.get("selection"));
        log.info("title" + map.get("title"));
        log.info("content" + map.get("content"));
//        int result = 0;


        int result = communityService.writeCommunity(Integer.parseInt(map.get("selection")), map.get("title"), map.get("content"), map.get("u_email"));

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

    @PostMapping("/community_modify/{id}")
    @ResponseBody
    public Object modifyCommunity(@PathVariable int id, @RequestBody Map<String, String> map) {
        log.info("[CommunityController] modifyCommunity()");
        log.info("title" + map.get("title"));
        log.info("content" + map.get("content"));

        int result = communityService.modifyCommunity(id, Integer.parseInt(map.get("selection")), map.get("title"), map.get("content"));

        return result;
    }

    @PostMapping("/delete{id}")
    public Object deleteCommunity(@PathVariable int id) {
        log.info("[CommunityController] deleteCommunity()");

        int result = communityService.deleteCommunity(id);

        return result;
    }

    @PostMapping("/write_comment")
    @ResponseBody
    public int writeComment(@RequestBody ReplyDto replyDto){
        log.info("[CommunityController] writeComment()");
        return communityService.writeComment(replyDto);
    }

    @PostMapping("/modify_comment")
    @ResponseBody
    public int modifyComment(@RequestBody ReplyDto replyDto){
        log.info("[CommunityController] modifyComment()");
        return communityService.modifyComment(replyDto);
    }

    @PostMapping("/delete_comment")
    @ResponseBody
    public int deleteComment(@RequestBody ReplyDto replyDto){
        log.info("[CommunityController] deleteComment()");
        return communityService.deleteComment(replyDto);
    }
    @GetMapping("/get_comments")
    @ResponseBody
    public Map<String,Object> getComments(@RequestParam int c_no){
        System.out.println("getComments " + c_no);
        Map<String, Object> map = communityService.getComments(c_no);
        return map;
    }

}
