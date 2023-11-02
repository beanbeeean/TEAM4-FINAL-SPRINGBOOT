package com.office.libooksserver.common.s3;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3ServiceImpl implements S3Service{
    private final S3Uploader s3Uploader;

    public S3ServiceImpl(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @Override
    @Transactional
    public String createTeam(String name, MultipartFile file) {

        String url = "";

        if(file != null)  url = s3Uploader.uploadFileToS3(file, "static/team-image");

        System.out.println("url: " + url);

        return url;
    }
}