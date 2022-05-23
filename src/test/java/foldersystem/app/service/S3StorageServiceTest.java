package foldersystem.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
public class S3StorageServiceTest {
    @Autowired
    private S3StorageService service;

    @Test
    public void uploadFileToS3Test() throws Exception {
        String content = IOUtils.resourceToString("/test-content.txt", StandardCharsets.UTF_8);
        service.uploadFileToS3("fileuser-bucket", "test-file-sdk.txt", content.getBytes());
    }

    @Test
    public void downloadFileFromS3Test() {
        String content = new String(service.downloadFileFromS3("fileuser-bucket", "test-file-sdk.txt"));
        assertThat(content.length(), is(not(equalTo(0))));
    }
}
