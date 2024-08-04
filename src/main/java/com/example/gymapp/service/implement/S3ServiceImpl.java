package com.example.gymapp.service.implement;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.gymapp.exception.exception.BadRequestException;
import com.example.gymapp.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 s3Client;

    @Override
    public String uploadFile(MultipartFile file){
        File fileObj = convertToFile(file);
        String fileName = file.getName() + "_" + System.currentTimeMillis();
        s3Client.putObject(new PutObjectRequest(bucket, fileName, fileObj));
        fileObj.delete();
        return fileName;
    }

    @Override
    public void removeFile(String fileName){
        s3Client.deleteObject(bucket, fileName);
    }

    private File convertToFile(MultipartFile multipartFile){
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(multipartFile.getBytes());
        } catch (Exception e){
            throw new BadRequestException("");
        }
        return convertedFile;
    }
}
