package vn.vnptit.sapi.oneapp.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DUtils {

    public static String decryptAESECB(String secret, String strToDecrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.getMessage());
        }
        return null;
    }
    
    static String replaceSpecChar(String inputString, String tagName) {
        String result = inputString;
        try {
            String beginTag = "<" + tagName + ">";
            String endTag = "</" + tagName + ">";
            Pattern pattern = Pattern.compile(beginTag + "(.*?)" + endTag);
            Matcher matcher = pattern.matcher(inputString);
            if (matcher.find()) {
                String sHasTag = matcher.group(0);
                String before = inputString.substring(0, inputString.indexOf(sHasTag));
                String after = inputString.substring(inputString.indexOf(sHasTag) + sHasTag.length(), inputString.length());

                String sNoTag = matcher.group(1);
                String replacement = sNoTag.replace("<", "&lt;").replace(">", "&gt;");
                result = before + beginTag + replacement + endTag + after;
            }
        } catch (Exception ex) {

        }
        return result;
    }
}
