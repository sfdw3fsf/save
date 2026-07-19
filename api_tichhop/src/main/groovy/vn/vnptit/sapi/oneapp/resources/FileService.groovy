package vn.vnptit.sapi.oneapp.service

import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.models.ekyc.DownFileModel

import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ThreadLocalRandom

@Component
class FileService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    byte[] downloadFile(String fileUrl) throws Exception {
        URL url = new URL(fileUrl)
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            httpConn.disconnect();

            return byteArrayOutputStream.toByteArray();
        } else {
            httpConn.disconnect();
            throw new IOException("Failed to download file: " + responseCode);
        }
    }

    DownFileModel downloadFile_v2(String fileUrl) throws Exception {
        URL url = new URL(fileUrl)
        String file_name = ""
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            String contentDisposition = httpConn.getHeaderField("Content-Disposition");
            if (contentDisposition != null && contentDisposition.contains("filename=")) {
                // Tìm và tách tên file từ Content-Disposition
                file_name = contentDisposition.substring(contentDisposition.indexOf("filename=") + 9).replace("\"", "");
            } else {
                // Nếu không có Content-Disposition, bạn có thể lấy tên từ URL
                file_name = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            httpConn.disconnect();
            DownFileModel dw = new DownFileModel();
            dw.file_name = file_name
            dw.file_content = byteArrayOutputStream.toByteArray();
            return dw
        } else {
            httpConn.disconnect();
            throw new IOException("Failed to download file: " + responseCode);
        }
    }

    String encodeToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    File createFileFromBytes(byte[] fileBytes, String fileName) {
        Path path = Paths.get(fileName)
        FileOutputStream fos = null
        try {
            fos = new FileOutputStream(path.toFile());
            fos.write(fileBytes);
            fos.close()
            return path.toFile();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
