package com.office.libooksserver.user.service.implement;

import com.office.libooksserver.user.dto.CommunityDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICommunityDaoMapper {

    List<CommunityDto> selectCommunity();
    int insertNewCommunity(int category, String title, String content, String uMail);

    void updateCommunityHit(int cNo);
    CommunityDto getCommunityDetail(int cNo);

    int updateCommunity(int id, int category, String title, String content);

    int deleteCommunity(int id);
}
