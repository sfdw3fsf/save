package vn.vnpt.vnptit.ecms.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
	@Value("${mahoa.key}")
	private String keymahoa;
	
    public EncryptionService() {
    	//throw new UnsupportedOperationException();
    }
	
    public String decrypt(String content) {
        try {
            final byte[] message = DatatypeConverter.parseBase64Binary(content);
            final MessageDigest md = MessageDigest.getInstance("md5");
            final byte[] digestOfPassword = md.digest(keymahoa.getBytes(StandardCharsets.US_ASCII));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            for (int j = 0, k = 16; j < 8;)
                keyBytes[k++] = keyBytes[j++];
            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final Cipher decipher = Cipher.getInstance("AES/GCM/NoPadding");
            decipher.init(Cipher.DECRYPT_MODE, key);
            final byte[] plainText = decipher.doFinal(message);
            return new String(plainText, StandardCharsets.US_ASCII);
        }
        catch(Exception e) {
            return null;
        }
    }

    public String encrypt(String content) {
        try {
            final byte[] message = content.getBytes(StandardCharsets.US_ASCII);
            final MessageDigest md = MessageDigest.getInstance("md5");
            final byte[] digestOfPassword = md.digest(keymahoa.getBytes(StandardCharsets.US_ASCII));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            for (int j = 0, k = 16; j < 8;)
                keyBytes[k++] = keyBytes[j++];
            final SecretKey key = new SecretKeySpec(keyBytes, 0, 24, "DESede");
            final Cipher decipher = Cipher.getInstance("AES/GCM/NoPadding");
            decipher.init(Cipher.ENCRYPT_MODE, key);
            final byte[] plainText = decipher.doFinal(message);
            byte[] base64Bytes = Base64.encodeBase64(plainText);
            String base64EncryptedString = new String(base64Bytes);
            return base64EncryptedString;
        }
        catch(Exception e) {
            return null;
        }
    }


}
