package com.office.libooksserver.login.service.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto findByid(Long id);

    UserDto findByEmail(String email);

    void save(UserDto user);

    void update(UserDto user);

    void delete(Long id);

    Boolean existsByEmail(String email);
}
