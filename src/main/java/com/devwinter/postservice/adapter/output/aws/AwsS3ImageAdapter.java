package com.devwinter.postservice.adapter.output.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devwinter.postservice.application.port.output.RemoveImagePort;
import com.devwinter.postservice.application.port.output.UploadImagePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3ImageAdapter implements UploadImagePort, RemoveImagePort {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.post-image-prefix}")
    private String postImagePrefix;
    private final AmazonS3Client amazonS3Client;

    @Override
    public String upload(String target, MultipartFile multipartFile) {
        try {
            ObjectMetadata metaData = createMetaData(multipartFile);
            return uploadToAwsS3Storage(target, multipartFile, metaData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMetadata createMetaData(MultipartFile multipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());
        objectMetadata.setContentType("image/" + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        return objectMetadata;
    }

    private String uploadToAwsS3Storage(String target, MultipartFile multipartFile, ObjectMetadata metaData) throws FileUploadException {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String path = postImagePrefix + target;
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, path, inputStream, metaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
            return path;
        } catch (IOException e) {
            throw new FileUploadException();
        }
    }

    @Override
    public boolean existImage(String filePath) {
        return amazonS3Client.doesObjectExist(bucket, filePath);
    }

    @Override
    public void remove(String filePath) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
    }
}
