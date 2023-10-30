package com.office.libooksserver.user.service;

import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.dto.CommunityDto;
import com.office.libooksserver.user.service.implement.ICommunityDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CommunityService {

    @Autowired
    ICommunityDaoMapper iCommunityDaoMapper;

    public Map<String, Object> getCommunity() {
        log.info("[CommunityService] getCommunity()");

        Map<String, Object> map = new HashMap<>();
        List<CommunityDto> communityDtos = iCommunityDaoMapper.selectCommunity();
        log.info("communityDtos" + communityDtos.size());

        map.put("communityDtos", communityDtos);

        return map;
    }

    public int writeCommunity(int category, String title, String content) {
        log.info("[CommunityService] writeCommunity()");

        int result = iCommunityDaoMapper.insertNewCommunity(category, title, content);
        log.info("result : " + result);

        return result;
    }

    public CommunityDto getCommunityDetail(int cNo) {
        log.info("[CommunityService] getCommunityDetail()");

        CommunityDto dto =  iCommunityDaoMapper.getCommunityDetail(cNo);

        return dto;
    }
}
