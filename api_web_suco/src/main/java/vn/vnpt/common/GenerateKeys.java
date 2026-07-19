package vn.vnpt.common;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class GenerateKeys {
	private String privateKey;

	private String publicKey;

	private final String RSA = "RSA";

	private final int KEY_LENGHT = 512;

	public GenerateKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(KEY_LENGHT);
			KeyPair kp = kpg.generateKeyPair();
			PrivateKey prk = kp.getPrivate();
			PublicKey puk = kp.getPublic();
			Base64.Encoder enc = Base64.getEncoder();
			this.privateKey = enc.encodeToString(prk.getEncoded());
			this.publicKey = enc.encodeToString(puk.getEncoded());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

}
