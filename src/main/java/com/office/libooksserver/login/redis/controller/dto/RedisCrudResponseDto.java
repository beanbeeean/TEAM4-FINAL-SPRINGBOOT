package com.office.libooksserver.login.redis.controller.dto;

import com.office.libooksserver.login.redis.domain.redis.RedisCrud;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RedisCrudResponseDto {

    private Long id;
    private String description;
    private LocalDateTime updatedAt;

    public RedisCrudResponseDto(RedisCrud redisHash) {
        this.id = redisHash.getId();
        this.description = redisHash.getDescription();
        this.updatedAt = redisHash.getUpdatedAt();
    }
}
