package vn.vnpt.vnptit.qlsc.service;

import org.springframework.stereotype.Service;

import vn.vnpt.storage.MinioStorage;

@Service
public class StorageService {

	private final MinioStorage storage;

	public StorageService(MinioStorage storage) {
		this.storage = storage;
	}

	public Object uploadFile(String fileName, byte[] file) {
		return storage.uploadObject(fileName, file);
	}

	public byte[] getFile(String fileName) {
		return storage.getObject(fileName);
	}

	public Object deleteFile(String fileName) {
		return storage.deleteObject(fileName);
	}

	public boolean checkExistsFile(String fileName) {
		return storage.checkExists(fileName);
	}

	public String getPresignedUrl(String fileName) {
		return storage.getPresignedUrl(fileName);
	}

}
