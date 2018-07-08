package com.warpww.web.servlet.sec;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AES;
import com.warpww.util.Util;

/**
 * Servlet implementation class configurewarpweb
 */
@WebServlet("/configurewarpweb")
public class configurewarpweb extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public configurewarpweb() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Clear Text:  : {"CommandName":"WarpSecConfigObject","CommandVersion":"1.0","WarpSecUri":"jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8","WarpSecUser":"root","WarpSecPassword":"62XYhC;erw;zZaCmZVzrFEwW","WarpSecEnvironment":"TEST_CN","WarpSecKey":"WarpSecKeyValue"}
		/*
		 * 
		 * Clear Text:  : {"CommandName":"WarpSecConfigObject","CommandVersion":"1.0","WarpSecUri":"jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8","WarpSecUser":"root","WarpSecPassword":"62XYhC;erw;zZaCmZVzrFEwW","WarpSecEnvironment":"TEST_CN","WarpSecKey":"WarpSecKeyValue"}

			Encrypted Text:  : JIVA2rU5sDjxfYebPLkS/gpUL5yHbjvBHE4SKLfpEMre0tCn2QpVNMHEic5qfyGmphmljqvXEgdCZcWKzgDiLjFxo7iF5kWCM9UvZ/s/LIgeUv/Efllf1l3PNJY2/htEFGdidl5bJAC3LpCtl19sZf2Oak7Q3jFE5WBOTXFrgGunot0868dLpD5eYGqwWaXkg0lcDEASiwljW2YM36L21Gee6kZ4VQiSsqALOBiHjWVfa86j6aNZXxBSz5DoBAPDVg2a2/CdcjQ2n6wsNsQlUCje+d54KhFTl6wIH5mRwyuxNJU8d4rulBntpPKZuom4/NFYDsX+hFz2IcyLd7pTAACVN+fKg0rA5Yo22ODYSdW+JcnfEXSAXhfkIqTh4moSPf7YwLBFRiZpHomleup8NQ==
			Salt (Base64):  : OO+/vSDvv70faUjvv70AY++/ve+/ve+/vWLvv71u77+9aO+/ve+/vQ==
			Token/Password:  : passwd
			Decrypted Text:  : {"CommandName":"WarpSecConfigObject","CommandVersion":"1.0","WarpSecUri":"jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8","WarpSecUser":"root","WarpSecPassword":"62XYhC;erw;zZaCmZVzrFEwW","WarpSecEnvironment":"TEST_CN","WarpSecKey":"WarpSecKeyValue"}


	Init Param Value:  : null
	Clear Text:  : {"CommandName":"WarpSecConfigObject","CommandVersion":"1.0","WarpSecUri":"jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8","WarpSecUser":"root","WarpSecPassword":"62XYhC;erw;zZaCmZVzrFEwW","WarpSecEnvironment":"TEST_CN","WarpSecKey":"WarpSecKeyValue"}
	Encrypted Text:  : 8/hh5Fkc2CyX6oW3wezU26c//pQ+UdKjwq+YvxJOaDAyDG8ZRD/iCv1lP8COWRxFZfw8xwt8LNpoLoeILJ+MPM4lD4w/rzmU9hrvWQSw6+NL5icdMssRNh4h1O0LkMejHZag45vQMrO8oyom6jjt5ir5nupO4R18ZmwVsWJ2+lKIR3KYTkB+lITwb2aSei0aNjqkWiSGPNDgkSlaKUcENnndIsRtjTt6XAQvvIUFwL1RNUpIqmv8y+vyVkH5gKMlaJRZbvxxnOzwQ8eQMkkfznlgqs0GXhXUaPJJXrrMZiECg9OawO0PeNJS61T54pS+iZ173rHByvdtKf2PVcP3akfzJpUb/fZaGKXqmhQBTdkH8L562fP73x6Ja/e+bC6bSCIeRJZ+EGYAZIxfeMedFQ==
	Salt (Base64):  : DjhM77+9c2E077+9HlkgCe+/ve+/vVcv77+977+9MO+/vQ==
	Token/Password:  : passwd
	Decrypted Text:  : {"CommandName":"WarpSecConfigObject","CommandVersion":"1.0","WarpSecUri":"jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8","WarpSecUser":"root","WarpSecPassword":"62XYhC;erw;zZaCmZVzrFEwW","WarpSecEnvironment":"TEST_CN","WarpSecKey":"WarpSecKeyValue"}

Init Param Value:  : 123456790
Clear Text:  : {"CommandName":"WarpSecConfigObject","CommandVersion":"1.0","WarpSecUri":"jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8","WarpSecUser":"root","WarpSecPassword":"62XYhC;erw;zZaCmZVzrFEwW","WarpSecEnvironment":"TEST_CN","WarpSecKey":"WarpSecKeyValue"}
Encrypted Text:  : 9qUcfl6g3Hp1XzNqfmwpPxxlXUmjgHT/IlHrYeqSE8+6ctIIAR6HT8sr4noMCtLEz4+1AWeBsFKqKiOVCAUmc/UssxMYWj0RPXc56jMreXQQOmQHO7EI6IkKn6wxeSNWDFsi7EU4/Qry6wpk+pQgg/ReSKAT+FC2PqDGInWXuE0IqpY9zMj/Ow1enyG47sQuosoDVS/MvwmnccK4kOqQE+6bsJQH7vMroFdfqx817Ii7k4QUbKkTqf+D0qoruwecmcckEPHGbKEiHK0nG2FOTk0IHCCkazODNBcirdLALegKkjuAEGIOoaVMY56I3z6OUkH3QmdoZtz1Nywlw0Pe0QW7RvosHegRfF7eRoitYMkgwM4sQY0fgTqS2NqsiHLgUncMmakpfaTzzDiBO7Vusg==
Salt (Base64):  : 77+9AkYBYwHvv73vv73vv73vv73vv71JGe+/ve+/ve+/ve+/vTnvv70=
Token/Password:  : passwd
Decrypted Text:  : {"CommandName":"WarpSecConfigObject","CommandVersion":"1.0","WarpSecUri":"jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8","WarpSecUser":"root","WarpSecPassword":"62XYhC;erw;zZaCmZVzrFEwW","WarpSecEnvironment":"TEST_CN","WarpSecKey":"WarpSecKeyValue"}



		 */
		

		String initParmValue = this.getServletContext().getInitParameter("test").toString();
		Util.debugPrint(true, "Init Param Value: ", initParmValue);
		
		String textToEncrypt = "With great power comes great responsibility.";
		
		// "Generate Random Key"
		byte[] keyBytes = new byte[32];
		
		try {
			SecureRandom.getInstanceStrong().nextBytes(keyBytes);
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		
		// Build the JSON String 
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add("CommandName","WarpSecConfigObject");
		jsonBuilder.add("CommandVersion", "1.0");
		jsonBuilder.add("WarpSecUri",  "jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8");
		jsonBuilder.add("WarpSecUser", "root");
		jsonBuilder.add("WarpSecPassword", "62XYhC;erw;zZaCmZVzrFEwW");
		jsonBuilder.add("WarpSecEnvironment", "TEST_CN");
		jsonBuilder.add("WarpSecKey", "WarpSecKeyValue");
		textToEncrypt = jsonBuilder.build().toString();
				
		Util.debugPrint(true, "Clear Text: ", textToEncrypt);
		
		String encryptedText = null;
		AES aesObject = new AES();
		try {
			encryptedText = aesObject.encyrpt(textToEncrypt);
			Util.debugPrint(true, "Encrypted Text: ", encryptedText);
			Util.debugPrint(true, "Salt (Base64): ", aesObject.salt_Base64);
			Util.debugPrint(true, "Token/Password: ", aesObject.TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String decryptedText = null;
		
		try {
			decryptedText = aesObject.decrypt(encryptedText);
			Util.debugPrint(true, "Decrypted Text: ", decryptedText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		request.getRequestDispatcher("WEB-INF/sec/configurewarpweb.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
