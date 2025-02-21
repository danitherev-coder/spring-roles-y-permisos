package com.danitherev.jjwt.services.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.danitherev.jjwt.services.CloudinaryService;

import jakarta.annotation.Resource;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadedFile = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap("folder", folderName));

            String urlSecure = (String) uploadedFile.get("secure_url");

            return cloudinary.url().secure(true).generate(urlSecure);

        } catch (IOException e) {
            return null;
        }        
    }
    
}
