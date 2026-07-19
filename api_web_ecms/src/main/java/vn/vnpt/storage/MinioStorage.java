package vn.vnpt.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import io.minio.*;
import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import vn.vnpt.context.AppRequestContext;

/**
 * Created by User on 12/09/2021.
 * 
 * @author LOCNV
 */
@Service
@Configuration
public class MinioStorage {

	@Value("${object-storage.enabled}")
	boolean enabled;
	@Value("${object-storage.url}")
	String url;
	@Value("${object-storage.access-key}")
	String accessKey;
	@Value("${object-storage.secret-key}")
	String secretKey;
	@Value("${object-storage.bucket}")
	String bucketName;
	@Value("${object-storage.region}")
	String region;
	@Value("${object-storage.folder}")
	String folder;
	@Value("${object-storage.presigned-url-expiry-seconds}")
	int PresignedUrlExpirySeconds;

	private static final String BUCKET_PREFIX = "onebssproduct";
	private static final String BUCKET_OLD_NAME = "onebss-develop";

	private final String[] mimeTypes = new String[] { "image/jpeg", "image/bmp", "text/csv", "application/msword",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/gzip", "image/gif",
			"application/json", "audio/mpeg", "video/mp4", "video/mpeg", "image/png", "application/pdf",
			"application/vnd.ms-powerpoint",
			"application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/rtf",
			"image/svg+xml", "application/x-shockwave-flash", "text/plain", "application/vnd.visio", "audio/wav",
			"application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			"application/x-tika-ooxml", "application/vnd.ms-spreadsheetml",
			"application/xml", "text/xml", "application/atom+xml", "application/zip", "video/3gpp", "audio/3gpp" };

	private MinioClient _minioClient = null;

	private String getFolderPath(String fileName) {
		String path = this.bucketName + "/" + this.folder;
		if (!path.endsWith("/"))
			path += "/";
		path += AppRequestContext.getCurrent().getPhanVungID() + "/";
		java.text.DateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		path += formatter.format(new java.util.Date()) + "/";
		path += java.util.UUID.randomUUID() + "/";
		return path + fileName;
	}

	private MinioClient getMinoClient() {
		if (enabled) {
			if (_minioClient == null)
				_minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey)
						.region(region)
						.build();
			// return MinioClient.builder().endpoint(url).credentials(accessKey,
			// secretKey).region(region).build();
		}
		return _minioClient;
	}

	public StorageMessage uploadObject(String fileName, byte[] file) {
		StorageMessage result = new StorageMessage();
		MinioClient minioClient = getMinoClient();

		if (minioClient != null) {
			result = this.uploadObject(fileName, file, minioClient);
		} else {
			result.setMessage("Không xác thực được thông tin máy chủ");
		}
		return result;
	}

	public StorageMessage uploadObject(String fileName, byte[] file, MinioClient minioClient) {
		StorageMessage result = new StorageMessage();
		Tika tika = new Tika();
		String mimeType = tika.detect(file, fileName);
		if (Arrays.asList(this.mimeTypes).contains(mimeType.toLowerCase())) {
			ObjectWriteResponse objectWriteResponse;
			try {
				objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
						.bucket(bucketName)
						.stream(new ByteArrayInputStream(file), file.length, -1).object(this.getFolderPath(fileName))
						.contentType(mimeType).build());
				Map<String, String> urls = new HashMap<String, String>();
				String relativeUrl = objectWriteResponse.object();
				urls.put("relativeUrl", relativeUrl);
				urls.put("fileName", fileName);
				urls.put("presignedUrl", this.getPresignedUrl(relativeUrl, minioClient));
				result.setMessage(urls);
				result.setStatus(true);
			} catch (Exception e) {
				result.setMessage(e.getMessage());
			}
		} else {
			result.setMessage("Định dạng của tập tin chưa được hỗ trợ");
			result.setStatus(false);
		}
		return result;
	}

	public StorageMessage deleteObject(String fileName) {
		StorageMessage result = new StorageMessage();
		MinioClient minioClient = getMinoClient();
		if (minioClient != null) {
			result = this.deleteObject(fileName, minioClient);
		}
		return result;
	}

	public StorageMessage deleteObject(String fileName, MinioClient minioClient) {
		StorageMessage result = new StorageMessage();
		if (checkExists(fileName, minioClient)) {
			try {
				minioClient.removeObject(RemoveObjectArgs.builder()
						.bucket(this.getBucketNameByPath(fileName)).object(fileName).build());
				result.setStatus(true);
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
					| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
					| IllegalArgumentException | IOException e) {
				result.setMessage(e.getMessage());
			}
		} else
			result.setMessage("Đường dẫn không tồn tại trên hệ thống");
		return result;
	}

	public boolean checkExists(String fileName) {
		MinioClient minioClient = getMinoClient();
		if (minioClient != null) {
			StatObjectResponse objectStat = this.getInformation(fileName, minioClient);
			return objectStat != null;
		}
		return false;
	}

	public boolean checkExists(String fileName, MinioClient minioClient) {
		StatObjectResponse objectStat = this.getInformation(fileName, minioClient);
		return objectStat != null;
	}

	public StatObjectResponse getInformation(String fileName) {
		MinioClient minioClient = getMinoClient();
		if (minioClient != null) {
			return this.getInformation(fileName, minioClient);
		}
		return null;
	}

	public StatObjectResponse getInformation(String fileName, MinioClient minioClient) {
		try {
			return minioClient.statObject(StatObjectArgs.builder()
					.bucket(this.getBucketNameByPath(fileName)).object(fileName).build());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
		}
		return null;
	}

	public byte[] getObject(String fileName) {
		MinioClient minioClient = getMinoClient();
		if (minioClient != null) {
			return this.getObject(fileName, minioClient);
		}
		return null;
	}

	public byte[] getObject(String fileName, MinioClient minioClient) {
		try {
			InputStream inputStream = minioClient
					.getObject(GetObjectArgs.builder()
							.bucket(this.getBucketNameByPath(fileName)).object(fileName).build());
			byte[] bytes = IOUtils.toByteArray(inputStream);
			inputStream.close();
			return bytes;
		} catch (Exception e) {
		}
		return null;
	}

	public String getPresignedUrl(String fileName) {
		MinioClient minioClient = getMinoClient();
		if (minioClient != null) {
			return this.getPresignedUrl(fileName, minioClient);
		}
		return null;
	}

	public String getPresignedUrl(String fileName, MinioClient minioClient) {
		try {
			return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
					.bucket(this.getBucketNameByPath(fileName))
					.object(fileName).expiry(PresignedUrlExpirySeconds, TimeUnit.SECONDS).build());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException
				| IllegalArgumentException | IOException e) {
		}
		return null;
	}

	private String getBucketNameByPath(String path) {
		if (path.startsWith(BUCKET_PREFIX))
			return path.split("/")[0];
		return BUCKET_OLD_NAME;
	}
}
