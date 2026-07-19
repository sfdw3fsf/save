package vn.vnptit.sapi.oneapp.service;

import io.minio.errors.*;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.vnptit.sapi.oneapp.managed.LoggingManaged;
import vn.vnptit.sapi.oneapp.managed.ObjectStorageManaged;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class FileService {
    @Autowired
    ObjectStorageManaged objectStorageManaged;

    @Autowired
    LoggingManaged loggingManaged;

    private static final String[] listPathFileNotAllow = { "https:", "http:", "ftp:", "ftps:", "sftp:" };

    public static boolean comparePathFile(String param) {
        return Arrays.stream(listPathFileNotAllow).anyMatch(param.toLowerCase()::contains);
    }

//    public String getFile(String path) {
//        if (!TextUtils.isEmpty(path)) {
//            if (!comparePathFile(path)) {
//                try {
//                    if (objectStorageManaged.isObjectExist(path)) {
//
//                        String url = objectStorageManaged.getUrl(path);
//                        if (url != null && !url.isEmpty()) {
//                            return url;
//                        }
//                    }
//                } catch (Exception ex) {
//                    loggingManaged.error("[FileService] - [getFile] - Error:", ex);
//                }
//            }
//        }
//        return null;
//    }
}
