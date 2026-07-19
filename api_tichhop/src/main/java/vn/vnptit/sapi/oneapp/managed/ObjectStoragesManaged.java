package vn.vnptit.sapi.oneapp.managed;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import io.minio.messages.Tags;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.vnptit.sapi.oneapp.properties.FileStorageConfig;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 6/22/2018.
 */
public class ObjectStoragesManaged {
    private static Logger logger = LoggerFactory.getLogger(ObjectStoragesManaged.class);
    private Map<String, FileStorageConfig> objectStorages;

    public ObjectStoragesManaged(Map<String, FileStorageConfig> objectStorages) throws SQLException {
        this.objectStorages = objectStorages;
    }

    private MinioClient getMinoClient(String name) throws MalformedURLException {

        if (objectStorages.containsKey(name)) {
            FileStorageConfig conf = objectStorages.get(name);
            Map<String, String> extras = conf.getExtras();
            URL url = new URL(conf.getProtocol(), conf.getHost(), conf.getPort(), "");
            MinioClient.Builder builder = MinioClient.builder()
                    .endpoint(url)
                    .credentials(conf.getUsername(), conf.getPassword());

            if (extras != null && extras.containsKey("region")) {
                if (StringUtils.isNotBlank(extras.get("region"))) {
                    builder.region(extras.get("region"));
                }
            }

            return builder.build();
        } else {
            return null;
        }
    }

    private MinioClient getMinoClient() throws MalformedURLException {
        String firstName = (String) objectStorages.keySet().toArray()[0];
        return getMinoClient(firstName);
    }

    public boolean makeBucket(String serverName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient(serverName);
        if (minioClient != null) {
            FileStorageConfig conf = objectStorages.get(serverName);
            String bucketName = conf.getDefaultLocation();
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

    public boolean uploadObject(String serverName, File file, Map<String, String> tags) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient(serverName);
        if (minioClient != null) {
            FileStorageConfig conf = objectStorages.get(serverName);
            String bucketName = conf.getDefaultLocation();

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

    public boolean uploadObject(String serverName, String fileName, InputStream file, Map<String, String> tags) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient(serverName);
        if (minioClient != null) {
            FileStorageConfig conf = objectStorages.get(serverName);
            String bucketName = conf.getDefaultLocation();

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

    public boolean uploadObject(String serverName, String fileName, byte[] file, Map<String, String> tags) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient(serverName);
        if (minioClient != null) {
            FileStorageConfig conf = objectStorages.get(serverName);
            String bucketName = conf.getDefaultLocation();

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

    public boolean deleteObject(String serverName, String fileName) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient(serverName);
        if (minioClient != null) {
            FileStorageConfig conf = objectStorages.get(serverName);
            String bucketName = conf.getDefaultLocation();

            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());

            return true;
        } else {
            return false;
        }
    }

    public InputStream getObject(String serverName, String fileName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient(serverName);
        if (minioClient != null) {
            FileStorageConfig conf = objectStorages.get(serverName);
            String bucketName = conf.getDefaultLocation();

            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
        } else {
            return null;
        }
    }

    public Map<String, String> getObjectTags(String serverName, String fileName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient(serverName);
        if (minioClient != null) {
            FileStorageConfig conf = objectStorages.get(serverName);
            String bucketName = conf.getDefaultLocation();

            Tags tags = minioClient.getObjectTags(
                    GetObjectTagsArgs.builder().bucket(bucketName).object(fileName).build());
            return tags.get();
        } else {
            return null;
        }
    }

    public Iterable<Result<Item>> listObject(String serverName, String prefix, boolean recursive) throws MalformedURLException {
        MinioClient minioClient = getMinoClient(serverName);
        if (minioClient != null) {
            FileStorageConfig conf = objectStorages.get(serverName);
            String bucketName = conf.getDefaultLocation();

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

    public String getUrl(String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException,
            InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException,
            IllegalArgumentException, IOException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
                    .bucket("onebss-develop").object(fileName).expiry(4, TimeUnit.HOURS).build());
        } else {
            return null;
        }
    }

    public boolean uploadObjectHPG(String fileName, byte[] file) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = getMinoClient("onebss-develop");
        if (minioClient != null) {
            String bucketName = "ftphpg-store1daypicture";

            Tika tika = new Tika();
            String mimeType = tika.detect(file);

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .stream(new ByteArrayInputStream(file), file.length, -1)
                    .object(fileName)
                    .contentType(mimeType)
                    .build());

            return true;
        } else {
            return false;
        }
    }

    public String getUrlHPG(String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException,
            InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException,
            IllegalArgumentException, IOException {
        MinioClient minioClient = getMinoClient();
        if (minioClient != null) {
            if (fileName.endsWith(".xlsx")) {
                Map<String, String> reqParams = new HashMap<String, String>();
                reqParams.put("response-content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
                        .bucket("ftphpg-store1daypicture").object(fileName).extraQueryParams(reqParams).expiry(4, TimeUnit.HOURS).build());
            }
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
                    .bucket("ftphpg-store1daypicture").object(fileName).expiry(4, TimeUnit.HOURS).build());
        } else {
            return null;
        }
    }

    public InputStream getInputStream(String fileName) {
        InputStream result = null;
        try {
            MinioClient minioClient = getMinoClient();
            FileStorageConfig conf = objectStorages.get("onebss-develop");
            String bucketName = conf.getDefaultLocation();
            result = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
        } catch (Exception ex) {
        }
        return result;
    }

    boolean uploadObject(String fileName, byte[] file) throws Exception {
        try {
            MinioClient minioClient = getMinoClient();
            FileStorageConfig conf = objectStorages.get("onebss-develop");
            String bucketName = conf.getDefaultLocation();
            Tika tika = new Tika();
            String mimeType = tika.detect(file);
            ObjectWriteResponse found = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .stream(new ByteArrayInputStream(file), file.length, -1)
                    .object(fileName)
                    .contentType(mimeType)
                    .build());

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    boolean checkExists(String fileName) {
        try {
            MinioClient minioClient = getMinoClient();
            FileStorageConfig conf = objectStorages.get("onebss-develop");
            String bucketName = conf.getDefaultLocation();

            minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(fileName).build()
            );

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
