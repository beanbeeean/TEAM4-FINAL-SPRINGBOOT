package com.office.libooksserver.admin.controller;

import com.office.libooksserver.admin.service.AdminBookService;
import com.office.libooksserver.admin.service.AdminCommunityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/admin/management")
public class AdminCommunityController {

    @Autowired
    AdminCommunityService adminCommunityService;

    @GetMapping("/communityManagement")
    @ResponseBody
    public Map<String, Object> home(@RequestParam(name = "category", required = false) String category,
                                    @RequestParam(name = "keyword", required = false) String keyword ) {

        log.info("[AdminCommunityController] home()");
        log.info("category : " + category);
        log.info("keyword : " + keyword);

        return adminCommunityService.showCommunities(category, keyword);
    }

    @GetMapping("/change_community_state")
    @ResponseBody
    public Object changeBookInfo(@RequestParam(name = "no", required = false) int no) {

        log.info("[AdminBookController] changeBookInfo()");
        log.info("no : " + no);

        return adminCommunityService.changeCommunityState(no);
    }

}

