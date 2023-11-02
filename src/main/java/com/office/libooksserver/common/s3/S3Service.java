package com.office.libooksserver.common.s3;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    public String createTeam(String name, MultipartFile file);

}
