package vn.vnptit.sapi.oneapp.util;

import java.io.FileWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

@SuppressWarnings("restriction")
public class RSADecryption {
	private static String ALGORITHM = "RSA";

	public static void main(String[] args) throws Exception {
		test();
	}

	private static void test() throws Exception {
		String plainText = "Hello, RSA!";

		// Mã hóa dữ liệu bằng khóa công khai
		PublicKey publicKey = loadPublicKeyBase64("oneapp.public.key");
		byte[] encryptedBytes = encrypt(publicKey.getEncoded(), plainText.getBytes());
		String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);

		// Giải mã dữ liệu bằng khóa riêng
		PrivateKey privateKey = loadPrivateKeyBase64_PCKS1("oneapp.private.key");
		byte[] decryptedBytes = decrypt(privateKey.getEncoded(), encryptedBytes);
		String decryptedString = new String(decryptedBytes);

		// In kết quả
		System.out.println("Dữ liệu gốc: " + plainText);
		System.out.println("Dữ liệu đã mã hóa: " + encryptedString);
		System.out.println("Dữ liệu đã giải mã: " + decryptedString);
	}

	public static void saveKeysToDiskBase64(String name, KeyPair keyPair) {
		try {

			Base64.Encoder encoder = Base64.getEncoder();

			String privateFileName = name + ".private.key";
			try (Writer out = new FileWriter(privateFileName)) {
				out.write(encoder.encodeToString(keyPair.getPrivate().getEncoded()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			String publicFileName = name + ".public.key";
			try (Writer out2 = new FileWriter(publicFileName)) {
				out2.write(encoder.encodeToString(keyPair.getPublic().getEncoded()));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	// https://www.devglan.com/online-tools/rsa-encryption-decryption
	public static PrivateKey loadPrivateKeyBase64_PCKS8(String keyFile) {
		PrivateKey pvt = null;
		try {
			/* Read all bytes from the private key file */
			Path path = Paths.get(keyFile);
			byte[] bytes = Files.readAllBytes(path);

			String s = new String(bytes).replace("\r\n", "");
			bytes = Base64.getDecoder().decode(s.getBytes());

			/* Generate private key. */
			PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
			KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
			pvt = kf.generatePrivate(ks);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return pvt;
	}

	// https://travistidwell.com/jsencrypt/demo/index.html
	public static PrivateKey loadPrivateKeyBase64_PCKS1(String keyFile) throws Exception {
		Path path = Paths.get(keyFile);
		byte[] bytes = Files.readAllBytes(path);
		String s = new String(bytes).replace("\r\n", "");
		bytes = Base64.getDecoder().decode(s.getBytes());

		DerInputStream derReader = new DerInputStream(bytes);
		DerValue[] seq = derReader.getSequence(0);
		BigInteger modulus = seq[1].getBigInteger();
		BigInteger publicExp = seq[2].getBigInteger();
		BigInteger privateExp = seq[3].getBigInteger();
		BigInteger prime1 = seq[4].getBigInteger();
		BigInteger prime2 = seq[5].getBigInteger();
		BigInteger exp1 = seq[6].getBigInteger();
		BigInteger exp2 = seq[7].getBigInteger();
		BigInteger crtCoef = seq[8].getBigInteger();

		RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static PublicKey loadPublicKeyBase64(String keyFile) {
		PublicKey pub = null;
		try {
			/* Read all the public key bytes */
			Path path = Paths.get(keyFile);
			byte[] bytes = Files.readAllBytes(path);

			String s = new String(bytes).replace("\r\n", "");
			bytes = Base64.getDecoder().decode(s.getBytes());

			/* Generate public key. */
			X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			pub = kf.generatePublic(ks);
		} catch (Exception x) {
			x.printStackTrace();
		}
		return pub;
	}

	public static byte[] encrypt(byte[] publicKey, byte[] inputData) throws Exception {
		PublicKey key = KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(publicKey));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedBytes = cipher.doFinal(inputData);
		return encryptedBytes;
	}

	public static byte[] decrypt(byte[] privateKey, byte[] inputData) throws Exception {
		PrivateKey key = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedBytes = cipher.doFinal(inputData);
		return decryptedBytes;
	}

	public static PrivateKey loadPrivateKeyBase64_PCKS1_FromKey(String key) throws Exception {
		byte[] bytes = Base64.getDecoder().decode(key.getBytes());

		DerInputStream derReader = new DerInputStream(bytes);
		DerValue[] seq = derReader.getSequence(0);
		BigInteger modulus = seq[1].getBigInteger();
		BigInteger publicExp = seq[2].getBigInteger();
		BigInteger privateExp = seq[3].getBigInteger();
		BigInteger prime1 = seq[4].getBigInteger();
		BigInteger prime2 = seq[5].getBigInteger();
		BigInteger exp1 = seq[6].getBigInteger();
		BigInteger exp2 = seq[7].getBigInteger();
		BigInteger crtCoef = seq[8].getBigInteger();

		RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static String decryptString(String privateKey, String inputData) throws Exception {
		try {
			PrivateKey privKey = loadPrivateKeyBase64_PCKS1_FromKey(privateKey);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(inputData));
			return new String(decryptedBytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

}
