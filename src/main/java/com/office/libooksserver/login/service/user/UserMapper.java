package com.office.libooksserver.login.service.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto findByid(Long u_no);

    UserDto findByEmail(String u_email);

    void save(UserDto user);

    void update(UserDto user);

    void delete(Long u_no);

    Boolean existsByEmail(String u_email);
}
