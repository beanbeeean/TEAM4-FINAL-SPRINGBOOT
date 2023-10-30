package com.office.libooksserver.login.redis.service.redis;

import com.office.libooksserver.login.redis.controller.dto.RedisCrudResponseDto;
import com.office.libooksserver.login.redis.controller.dto.RedisCrudSaveRequestDto;
import com.office.libooksserver.login.redis.domain.redis.RedisCrud;
import com.office.libooksserver.login.redis.domain.redis.RedisCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RedisCrudService {

    private final RedisCrudRepository redisCrudRepository;

    @Transactional
    public Long save(RedisCrudSaveRequestDto requestDto) {
        return redisCrudRepository.save(requestDto.toRedisHash()).getId();
    }

    public RedisCrudResponseDto get(Long id) {
        RedisCrud redisCrud = redisCrudRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Nothing saved. id=" + id));
        return new RedisCrudResponseDto(redisCrud);
    }
}
