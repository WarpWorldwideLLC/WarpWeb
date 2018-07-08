package com.warpww.sec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

public class AES {

	
	// User Supplied Data
	public String ClearDataText = null;
	public byte[] ClearData;
	
	public String EncryptionKey_Base64 = null;
	public String EncryptionKey_Text = null;
	public byte[] EncryptionKey;
	
	public String EncryptedData_Base64 = null;
	public byte[] EncryptedData;
	
	public String TOKEN = "passwd";
	public String salt;
	public String salt_Base64;
	
	/* Internal Encryption Algorithm and Configuration */
	
	private int pwdIterations = 65536;
	private int keySize = 256;
	private byte[] ivBytes;
	private String keyAlgorithm = "AES";
	private String encryptAlgorithm = "AES/CBC/PKCS5Padding";
	private String secretKeyFactoryAlgorithm = "PBKDF2WithHmacSHA1";
	

	public AES(){
		this.salt = getSalt();
		try {
			this.salt_Base64 = new Base64().encodeAsString(this.salt.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getSalt(){
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		String text = new String(bytes);
		return text;
	}
	
	/**
	 * 
	 * @param plainText
	 * @return encrypted text
	 * @throws Exception
	 */
	public String encyrpt(String plainText) throws Exception{
		//generate key
		byte[] saltBytes = salt.getBytes("UTF-8");
		
		SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
		PBEKeySpec spec = new PBEKeySpec(TOKEN.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
		SecretKey secretKey = skf.generateSecret(spec);
		SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);
		
		//AES initialization
		Cipher cipher = Cipher.getInstance(encryptAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		//generate IV
		this.ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedText = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new Base64().encodeAsString(encryptedText);
	}
	
	
	
	
	/**
	 * 
	 * @param encryptText
	 * @return decrypted text
	 * @throws Exception
	 */
	public String decrypt(String encryptText) throws Exception {
		byte[] saltBytes = salt.getBytes("UTF-8");
		byte[] encryptTextBytes = new Base64().decode(encryptText);
		
		SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
		PBEKeySpec spec = new PBEKeySpec(TOKEN.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
		SecretKey secretKey = skf.generateSecret(spec);
		SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);
		
		//decrypt the message
		Cipher cipher = Cipher.getInstance(encryptAlgorithm);
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
		
		byte[] decyrptTextBytes = null;
		try {
			decyrptTextBytes = cipher.doFinal(encryptTextBytes);
		} catch (IllegalBlockSizeException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		String text = new String(decyrptTextBytes);
		return text;
	}
	
}
