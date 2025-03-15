package com.office.libooksserver.admin.service.implement;

import com.office.libooksserver.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminDaoMapper {

    int updateUserState(int no);

    List<UserDto> selectMembersByFiltering(String keyword);
}
