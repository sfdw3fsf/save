package vn.vnptit.sapi.oneapp.managed;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.vnptit.sapi.oneapp.properties.FileStorageConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 6/22/2018.
 */
public class FtpsManaged {
    private static Logger logger = LoggerFactory.getLogger(FtpsManaged.class);
    private Map<String, FileStorageConfig> ftpStorages;

    public FtpsManaged(Map<String, FileStorageConfig> ftpStorages) throws SQLException {
        this.ftpStorages = ftpStorages;
    }

    public FTPClient getFtpClient(String name) throws IOException {
        if (ftpStorages.containsKey(name)) {
            FileStorageConfig conf = ftpStorages.get(name);
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(conf.getHost(), conf.getPort());

            Map<String, String> extras = conf.getExtras();

            if (extras.containsKey("file-type") && extras.containsKey("format-or-byte-size")) {
                int fileType = Integer.parseInt(extras.get("file-type"));
                int formatOrByteSize = Integer.parseInt(extras.get("format-or-byte-size"));
                ftpClient.setFileType(fileType, formatOrByteSize);
            } else if (extras.containsKey("file-type")) {
                int fileType = Integer.parseInt(extras.get("file-type"));
                ftpClient.setFileType(fileType);
            }
            if (extras.containsKey("file-transfer-mode")) {
                int fileTransferMode = Integer.parseInt(extras.get("file-transfer-mode"));
                ftpClient.setFileTransferMode(fileTransferMode);
            }
            if (extras.containsKey("passive-mode")) {
                String passiveMode = extras.get("passive-mode");
                if ("local-passive".equals(passiveMode)) {
                    ftpClient.enterLocalPassiveMode();
                } else if ("local-active".equals(passiveMode)) {
                    ftpClient.enterLocalActiveMode();
                } else if ("remote-passive".equals(passiveMode)) {
                    ftpClient.enterRemotePassiveMode();
                }
            }

            ftpClient.login(conf.getUsername(), conf.getPassword());

            if (extras.containsKey("pasv")) {
                ftpClient.pasv();
            }
            if (extras.containsKey("epsv")) {
                ftpClient.epsv();
            }

            return ftpClient;
        } else {
            return null;
        }
    }

    public FTPClient getFtpClient() throws IOException {
        String firstName = (String) ftpStorages.keySet().toArray()[0];
        return getFtpClient(firstName);
    }

    public boolean uploadObject(FTPClient ftpClient, String folderName, String fileName, InputStream inputStream, Map<String, String> tags) throws IOException {
        changeWorkingDirectory(ftpClient, folderName);
        return ftpClient.storeFile(fileName, inputStream);
    }

    public void changeWorkingDirectory(FTPClient ftpClient, String folderName) throws IOException {
        ftpClient.changeWorkingDirectory("/");
        boolean dirExists = true;
        String[] directories = folderName.split("/");
        for (String dir : directories) {
            if (!dir.isEmpty()) {
                if (dirExists) {
                    dirExists = ftpClient.changeWorkingDirectory(dir);
                }
                if (!dirExists) {
                    if (!ftpClient.makeDirectory(dir)) {
                        throw new IOException("Unable to create remote directory '" + dir + "'.  error='" + ftpClient.getReplyString() + "'");
                    }
                    if (!ftpClient.changeWorkingDirectory(dir)) {
                        throw new IOException("Unable to change into newly created remote directory '" + dir + "'.  error='" + ftpClient.getReplyString() + "'");
                    }
                }
            }
        }
    }

    public Map<String, Object> upload(String host, int port, String username, String password,
                         String mainFolder, String fileName, InputStream data) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "0");
        result.put("error", "Có lỗi xảy ra trong quá trình upload file " + fileName);

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            String folder = mainFolder + "/" + new SimpleDateFormat("MMyyyy").format(new Date());
            boolean dirExists = ftpClient.changeWorkingDirectory(folder);
            if (!dirExists) {
                ftpClient.makeDirectory(folder);
                ftpClient.changeWorkingDirectory(folder);
            }

            boolean done = ftpClient.storeFile(fileName, data);
            if (done) {
                result.put("status", "1");
                result.put("error", "");
                String filePath = folder + "/" + fileName;
                result.put("path", filePath.substring(mainFolder.length()+1));
            }
        } catch (Exception e) {
            result.put("error", e.getMessage());
        } finally {
            try {
                ftpClient.logout();
            } catch (Exception e1) {}
            try {
                ftpClient.disconnect();
            } catch (Exception e2) {}
        }

        return result;
    }

    public Map<String, Object> uploadCSGT(String host, int port, String username, String password,
                                      String folder, String fileName, InputStream data) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "0");
        result.put("error", "Có lỗi xảy ra trong quá trình upload file " + fileName);

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(folder);
            boolean done = ftpClient.storeFile(fileName, data);
            if (done) {
                result.put("status", "1");
            }
        } catch (Exception e) {
            result.put("error", e.getMessage());
        } finally {
            try {
                ftpClient.logout();
            } catch (Exception e1) {}
            try {
                ftpClient.disconnect();
            } catch (Exception e2) {}
        }

        return result;
    }

    byte[] getFile(String host, int port, String username, String password,
                                String mainFolder, String path) {
        byte[] fileBytes = null;
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            String filePath = path.substring(0, path.lastIndexOf("/"));
            String folder = mainFolder + "/" + filePath;
            boolean dirExists = ftpClient.changeWorkingDirectory(folder);
            if (!dirExists) {
                return null;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
            boolean fileExists = ftpClient.retrieveFile(fileName, baos);
            if (!fileExists) {
                return null;
            } else {
                fileBytes = baos.toByteArray();
            }

            return fileBytes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                ftpClient.logout();
            } catch (Exception e1) {}
            try {
                ftpClient.disconnect();
            } catch (Exception e2) {}
        }
    }
}
