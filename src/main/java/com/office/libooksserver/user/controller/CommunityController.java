package com.office.libooksserver.user.controller;

import com.office.libooksserver.user.service.CommunityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;

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
}
