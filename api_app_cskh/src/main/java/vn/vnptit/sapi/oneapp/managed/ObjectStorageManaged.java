package vn.vnptit.sapi.oneapp.managed;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import io.minio.messages.Tags;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static vn.vnptit.sapi.oneapp.service.FileService.comparePathFile;

/**
 * Created by User on 6/22/2018.
 */
public class ObjectStorageManaged {
    private static Logger logger = LoggerFactory.getLogger(ObjectStorageManaged.class);

    @Value("${object-storage.enabled}")
    boolean enabled;
    @Value("${object-storage.url}")
    String url;
    @Value("${object-storage.access-key}")
    String accessKey;
    @Value("${object-storage.secret-key}")
    String secretKey;
//    @Value("${object-storage.bucket}")
//    String bucketName;
//    @Value("${object-storage.region}")
//    String region;

    private MinioClient getMinoClient() {
        if (enabled) {
            return MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secretKey)
//                    .region(region)
                    .build();
        } else {
            return null;
        }
    }

    public boolean makeBucket(String bucketName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean uploadObject(String bucketName, File file, Map<String, String> tags) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            Tika tika = new Tika();
            String mimeType = tika.detect(file);

            ObjectWriteResponse found = minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getName())
                    .filename(file.getAbsolutePath())
                    .contentType(mimeType)
                    .tags(tags)
                    .build());

            return true;
        } else {
            return false;
        }
    }

    public boolean uploadObject(String bucketName, String fileName, InputStream file, Map<String, String> tags) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            Tika tika = new Tika();
            String mimeType = tika.detect(file);

            ObjectWriteResponse found = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .stream(file, -1, 10485760)
                    .object(fileName)
                    .contentType(mimeType)
                    .tags(tags)
                    .build());

            return true;
        } else {
            return false;
        }
    }

    public boolean uploadObject(String bucketName, String fileName, byte[] file, Map<String, String> tags) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            Tika tika = new Tika();
            String mimeType = tika.detect(file);

            ObjectWriteResponse found = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .stream(new ByteArrayInputStream(file), file.length, -1)
                    .object(fileName)
                    .contentType(mimeType)
                    .tags(tags)
                    .build());

            return true;
        } else {
            return false;
        }
    }

    public boolean deleteObject(String bucketName, String fileName) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {

            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());

            return true;
        } else {
            return false;
        }
    }

    public InputStream getObject(String bucketName, String fileName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
        } else {
            return null;
        }
    }

    public Map<String, String> getObjectTags(String bucketName, String fileName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            Tags tags = minioClient.getObjectTags(
                    GetObjectTagsArgs.builder().bucket(bucketName).object(fileName).build());
            return tags.get();
        } else {
            return null;
        }
    }

    public Iterable<Result<Item>> listObject(String bucketName, String prefix, boolean recursive) {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            return minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .recursive(recursive)
                            .maxKeys(100)
                            .build());
        } else {
            return null;
        }
    }

//    public boolean isObjectExist(String fileName) {
//        MinioClient minioClient = getMinoClient();
//        if (minioClient != null) {
//            try {
//                minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
//                return true;
//            } catch (ErrorResponseException e) {
//                logger.info(e.getMessage());
//                return false;
//            } catch (Exception e) {
//                logger.info(e.getMessage());
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }

//    public String getUrl(String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException,
//            InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException,
//            IllegalArgumentException, IOException {
//        MinioClient minioClient = getMinoClient();
//        if (minioClient != null) {
//            if (!comparePathFile(fileName)) {
//                return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
//                        .bucket(bucketName).object(fileName).expiry(4, TimeUnit.HOURS).build());
//            } else {
//                return "Đường dẫn không hợp lệ";
//            }
//        } else {
//            return null;
//        }
//    }
}
