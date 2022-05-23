package foldersystem.app.service;

import foldersystem.app.repository.StorageObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3StorageService {
    @Autowired
    private StorageObjectRepository repository;

    private S3Client s3Client;

    public void uploadFileToS3(String bucket, String name, byte[] content) {
        Region region = Region.US_EAST_1;
        s3Client = S3Client.builder().region(region).build();
        PutObjectRequest objectRequest = PutObjectRequest
                .builder()
                .bucket(bucket)
                .key(name)
                .build();
        s3Client.putObject(objectRequest, RequestBody.fromBytes(content));
    }

    public byte[] downloadFileFromS3(String bucket, String name) {
        Region region = Region.US_EAST_1;
        s3Client = S3Client.builder().region(region).build();
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(name)
                .build();
        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
        return objectBytes.asByteArray();
    }
}
