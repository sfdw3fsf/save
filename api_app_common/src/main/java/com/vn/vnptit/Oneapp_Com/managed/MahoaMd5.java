/**
 * 
 */
package com.vn.vnptit.Oneapp_Com.managed;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author DanhNQ
 *
 */
public class MahoaMd5 {
	

public String encrypt(String sourceString, String key1) {

	try {
		final byte[] message = sourceString
				.getBytes(StandardCharsets.US_ASCII);
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md
				.digest(key1.getBytes(StandardCharsets.US_ASCII));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;)
			keyBytes[k++] = keyBytes[j++];
		final SecretKey key = new SecretKeySpec(keyBytes, 0, 24,
				"DESede");
		final Cipher decipher = Cipher
				.getInstance("DESede/ECB/PKCS5Padding");
		decipher.init(Cipher.ENCRYPT_MODE, key);
		final byte[] plainText = decipher.doFinal(message);
		byte[] base64Bytes = org.apache.commons.codec.binary.Base64
				.encodeBase64(plainText);
		String base64EncryptedString = new String(base64Bytes);
		return base64EncryptedString;
	} catch (Exception e) {
		return null;
	}

}

}
