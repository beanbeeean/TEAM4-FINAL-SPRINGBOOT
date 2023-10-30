package com.office.libooksserver.user.service.implement;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommunityDaoMapper {

    int insertNewCommunity(int category, String title, String content);
}
