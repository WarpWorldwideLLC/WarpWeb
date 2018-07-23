package com.warpww.sec;
/** 
 * AES provides 256-bit AES Encryption. 
 */
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import org.apache.commons.codec.binary.Base64;

import com.warpww.util.Util;

public class AES {

	
	// User Supplied Data
	public String ClearDataText = null;
	
	// New Object
	public String passphrase = null;
	public String salt64 = null;
	public String IV64 = null;
	public String encryptedData64 = null;
	
	public String StatusMessage;
	public int StatusCode;

	
	/* Internal Encryption Algorithm and Configuration */
	private String defaultEncoding = "UTF-8";
	private int pwdIterations = 65536;
	private int keySize = 256;
	private byte[] ivBytes;
	private String keyAlgorithm = "AES";
	private String encryptAlgorithm = "AES/CBC/PKCS5Padding";
	private String secretKeyFactoryAlgorithm = "PBKDF2WithHmacSHA1";
	

	public AES(){
		getSalt64();

	}
	
	/** 
	 * Generate a unique salt value for encryption.
	 */
	private void getSalt64(){
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		this.salt64 = new Base64().encodeAsString(bytes);
	}
		
	/** 
	 * Returns the salt value as a UTF-8 encoded byte array.
	 * 
	 * @return
	 */
	private byte[] getSaltBytes() {
		byte[] returnValue = null;
		
		try {
			returnValue = new Base64().decode(this.salt64.getBytes(defaultEncoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	/** 
	 * The Encrypt method encrypts text data, BASE64 encodes the encoded byte array, 
	 * and returns the BASE64 encoded string.
	 * 
	 * A salt and IV are randomly generated for the encryption, and those values are 
	 * stored BASE64 encoded in salt64 and IV64 respectively.
	 * 
	 * The only input required for this function is ClearDataText. If is not supplied, the 
	 * method will throw an exception. 
	 * 
	 * @return
	 * @throws Exception
	 */
	/* Note that, because of the Initialization Vector, the same data with the same password and salt, will 
	 * produce a different encrypted string every time. 
	 */
	public String encyrpt(){
		
		// Variable Declaration
		SecretKeyFactory skf = null;
		PBEKeySpec spec = null;
		SecretKey secretKey = null;
		SecretKeySpec key = null;
		Cipher cipher =null;
		byte[] encryptedText = null;
		
		//generate salt portion of key
		byte[] saltBytes = getSaltBytes();
		
		// Set the SecretKeyFactory algorithm using the private class setting.
		try {
			skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			this.StatusMessage = "Secret Key Factory Algorithm does not exist: " + this.secretKeyFactoryAlgorithm;
			this.StatusCode = 100;
		}
		
		spec = new PBEKeySpec(this.passphrase.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
		try {
			secretKey = skf.generateSecret(spec);
		} catch (InvalidKeySpecException e) {
			this.StatusMessage = "Invalid Key Specification";
			this.StatusCode = 101;
		}
		
		key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);
		
		//AES initialization
		
		try {
			cipher = Cipher.getInstance(this.encryptAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			this.StatusMessage = "No Such Algorithm: " + this.encryptAlgorithm;
			this.StatusCode = 102;
		} catch (NoSuchPaddingException e) {
			this.StatusMessage = "No Such Padding" + this.encryptAlgorithm;
			this.StatusCode = 103;
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			this.StatusMessage = "Invalid Key";
			this.StatusCode = 104;
		}
		
		//generate IV
		try {
			this.ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
		} catch (InvalidParameterSpecException e) {
			this.StatusMessage = "Invalid IV Parameter Exception";
			this.StatusCode = 105;
		}
		this.IV64 = new Base64().encodeAsString(this.ivBytes); 
		
		try {
			encryptedText = cipher.doFinal(this.ClearDataText.getBytes("UTF-8"));
		} catch (IllegalBlockSizeException e) {
			this.StatusMessage = "Illegal Block Size Exception";
			this.StatusCode = 106;
		} catch (BadPaddingException e) {
			this.StatusMessage = "Bad Padding Exception";
			this.StatusCode = 107;
		} catch (UnsupportedEncodingException e) {
			this.StatusMessage = "Unsupported Encoding";
			this.StatusCode = 108;
		}
		
		this.encryptedData64 = new Base64().encodeAsString(encryptedText);
		return this.encryptedData64; 
	}
	
	
	/** 
	 * The decrypt method decrypts a BASE64-encoded byte array back into a clear text value.
	 * 
	 * decrypt takes as input the passphrase, salt64 and IV64 values that were used to encode the data initially.
	 * 
	 * @param encryptText
	 * @param passphrase
	 * @param userSalt
	 * @param IV
	 * @return
	 * @throws Exception
	 */
	public String decrypt() throws Exception {
		byte[] saltBytes = Base64.decodeBase64(this.salt64);
		byte[] encryptTextBytes = new Base64().decode(this.encryptedData64);
		
		SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
		PBEKeySpec spec = new PBEKeySpec(this.passphrase.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
		SecretKey secretKey = skf.generateSecret(spec);
		SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);
		
		//decrypt the message
		byte[] ivArray = new Base64().decode(IV64.getBytes());
		Cipher cipher = Cipher.getInstance(encryptAlgorithm);
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivArray));
		
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
		this.ClearDataText = text;
		return text;
	}
	
	
	
/*

	public String EncryptionKey_Base64 = null;
	public String EncryptionKey_Text = null;
	public byte[] EncryptionKey;
	
	public String EncryptedData_Base64 = null;
	public byte[] EncryptedData;
	
	private String TOKEN = "";
	private String salt;
	public String salt_Base64;
	
	public String IV_Base64;
 
	private String encyrpt2(String plainText) throws Exception{
		//generate salt portion of key
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
	
	private String decrypt2(String encryptText) throws Exception {
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
*/	
	
	
	
}
