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

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.warpww.sec.AES;
import com.warpww.sec.Hsx;
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

		
		
		
		/* Read from ServletContext 
		String initParmValue = this.getServletContext().getInitParameter("test").toString();
		Util.debugPrint(true, "Init Param Value: ", initParmValue);
		*/



		String initKey = this.getServletContext().getInitParameter("WarpInit").toString();
		Hsx configW = new Hsx();
		
		Util.debugPrint(true,"Before Load", configW.getJdbcUri());
		
		configW.LoadFromKey(initKey);

		Util.debugPrint(true,"After oad", configW.getJdbcUri());
		Util.debugPrint(true,"Resoure File Name", configW.getResourceFileName());
		
		this.getServletContext().setAttribute("configW", configW);
		
		Hsx configW2 = (Hsx) this.getServletContext().getAttribute("configW");

		Util.debugPrint(true,"Context Data", configW2.getResourceFileName());
		
		
		request.getRequestDispatcher("WEB-INF/sec/configurewarpweb.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected String createEncryptedKey() {
		String returnValue = null;
		
		AES aesObject = new AES();
		aesObject.ClearDataText = this.getConfig1();
		aesObject.passphrase = "gracie is beautiful 20131206";
		
		
		try {
				
			
			aesObject.encyrpt();
			
			Util.debugPrint(true, "Clear Text: ", aesObject.ClearDataText);
			Util.debugPrint(true, "Salt: ", aesObject.salt64);
			Util.debugPrint(true, "Passphrase: ", aesObject.passphrase);
			Util.debugPrint(true, "Encrypted Text: ", aesObject.encryptedData64);
			Util.debugPrint(true, "IV", aesObject.IV64);
			
			
			//newData = this.getServletContext().getInitParameter("WarpInit").toString();
			
			String originalData = aesObject.decrypt();
			Util.debugPrint(true, "Decrypted Text: ", originalData);
			
			
			
			/*
			String encryptedData64 = "";
			String IV64 = "";
			String passphrase = "";
			String salt64 = "";
			
			encryptedData64 = this.getServletContext().getInitParameter("WarpInit").toString();
			IV64 = "Mv7bSOfSu2O15ISOAxYWxg==";
			passphrase = "gracie is beautiful 20131206";
			salt64 = "SldlEIVttItC8O5IN4GOkgRjVpQ=";
			
			Util.debugPrint(true, "Salt2: ", salt64);
			Util.debugPrint(true, "Passphrase2: ", passphrase);
			Util.debugPrint(true, "Encrypted Text2: ", encryptedData64);
			Util.debugPrint(true, "IV2", IV64);
			
			
			AES aes = new AES();
			aes.encryptedData64 = encryptedData64;
			aes.IV64 = IV64;
			aes.passphrase = passphrase;
			aes.salt64 = salt64;
			aes.decrypt();
			Util.debugPrint(true, "New Object Decrypted Text: ", aes.ClearDataText);
			*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	
	protected String getConfig1() {
		// Build the JSON String 
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add("CommandName","WarpSecConfigObject");
		jsonBuilder.add("CommandVersion", "1.0");
		jsonBuilder.add("WarpSecUri",  "jdbc:mysql://localhost:3306/WarpSec_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8");
		jsonBuilder.add("WarpSecUser", "root");
		jsonBuilder.add("WarpSecPassword", "62XYhC;erw;zZaCmZVzrFEwW");
		jsonBuilder.add("WarpSecEnvironment", "TEST_CN");
		jsonBuilder.add("WarpSecKey", "WarpSecKeyValue");
		String textToEncrypt = jsonBuilder.build().toString();
		
		return textToEncrypt;

	}
	
}
