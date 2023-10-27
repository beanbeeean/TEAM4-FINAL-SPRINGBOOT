package com.office.libooksserver.login.service.token;

import com.office.libooksserver.login.domain.entity.user.Token;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {

    Token findByUserEmail(String email);

    Token findByRefreshToken(String refreshToken);

    int isToken(String email);

    void save(Token token);

    void update(Token token);

    Token delete(Token token);
}
