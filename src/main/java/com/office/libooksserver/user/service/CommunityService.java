package com.office.libooksserver.user.service;

import com.office.libooksserver.user.dto.CommunityDto;
import com.office.libooksserver.user.service.implement.ICommunityDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class CommunityService {

    @Autowired
    ICommunityDaoMapper iCommunityDaoMapper;

    public int writeCommunity(int category, String title, String content) {
        log.info("[CommunityService] writeCommunity()");

        int result = iCommunityDaoMapper.insertNewCommunity(category, title, content);
        log.info("result : " + result);

        return result;
    }
}
