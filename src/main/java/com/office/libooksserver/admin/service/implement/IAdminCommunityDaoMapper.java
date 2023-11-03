package com.office.libooksserver.admin.service.implement;

import com.office.libooksserver.user.dto.CommunityDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminCommunityDaoMapper {
    List<CommunityDto> selectCommunityByFiltering(String category, String keyword);

    int updateCommunityState(int no);
}
