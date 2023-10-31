package com.office.libooksserver.user.controller;

import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.dto.CommunityDto;
import com.office.libooksserver.user.service.CommunityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    @GetMapping({"", "/"})
    @ResponseBody
    public Map<String, Object> getCommunity(){
        log.info("[CommunityController] getCommunity()");

        return communityService.getCommunity();
    }

    @GetMapping("/write")
    @ResponseBody
    public Object writeCommunity(@RequestParam(name = "selection", required = false) int category,
                                 @RequestParam(name = "title", required = false) String title,
                                 @RequestParam(name = "content", required = false) String content
    ) {
        log.info("[CommunityController] writeCommunity()");
        log.info("category" + category);
        log.info("title" + title);
        log.info("content" + content);

        int result = communityService.writeCommunity(category, title, content);

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
}
