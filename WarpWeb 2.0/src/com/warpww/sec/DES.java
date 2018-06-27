package com.warpww.sec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DES {

	
	public static void encrypt(String key, String inputString) throws Throwable {

		// Create DESKey from key
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		
		// Select DES/ECB/PKCS5Padding as the cipher for encryption
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		
		byte[] text = inputString.getBytes();

		// Encrypt the text
		desCipher.init(Cipher.ENCRYPT_MODE, desKey);
		byte[] textEncrypted = desCipher.doFinal(text);
		
		String base64text = Base64.encodeBase64String(textEncrypted);
		
		String urlEncodedText = URLEncoder.encode(base64text, "utf-8");
		System.out.println("key: " + key);
		System.out.println("inputString: " + inputString);
		System.out.println("Bas64 Encoded Text: " + base64text);
		System.out.println("URL Encoded Text: " + urlEncodedText);
	}
	
	public static String ellEncrypt(String key, String inputString) throws Throwable {

		String returnValue = null;
				
		// Create DESKey from key
		DESKeySpec dks = new DESKeySpec(key.getBytes("utf-8"));
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		
		// Select DES/ECB/PKCS5Padding as the cipher for encryption
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		
		byte[] text = inputString.getBytes();

		// Encrypt the text
		desCipher.init(Cipher.ENCRYPT_MODE, desKey);
		byte[] textEncrypted = desCipher.doFinal(text);
		
		String base64text = Base64.encodeBase64String(textEncrypted);
		
		String urlEncodedText = URLEncoder.encode(base64text, "utf-8");
		
		
		returnValue = urlEncodedText;

		System.out.println("key: " + key);
		System.out.println("inputString: " + inputString);
		System.out.println("Bas64 Encoded Text: " + base64text);
		System.out.println("URL Encoded Text: " + urlEncodedText);
		
		return returnValue;
		
	}

}
