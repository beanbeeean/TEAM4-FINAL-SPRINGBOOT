package com.office.libooksserver.admin.service;

import com.office.libooksserver.admin.service.implement.IAdminBookDaoMapper;
import com.office.libooksserver.admin.service.implement.IAdminCommunityDaoMapper;
import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.dto.CommunityDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class AdminCommunityService {

    @Autowired
    IAdminCommunityDaoMapper iAdminCommunityDaoMapper;

    public Map<String, Object> showCommunities(String category, String keyword) {
        log.info("[AdminCommunityService] showCommunities()");
        log.info("category : " + category);
        log.info("keyword : " + keyword);

        Map<String, Object> map = new HashMap<>();
        List<CommunityDto> dtos = iAdminCommunityDaoMapper.selectCommunityByFiltering(category, keyword);
        log.info("dtos" + dtos.size());

        map.put("dtos",dtos);

        return map;
    }
}
