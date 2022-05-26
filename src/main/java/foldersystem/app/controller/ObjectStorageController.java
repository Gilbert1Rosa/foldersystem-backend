package foldersystem.app.controller;

import foldersystem.app.repository.StorageObjectRepository;
import foldersystem.app.service.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class ObjectStorageController {
    @Value("${foldersystem.s3-bucket}")
    private String bucketName;

    @Autowired
    private StorageObjectRepository repository;

    @Autowired
    private S3StorageService s3Service;

    /**
     * Create an object (File or folder) in an specifc folder.
     *
     * @param params
     * @return
     */
    @PostMapping("/fileexplorer/object")
    public Map<String, Object> createObject(
            @RequestBody Map<String, Object> params
    ) {
        HashMap<String, Object> response = new HashMap<>();
        Integer type = (Integer) params.get("objectType");
        if (type == 0) { // Is a folder
            repository.addObject((Integer) params.get("parent"),
                    params.get("objectName").toString(),
                    type,
                    "");
        }
        response.put("success", true);
        return response;
    }

    /**
     * Gets the list of objects in a folder.
     *
     * @param params
     * @return
     */
    @GetMapping("/fileexplorer/object")
    public Map<String, Object> getObject(
            @RequestParam Map<String, Object> params
    ) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("children", repository.getChildren(Integer.valueOf(params.get("folderId").toString())));
        return response;
    }

    /**
     * Moves an object to another folder.
     *
     * @param params
     * @return
     */
    @PatchMapping("/fileexplorer/object")
    public Map<String, Object> moveObject(
            @RequestBody Map<String, Object> params
    ) {
        HashMap<String, Object> response = new HashMap<>();
        repository.moveObject((Integer) params.get("objectId"), (Integer) params.get("parentId"));
        response.put("success", true);
        return response;
    }

    @PostMapping("/fileexplorer/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("parent") Integer parent,
            @RequestParam("filename") String fileName
            ) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            String hashName = UUID.randomUUID().toString().replace("-", "");
            s3Service.uploadFileToS3(bucketName, hashName, multipartFile.getBytes());
            repository.addObject(parent, fileName, 1, hashName);
            response.put("success", true);
        } catch (IOException exception) {
            response.put("success", false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/fileexplorer/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("fileId") Integer id
    ) {
        String hashName = repository.getHashName(id);
        byte[] fileBytes = s3Service.downloadFileFromS3(bucketName, hashName);
        ByteArrayResource resource = new ByteArrayResource(fileBytes);
        return ResponseEntity.ok()
                .contentLength(fileBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
