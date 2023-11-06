package com.office.libooksserver.admin.service.implement;

import com.office.libooksserver.login.service.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminDaoMapper {

    List<UserDto> selectUsersByFiltering(String keyword);

    List<UserDto> selectAdminsByFiltering(String keyword);

    int updateUserState(int no);
}
