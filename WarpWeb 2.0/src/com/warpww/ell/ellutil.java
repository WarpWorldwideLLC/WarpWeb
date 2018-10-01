package com.warpww.ell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.warpww.sec.DES;
import com.warpww.sec.Hsx;
import com.warpww.util.Util;

public class ellutil {
	
	private String ellUserID = null;
	private String userName = null;
	private String password = null;
	private String email = null;
	private String firstName = null;
	private String lastName = null;
	private String birthDate = null;
	private String language = null;
	private String country = null;
	
	private String rawResponse = null;
	
	public int HttpResponseCode = -1;
	public String HttpResponseMessage = null;
	public String ellResponseMessage = null;
	public String ellResponseStatus = null;
	public String ellResponseUserid = null;
	
	public boolean userAlreadyExists = false;
	
	private boolean debugMode = true;
	
	private Hsx configW = null;
	
	// ********************************************************************************************
	// Accessors and Mutators (Getters and Setters)
	// ********************************************************************************************
	public String getEllUserID () {
		return this.ellUserID;
	}

	public  boolean setEllUserID (String parmEllUserID) {
		boolean returnValue = false;
		
		this.ellUserID = parmEllUserID;
		returnValue = true;
		return returnValue;
	}

	public  boolean setUserName (String parmUserName) {
		boolean returnValue = false;
		
		this.userName = parmUserName;
		returnValue = true;
		return returnValue;
	}
	
	public String getUserName () {
		return this.userName;
	}

	public  boolean setPassword (String parmPassword) {
		boolean returnValue = false;
		
		this.password = parmPassword;
		returnValue = true;
		return returnValue;
	}
	
	public String getPassword () {
		return this.password;
	}
	
	public  boolean setEmail (String parmEmail) {
		boolean returnValue = false;
		
		this.email = parmEmail;
		returnValue = true;
		return returnValue;
	}
	
	public String getEmail () {
		return this.email;
	}	
	
	public  boolean setFirstName (String parmFirstName) {
		boolean returnValue = false;
		
		this.firstName = parmFirstName;
		returnValue = true;
		return returnValue;
	}
	
	public String getFirstName () {
		return this.firstName;
	}
	
	public  boolean setLastName (String parmLastName) {
		boolean returnValue = false;
		
		this.lastName = parmLastName;
		returnValue = true;
		return returnValue;
	}
	
	public String getLastName () {
		return this.lastName;
	}
	
	public  boolean setBirthDate (String parmBirthDate) {
		boolean returnValue = false;
		
		this.birthDate = parmBirthDate;
		returnValue = true;
		return returnValue;
	}
	
	public String getBirthDate () {
		return this.birthDate;
	}
	
	public  boolean setLanguage (String parmLanguage) {
		boolean returnValue = false;
		
		this.language = parmLanguage;
		returnValue = true;
		return returnValue;
	}
	
	public String getLanguage () {
		return this.language;
	}
	
	public  boolean setCountry (String parmCountry) {
		boolean returnValue = false;
		
		this.country = parmCountry;
		returnValue = true;
		return returnValue;
	}
	
	public String getCountry () {
		return this.country;
	}
	
	// ********************************************************************************************	
	// Constructors
	// ********************************************************************************************
	public ellutil(HttpServletRequest request) {
		this.configW = (Hsx) request.getServletContext().getAttribute("configW");
	}

	
	// ********************************************************************************************
	// Public Methods
	// ********************************************************************************************
	public boolean getMemberDataFromDb(int memberID, HttpServletRequest request, HttpServletResponse response) {
		boolean returnValue = false;

		
		try {
			
			String commandValue = "GetEllNewUserData";
			
			// Create the command JSON
			String json = Json.createObjectBuilder()
					 .add("Command", commandValue)
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .build()
					 .toString(); 		

			String jsonParms = "";

			jsonParms = json;
			
			Util.debugPrint(this.debugMode, "ellutil.getMemberDataFromDb jsonParms", jsonParms);
			
			request.setAttribute("CommandText", jsonParms);
			
			// Retrieve the shopping cart information in JSON format.
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
					
			Util.debugPrint(debugMode, "ellutil.getMemberDataFromDb Command Results", request.getAttribute("CommandResults").toString());
			
			// Load and Parse the InputJSON
			if(request.getAttribute("CommandResults") != null ) {
				JsonReader reader = Json.createReader(new StringReader(request.getAttribute("CommandResults").toString()));
				JsonObject originalDoc = reader.readObject();
				String jsonResults = originalDoc.getJsonString("Country").toString();
				System.out.println("ellUtil User Data: " +jsonResults);
				
				this.ellUserID = originalDoc.getJsonString("MemberName").toString().replaceAll("\"", "");
				this.password =  originalDoc.getJsonString("Password").toString().replaceAll("\"", "");
				this.email =  originalDoc.getJsonString("Email").toString().replaceAll("\"", "");
				this.firstName = originalDoc.getJsonString("FirstName").toString().replaceAll("\"", "");
				this.lastName =  originalDoc.getJsonString("LastName").toString().replaceAll("\"", "");
				this.birthDate =  originalDoc.getJsonString("BirthDate").toString().replaceAll("\"", "");
				
				this.language =  originalDoc.getJsonString("Country").toString().replaceAll("\"", "");
				this.country =  originalDoc.getJsonString("Country").toString().replaceAll("\"", "");
				
			}

			
		} catch (Exception ex) {
			returnValue = false;
			ex.printStackTrace();
		}
		
		
		return returnValue;
	}
	
	public boolean addMemberEllUserId(int memberID, HttpServletRequest request, HttpServletResponse response) {
		boolean returnValue = false;

		
		try {
			
			String commandValue = "AddMemberEllUserID";
			
			// Create the command JSON
			String json = Json.createObjectBuilder()
					 .add("Command", commandValue)
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .add("MiscKey", "ELLID")
					 .add("MiscValue", this.ellUserID)
					 .build()
					 .toString(); 		

			String jsonParms = "";

			jsonParms = json;
			
			Util.debugPrint(this.debugMode, "ellutil.addMemberEllUserId jsonParms", jsonParms);
			
			request.setAttribute("CommandText", jsonParms);
			
			// Add the data. 
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
					
			Util.debugPrint(debugMode, "ellutil.addMemberEllUserId Command Results", request.getAttribute("CommandResults").toString());
			
			// Load and Parse the InputJSON
			if(request.getAttribute("CommandResults") != null ) {
				JsonReader reader = Json.createReader(new StringReader(request.getAttribute("CommandResults").toString()));
				JsonObject originalDoc = reader.readObject();
				// String jsonResults = originalDoc.getJsonString("Country").toString();
				
				Util.debugPrint(debugMode, "ellutil.addMemberEllUserId CommandResults", request.getAttribute("CommandResults").toString());


			}

			
		} catch (Exception ex) {
			returnValue = false;
			ex.printStackTrace();
		}
		
		
		return returnValue;
	}
	 
	public boolean addLicenseToAllSolutionsForMember(HttpServletRequest request, HttpServletResponse response, int memberID) { 
		boolean returnValue = false;
		
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "GetEllEntitySolutions")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .add("SystemMode", this.configW.getSystemMode())
					 .build()
					 .toString(); 		

			String jsonParms = "";
		
			
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			Util.debugPrint(this.debugMode,"ellutil.addLicenseToAllSolutionsForMember ComandText", jsonParms);
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
			
			
			//* ******************************************************************************************************************************* */
			//* ******************************************************************************************************************************* */
			// Loop through all EntitySolution Records, assigning Licenses to Lingo Records
			//* ******************************************************************************************************************************* */
			//* ******************************************************************************************************************************* */
			
			String displayCart = "";
			
			// Load and Parse the InputJSON
			if(request.getAttribute("CommandResults") != null ) {
				JsonReader reader = Json.createReader(new StringReader("{\"MySolutions\": [" + request.getAttribute("CommandResults").toString() + "]}"));
				JsonObject originalDoc = reader.readObject();
				// returnValue = originalDoc.getJsonString("CommandResults").toString();
				// System.out.println("Checkout getShoppingCart ProcStatus: " + returnValue);
			
				javax.json.JsonArray cart = originalDoc.getJsonArray("MySolutions");
				for (int i = 0; i < cart.size(); i++) {
					
					JsonObject explrObject = cart.getJsonObject(i);

					String ellLicenseID = explrObject.getJsonString("EllLicenseCode").toString().replaceAll("\"", "");
					this.assignLicense(this.ellUserID, ellLicenseID);
				}
				

				// System.out.println("Cart: " + displayCart);
			} else {
								
				Properties prop = new Properties();
				ClassLoader loader = Thread.currentThread().getContextClassLoader();           
				InputStream stream = loader.getResourceAsStream(this.configW.getResourceFileName());
				prop.load(stream);
				
				// displayCart = "No Solutions Active or Available.";
				displayCart = prop.getProperty("mysolutions.no_solutions");
			}
			
			returnValue = true;
			
			
		} catch (Exception ex) {
			returnValue = false;
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	public boolean parseResponse(String rawResponse) {
		boolean returnValue = false;
		
		// Reset all the response values. 
		this.ellResponseMessage = null;
		this.ellResponseStatus = null;
		this.ellResponseUserid = null;
		
		Util.debugPrint(debugMode, "ellutil.parseResponse: ", rawResponse);
		BufferedReader inputData = new BufferedReader(new StringReader(rawResponse));
		
		String line;
		try {
			while ((line = inputData.readLine()) != null) {
				
				// {"message":"Username must be between 6 and 15 characters!","status":1}
				// {"message":"Username(JohnnyWarp) already exists!","status":1,"userid":229476}
				// {"message":"User(JohnnyWarp) create success","status":0,"userid":229477}
				
				Util.debugPrint(debugMode, "ellutil.parseResponse.LineRead", line);
				
				String responseArray[] = line.split(",");
				for (String responseElement: responseArray) {           
			        if(responseElement.startsWith("{\"message\""))
			        {
			        		this.ellResponseMessage = responseElement.replace("{\"message\":", "");
			        		this.ellResponseMessage = this.ellResponseMessage.replaceAll("\"","");
			        		Util.debugPrint(debugMode, "ellutil.parseResponse.responseElement.message", this.ellResponseMessage);
			        		
			        } else if (responseElement.startsWith("\"status\"")) {
			        		this.ellResponseStatus = responseElement.replace("\"status\":", "");
			        		this.ellResponseStatus = this.ellResponseStatus.replaceAll("}", "");
			        		Util.debugPrint(debugMode, "ellutil.parseResponse.responseElement.status", this.ellResponseStatus);
			        		
			        } else if(responseElement.startsWith("\"userid\"")) {
			        		this.ellResponseUserid = responseElement.replace("\"userid\":", "");
			        		this.ellResponseUserid = this.ellResponseUserid.replaceAll("}", "");
			        		Util.debugPrint(debugMode, "ellutil.parseResponse.responseElement.userid", this.ellResponseUserid);
			        		
			        }
			        
			    }
			}
		} catch (IOException e) {
			returnValue = false;
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	public boolean createNewUser() {
		boolean returnValue = false;
		
		final String uriSuffix = "/user/create";
		
		String createUri = this.configW.getEllApiUri() + uriSuffix;
		
		String param = "clientId=" + this.configW.getEllClientID()
				+ "&username=" + this.ellUserID 
				+ "&password=" + this.password
				+ "&email=" + this.email
				+ "&firstName=" + this.firstName
				+ "&lastName=" + this.lastName
				+ "&birthdate=" + this.birthDate
				+ "&language=" + this.language
				+ "&country=" + this.country;
		
		try {
			
			rawResponse = sendHttpsPost(createUri,param);
			parseResponse(rawResponse);
			
			if(this.ellResponseStatus == "0") {
				this.ellUserID = this.ellResponseUserid;
				returnValue = true;
				Util.debugPrint(debugMode, "ellutil.createNewUser: ", "User Creation Successful.");
			} else {
				if(this.ellUserID != null) {
					returnValue = true;
					this.ellResponseStatus = "0";
					this.userAlreadyExists = true;
					this.ellUserID = this.ellResponseUserid;
					Util.debugPrint(debugMode, "ellutil.createNewUser: ", "User Creation Failed, Existing User Found.");
					
				} else {
					returnValue = false;
					Util.debugPrint(debugMode, "ellutil.createNewUser: ", "User Creation Failed.");
				}

			}
		
		} catch (IOException e) {
			e.printStackTrace();
			returnValue = false;
		}
		
		
		return returnValue;
	}
	
	public boolean assignLicense(String ellUserID, String ellLicenseID) {
		boolean returnValue = false;
		
		// {"message":"The user has been assigned, canot assign again!","status":1}
		
		final String uriSuffix = "/license/assign";
		
		String url = this.configW.getEllApiUri() + uriSuffix;
		String param = "clientId=" + this.configW.getEllClientID() + "&userId=" + ellUserID + "&licenseId=" + ellLicenseID;
		
		System.out.println("URL: " + url);
		System.out.println("Params: " + param);
		
		try {
			System.out.println(sendHttpsPost(url,param));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnValue;
		
	}
	
	public int setSolutionCredentials() {
		int returnValue = -1;
		
		
		
		return returnValue;
	}
	
	public String  CreateSSO(String inputUserID) {
		String returnValue = null;
		final String uriSuffix = "/user/autologin";
		
		
		String ssoUri = this.configW.getEllSsoUri() + uriSuffix;
		
		try {
			
			String ssoParams = "?";
			String parmUid = "uid=" + inputUserID;
			
			String parmUserInfo = "userInfo=" + DES.ellEncrypt(this.configW.getEllSecretKey(), parmUid);
			String parmPartnerID = "&partnerId=" +  this.configW.getEllClientID();
			ssoParams += parmUserInfo + parmPartnerID;
			
			ssoUri += ssoParams; 
			
			returnValue = ssoUri;
			
		} catch (Throwable ex) {
			// TODO: Add Error URI on WARP site to use here. Or use external static S3 site to serve error and maintenance pages.
			returnValue = "Unable to create SSO tokens.";
			ex.printStackTrace();
		}
		
		
		return returnValue;
	}
		
	public boolean setEllSolutionSso(int memberID, HttpServletRequest request, HttpServletResponse response) {
		boolean returnValue = false;
		
		
		try {
			
			String commandValue = "SetEllSolutionSso";
			String ssoString = this.CreateSSO(this.ellUserID);
			
			// Create the command JSON
			String json = Json.createObjectBuilder()
					 .add("Command", commandValue)
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .add("SsoUri", ssoString)
					 .build()
					 .toString(); 		

			String jsonParms = "";

			jsonParms = json;
			
			Util.debugPrint(this.debugMode, "ellutil.setEllSolutionSso jsonParms", jsonParms);
			
			request.setAttribute("CommandText", jsonParms);
			
			// Retrieve the shopping cart information in JSON format.
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
					
			Util.debugPrint(debugMode, "ellutil.setEllSolutionSso Command Results", request.getAttribute("CommandResults").toString());
			
			// Load and Parse the InputJSON
			if(request.getAttribute("CommandResults") != null ) {
				JsonReader reader = Json.createReader(new StringReader(request.getAttribute("CommandResults").toString()));
				JsonObject originalDoc = reader.readObject();
				//String jsonResults = originalDoc.getJsonString("Country").toString();
				//System.out.println("ellUtil.setEllSolutionSso User Data: " +jsonResults);
				
			}

			
		} catch (Exception ex) {
			returnValue = false;
			ex.printStackTrace();
		}
				
		return returnValue;
	}
	
	public SSLSocketFactory init() throws Exception { 
		
		class MyX509TrustManager implements X509TrustManager {
			public MyX509TrustManager() throws Exception {}  
	
			@Override
			public void checkClientTrusted(X509Certificate[] chain,  String authType) throws CertificateException {}  
	
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	
			@Override
			public X509Certificate[] getAcceptedIssuers() {  
	
				return new X509Certificate[] {};  
			}  
		} 
		
		TrustManager[] tm = { new MyX509TrustManager() };  
	
		System.setProperty("https.protocols", "TLSv1");  
	
		SSLContext sslContext = SSLContext.getInstance("TLSv1","SunJSSE");  
		sslContext.init(null, tm, new java.security.SecureRandom());  
		SSLSocketFactory ssf = sslContext.getSocketFactory();  
	
		return ssf;  
	}

	/** 
     *  
     * @param POST_URL 
     * @param token 
     * @return
     * @throwsIOException
     */
	public String sendHttpsPost(String POST_URL,String params) throws IOException {
		
		
		// Clear any old codes before starting.
		this.HttpResponseCode = -1;
		this.HttpResponseMessage = null;
		
		String result = "";  
		String name = this.configW.getEllClientID(); //provided by ELL
		String password = this.configW.getEllSecretKey();   // provided by ELL
		String authString = name + ":" + password;
		
		Util.debugPrint(debugMode, "ellutil.sendHttpsPost POST_URL", POST_URL);
		Util.debugPrint(debugMode, "ellutil.sendHttpsPost params", params);
	
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
	
		URL myURL = new URL(POST_URL);
		HttpsURLConnection con = (HttpsURLConnection) myURL.openConnection();  
		con.setRequestProperty("Authorization", "Basic " + authStringEnc);

		HostnameVerifier hostNameVerify = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) { 
				return true;
			}
        };  

        con.setHostnameVerifier(hostNameVerify); 

        try {
        		con.setSSLSocketFactory(init()); 
        } catch (Exception e1) {
        		throw new IOException(e1);  
        }

		con.setDoOutput(true);      
		con.setDoInput(true);      
		con.setRequestMethod("POST");      
		con.setUseCaches(false);      
		con.setInstanceFollowRedirects(true);      
		con.setRequestProperty("Content-Type "," application/x-www-form-urlencoded ");      
	
		con.connect();   

		PrintWriter out = new PrintWriter(con.getOutputStream());
		out.print(params); 

		out.flush(); 
		out.close(); 
	
		this.HttpResponseCode = con.getResponseCode();
		this.HttpResponseMessage = con.getResponseMessage();
		
		Util.debugPrint(debugMode, "ellutil.sendHttpsPost Response Code", this.HttpResponseCode);
		Util.debugPrint(debugMode, "ellutil.sendHttpsPost Response Message", this.HttpResponseMessage);
		
		InputStream _is;
		if (con.getResponseCode() < HttpsURLConnection.HTTP_BAD_REQUEST) {
		    _is = con.getInputStream();
		} else {
		     /* error from server */
		    _is = con.getErrorStream();
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(_is));
		
		/*
		BufferedReader reader = new BufferedReader(
				new InputStreamReader( 
						con.getInputStream()
				)
		); 
		*/
		
        String line; 

		while ((line = reader.readLine()) != null) { 
			result += line;
		        } 
		reader.close(); 
		con.disconnect();
	
		return result;
    }
}
