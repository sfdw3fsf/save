package vn.vnptit.sapi.oneapp.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class BSSCrypto {

    private static final byte[] FIXED_IV = {
            (byte)0x01, (byte)0x0A, (byte)0x07, (byte)0x0F,
            (byte)0x05, (byte)0x02, (byte)0x03, (byte)0x09,
            (byte)0x10, (byte)0x08, (byte)0x0D, (byte)0x04
    };

    public static String encryptPasswordBased(String plainText, String keyString) throws Exception {
        SecretKey key = generateKeyFromPassword(keyString);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Use the fixed IV
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, FIXED_IV);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);

        byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));

        // Combine IV and cipherText
        byte[] encryptedData = new byte[FIXED_IV.length + cipherText.length];
        System.arraycopy(FIXED_IV, 0, encryptedData, 0, FIXED_IV.length);
        System.arraycopy(cipherText, 0, encryptedData, FIXED_IV.length, cipherText.length);

        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decryptPasswordBased(byte[] encryptedData, String keyString) throws Exception {
        SecretKey key = generateKeyFromPassword(keyString);

        // Extract IV from the encrypted data
        byte[] iv = new byte[12]; // IV is 12 bytes in GCM mode
        System.arraycopy(encryptedData, 0, iv, 0, iv.length);

        // Initialize cipher with IV
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));

        // Decrypt the rest of the data
        byte[] cipherText = new byte[encryptedData.length - 12];
        System.arraycopy(encryptedData, 12, cipherText, 0, cipherText.length);
        byte[] decryptedText = cipher.doFinal(cipherText);

        return new String(decryptedText, "UTF-8");
    }

    public static SecretKey generateKeyFromPassword(String password) throws Exception {
        byte[] keyData = password.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyData = sha.digest(keyData);
        return new SecretKeySpec(keyData, "AES");
    }

    public static String encrypt(String encryptString, String cryptoKey) {
        String cipherText = encryptString;
        try {
            cipherText = encryptPasswordBased(encryptString, cryptoKey);
        } catch (Exception ex) {
            System.out.println("Mã hóa lỗi");
        }
        return cipherText;
    }

    public static String decrypt(String cipherText, String cryptoKey) {
        String decryptedString = cipherText;
        byte[] decryptedByte = Base64.getDecoder().decode(cipherText);
        try {
            decryptedString = decryptPasswordBased(decryptedByte, cryptoKey);
        } catch (Exception ex) {
            System.out.println("Giải mã lỗi");
        }
        return decryptedString;
    }

}
