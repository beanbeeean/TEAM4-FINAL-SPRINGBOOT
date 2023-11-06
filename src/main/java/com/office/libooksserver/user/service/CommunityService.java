package com.office.libooksserver.user.service;

import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.dto.CommunityDto;
import com.office.libooksserver.user.dto.ReplyDto;
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

    public Map<String, Object> getCommunity(String keyword, int category,int searchOption) {
        log.info("[CommunityService] getCommunity()");
        Map<String, Object> map = new HashMap<>();
        List<CommunityDto> communityDtos = iCommunityDaoMapper.selectCommunity(keyword, category, searchOption);
        log.info("communityDtos" + communityDtos.size());

        map.put("communityDtos", communityDtos);

        return map;
    }

    public int writeCommunity(int category, String title, String content, String uMail) {
        log.info("[CommunityService] writeCommunity()");

        int result = iCommunityDaoMapper.insertNewCommunity(category, title, content, uMail);
        int latestCno = iCommunityDaoMapper.getCommunityCno(category,uMail);
        log.info("result : " + result);

        return latestCno;
    }

    public CommunityDto getCommunityDetail(int cNo) {
        log.info("[CommunityService] getCommunityDetail()");

        iCommunityDaoMapper.updateCommunityHit(cNo);
        CommunityDto dto =  iCommunityDaoMapper.getCommunityDetail(cNo);

        return dto;
    }

    public int modifyCommunity(int id, int category, String title, String content) {
        log.info("[CommunityService] modifyCommunity()");

        int result = iCommunityDaoMapper.updateCommunity(id, category, title, content);
        log.info("result : " + result);

        return result;
    }

    public int deleteCommunity(int id) {
        log.info("[CommunityService] deleteCommunity()");

        int result = iCommunityDaoMapper.deleteCommunity(id);
        log.info("result : " + result);

        return result;
    }

    public int writeComment(ReplyDto replyDto) {
        log.info("[CommunityService] writeComment()");
        return iCommunityDaoMapper.insertComment(replyDto);
    }

    public Map<String, Object> getComments(int cNo) {
        log.info("[CommunityService] writeComment()");
        Map<String, Object> map = new HashMap<>();
        List<ReplyDto> dtos =iCommunityDaoMapper.getComments(cNo);
        map.put("dtos", dtos);
        return map;
    }
}
