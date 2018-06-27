package com.warpww.sec;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.util.Command;

import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.json.Json;

/** ***************************************************************************
 * AuthMod contains authorization and authentication functions.<br>
 * 
 * AuthMod reads values from the HttpServletRequest and writes values to 
 * HttpServletRequest for use in processing by the rest of the applcation. 
 * 
 * @author Warp Worldwide, LLC (Copyright 2016-18 WARP Worldwide, LLC. Subject to the XYZ license.)
 * @version 2.0
 * @since 2018.06.30
 *
* *************************************************************************** */
// TODO:  Review methods, determine if methods are appropriately located or if they need to be moved. Medium Priority.

public class AuthMod {

	//**********************************************************************
	// Private Variables
	//**********************************************************************
	private HttpServletRequest request;
	private HttpServletResponse response;
		
	private String currentPage;
	private String lastPageVisited;
	private int memberID;
	private String authTime;
	private boolean authenticated;
	private String firstName;
	private String lastName;
	
	//**********************************************************************
	// Accessors and Mutators (Getters and Setters)
	//**********************************************************************
	
	// memberID
	public int getMemberID() {
		return this.memberID;
	}
	
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	
	
	// firstName
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	// lastName
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	// request
	public HttpServletRequest getRequest() {
		return this.request;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	// response
	public HttpServletResponse getResponse() {
		return this.response;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	// currentPage
	public String getCurrentPage() {
		String returnValue = null;
		returnValue = this.currentPage;
		return returnValue;
	}
	
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
	// lastPageVisited
	public String getLastPageVisited() {
		String returnValue = null;
		returnValue = this.lastPageVisited;
		return returnValue;
	}
	
	public void setLastPageVisited(String lastPageVisited) {
		this.lastPageVisited = lastPageVisited;
	}
	
	// authTime
	public String getAuthTime() {
		String returnValue = null;
		returnValue = this.authTime;
		return returnValue;
	}
	
	public void setAuthTime(String authTime) {
		this.authTime = authTime;
	}
	
	// authenticated
	public boolean getAuthenticated() {
		boolean returnValue = false;
		returnValue = this.authenticated;
		return returnValue;
	}
	
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	//**********************************************************************
	// Enumerations
	//**********************************************************************
	public enum Sign { 
		
		in, out
		
	}
	
	
	//**********************************************************************
	// Constructors
	//**********************************************************************
	/** 
	 * <b>AuthMod</b> class constructor.
	 * <br>
	 * Accepts no parameters, takes no actions. 
	 */
	public AuthMod() {
		
	}
	
	public AuthMod(HttpServletRequest request, HttpServletResponse response) {
		
		this.request = request;
		this.response = response;
		
	}
	
	public AuthMod(HttpServletRequest request, HttpServletResponse response, Sign actionValue) {

		this.request = request;
		this.response = response;
		String greeting;
		
		// indicates the values are already stored in session variables. 
		switch (actionValue) {
		
		case in: 
			greeting = getGreeting(Integer.parseInt(this.request.getAttribute("MemberID").toString()), this.request, this.response, true);
			this.memberID = Integer.parseInt(request.getAttribute("MemberID").toString());
			this.authenticated = true;
			System.out.println("Authenticated.");
			break;
			
		default:
			this.authenticated = false;
			this.memberID = 0;
			greeting = getGreeting(this.memberID, this.request, this.response, false);
			System.out.println("Not Authenticated.");
			break;
			
		}
				
		request.setAttribute("Greeting", greeting);

			
		
	}
	
	//**********************************************************************
	// Methods
	//**********************************************************************
	//----------------------------------------------------------------------
	//Authenticate: Authenticates Users when a page loads. 
	//----------------------------------------------------------------------
	public boolean authenticate() {
		String callingMethod = Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
		
		boolean returnValue = false;
		
		try {
			
			// Set lastVisitedPageName 
			String uri = request.getRequestURI();
			String pageName = uri.substring(uri.lastIndexOf("/")+1);
			request.setAttribute("pageName", pageName);
			String greeting = "";
		
			// Authenticate the User via Cookie; populate memberID and authTime fields.
			if(authenticateToken(this.request)) {
				greeting = getGreeting(Integer.parseInt(this.request.getAttribute("TokenMemberID").toString()), this.request, this.response, true);
				this.memberID = Integer.parseInt(request.getAttribute("verifyToken_MemberID").toString());
				this.authTime = request.getAttribute("verifyToken_CreateTime").toString();
				this.authenticated = true;
				System.out.println("Authenticated.");
			} else {
				this.authenticated = false;
				this.memberID = 0;
				greeting = getGreeting(this.memberID, this.request, this.response, false);
				System.out.println("Not Authenticated.");
			}	

			request.setAttribute("Greeting", greeting);
			returnValue = true;
		
		} catch(Exception ex) {
			returnValue = false;
			System.out.println("Calling Method: " + callingMethod);
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	public static String getGreetingAuthenticated(int MemberID, HttpServletRequest request, HttpServletResponse response) {
		String returnValue = "";
		
		try
		{
		String json = Json.createObjectBuilder()
				 .add("Command", "GetGreeting")
				 .add("AuID", 1)
				 .add("IuID", 1)
				 .add("MemberID", MemberID)
				 .build()
				 .toString(); 		
		
		String jsonParms = "";
		jsonParms = json;
		request.setAttribute("CommandText", jsonParms);
		
		//request.getRequestDispatcher("/dbProcess").include(request, response);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
		dispatcher.include(request, response);
		
		Command cmd = new Command(request.getAttribute("CommandResults").toString());
		
		/* 
		System.out.println("Status: " + cmd.CommandResults);
		System.out.println("Constant: " + cmd.COMMAND_SUCCESS);
		System.out.println("Comparison: " + cmd.ProcStatus.equals( cmd.COMMAND_SUCCESS));
		*/
		
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream("/com/warpww/web/i18n/warp.properties");
		prop.load(stream);
		/*
		Enumeration en = prop.propertyNames(); 
		while (en.hasMoreElements()) { 
			System.out.println(en.nextElement()); 
		} 
		*/
		
		String greeting = prop.getProperty("topmenu.greeting");
		String mySolutions = prop.getProperty("usermenu.my_solutions");
		String shoppingCart = prop.getProperty("usermenu.shopping_cart");
		String signout = prop.getProperty("usermenu.signout");
		
		
		
		if(cmd.CommandResults.equals( cmd.COMMAND_SUCCESS)) {
			returnValue = "<fmt:message key=\"cartmaint.topmenu.greeting\" /> " + cmd.FirstName + " " + cmd.LastName;
			returnValue += "<div class=\"account-content\">";
			returnValue += "<a href=\"#openModalLogout\">" + signout + "</a>";
			returnValue += "<a href=\"#\">" + mySolutions + "</a>";
			returnValue += "</div>";
			returnValue += "";	
			
			String greetingText = greeting + " " + cmd.FirstName + " " + cmd.LastName;
			

			returnValue = "    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">" + greetingText  + "<span class=\"caret\"></span></a>";
			returnValue += "    <ul class=\"dropdown-menu\">";
			returnValue += "      <li><a href=\"#openModalLogout\">" + signout + "</a></li>";
			returnValue += "      <li><a href=\"mysolutions\">" + mySolutions + "</a></li>";
			returnValue += "      <li><a href=\"cartmaint\">" + shoppingCart + "</a></li>";
			returnValue += "    </ul>";
			returnValue += "";
			returnValue += "";
			
			// System.out.println(returnValue);
			
			// returnValue = greetingText;
			request.setAttribute("accountButton", returnValue);
		}
		
		} catch (Exception ex)
		{
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = null;
		}
		
		return returnValue;
	}
	
	public static String getGreetingAnonymous(HttpServletRequest request, HttpServletResponse response) {
		String returnValue = "";
		
		try
		{
			Properties prop = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();           
			InputStream stream = loader.getResourceAsStream("/com/warpww/web/i18n/warp.properties");
			prop.load(stream);
			/*
			Enumeration en = prop.propertyNames(); 
			while (en.hasMoreElements()) { 
				System.out.println(en.nextElement()); 
			} 
			*/
			
			String signin = prop.getProperty("usermenu.signin");
			
			
			returnValue = "<a href=\"#openModalLogin\">" + signin + "</a>";
			request.setAttribute("accountButton", returnValue);
		} catch (Exception ex)
		{
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = null;
		}
		
		return returnValue;
	}
	
	public static String getGreeting(int MemberID, HttpServletRequest request, HttpServletResponse response, boolean authenticated) {
		String returnValue = "";
		// accountButton
		
		
		try
		{
			if(authenticated) {
				returnValue = getGreetingAuthenticated(MemberID, request, response);
			} else {
				returnValue = getGreetingAnonymous(request, response);
			}

		
		} catch (Exception ex)
		{
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = null;
		}
		
		return returnValue;
	}
	
	// Format time for the token. 
	public static String formatTokenTime(Instant instantValue) {
		String returnValue = null;
		
		LocalDateTime datetime = LocalDateTime.ofInstant(instantValue, ZoneOffset.UTC);
		returnValue = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(datetime);
		
		return returnValue;
	}
	
	public static int getTokenMemberID(String memberHex, String createTime) {
		int returnValue = -1;
		
		int rawValue = Integer.parseInt(memberHex, 16);
		String offset = createTime.substring(14);
		hsc hscObject = new hsc();
		returnValue = rawValue - Integer.parseInt(offset) - hscObject.tokenMemberPadding;
		
		return returnValue;
	}
	
	public static String formatTokenMemberID(String createTime, int memberID) {
		String returnValue = null;
		
			// Token is: Embedded Member ID
			String offset = createTime.substring(14);
			hsc hscObject = new hsc();
			int memberCodeN = memberID + Integer.parseInt(offset) + hscObject.tokenMemberPadding;
			String memberCode = Integer.toHexString(memberCodeN);
			
			returnValue = memberCode;
					
		return returnValue;
	}
	
	public static boolean validateTokenHash(String tokenHash, String createTime, String userAgent, String remoteAddr, int memberID) {
		boolean returnValue = false;
		
		try {

			String hashCheck = formatTokenHash(createTime, userAgent, remoteAddr, memberID);
			
			if(tokenHash.equals(hashCheck))
			{
				returnValue = true;
			}
			
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = false;				
		}
		
		// System.out.println("validateTokenHash: " + returnValue);
		return returnValue;
	}
	
	public static String formatTokenHash (String createTime, String userAgent, String remoteAddr, int memberID) {
		String returnValue = null;
		
		try { 
			String tokenHashInput = createTime + userAgent + remoteAddr + Integer.toString(memberID);
					
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(tokenHashInput.getBytes());
			returnValue = new String(messageDigest.digest());
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = null;				
		}
		
		return returnValue;
	}
	
	public static String encodeToken (String rawTokenValue) { 
		String returnValue = null;
		
		try {
			
			returnValue = URLEncoder.encode(rawTokenValue, "UTF-8");
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = null;
		}

		return returnValue;
	}
	
	public static String decodeToken (String encodedTokenValue) {
		String returnValue = null;
		
		try {
			
			returnValue = URLDecoder.decode(encodedTokenValue, "UTF-8");
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = null;

		}
		
		return returnValue;
	}
	
	public static String createAuthenticationToken(HttpServletRequest request, HttpServletResponse response, int memberID) {
		String returnValue = null;
		try {

			// Start creating the token in tokenFinal.
			// tokenFinal format a string is: 
			// formatted createTime (yyyyMMddHHmmssSSS)
			// memberCode = memberID + offSet + tokenMemberPadding, converted to Hex
			
			
			// Date/Time Token Created
			Instant createTime = Instant.now();
			String tokenCreateTime = formatTokenTime(createTime);
			
			// MemberID Token Value
			String tokenMemberCode = formatTokenMemberID(tokenCreateTime, memberID);
			
			// Token HASH Value
			String tokenHashValue = formatTokenHash(tokenCreateTime, request.getHeader("User-Agent"), request.getRemoteAddr(), memberID);
			
			// Compose the Raw Token
			String tokenRaw = tokenCreateTime + tokenMemberCode + tokenHashValue;
			
			// Encode the entire token
			String tokenFinal = encodeToken(tokenRaw);
			
			// Store a token in a cookie. 
			hsc hscObject = new hsc();
			Cookie userCookie = new Cookie(hscObject.cookieName, tokenFinal);
			userCookie.setComment("Used by Warp Worldwide website.");
			userCookie.setMaxAge(hscObject.tokenExpirationDuration);      
			// userCookie.setDomain(hsc.cookieDomain);
			userCookie.setSecure(hscObject.cookieSSL);       	// Cookie can only be retreived over SSL
			userCookie.setHttpOnly(true);       		 	// Cookie can only be retrieved via HTTP 
			response.addCookie(userCookie);
			System.out.println("Authentication Cookie added.");
			returnValue = tokenFinal;
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = null;			
		}		
		return returnValue;
	}
	
	public static boolean authenticateToken(HttpServletRequest request) {

		boolean returnValue = false;
		
		// Loop through available Cookes and find the correct one.
		String cookieValue = null;
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) 
		{
		    for(int i=0; i<cookies.length; i++) 
		    {
		    		
		        Cookie cookie = cookies[i];
		        hsc hscObject = new hsc();
		        if (hscObject.cookieName.equals(cookie.getName())) 
		        {
		        		/*
		            System.out.println("Domain: " + cookie.getDomain()); 
		            System.out.println("Comment: " + cookie.getComment());
		            System.out.println("MaxAge: " + cookie.getMaxAge());
		            System.out.println("Name: " + cookie.getName());
		            System.out.println("Path: " + cookie.getPath());
		            System.out.println("Value: " + cookie.getValue());
		            System.out.println("Cookie Name: " + cookie.getName());
		            */
		        		
		            cookieValue = cookie.getValue();
		            // System.out.println("Cookie Name: " + cookie.getName() + " [" + cookieValue + "]");
		            
		            /*
		            System.out.println("cookieValue: " + cookieValue);
		            System.out.println("Version: " + cookie.getVersion());
		            System.out.println("Class: " + cookie.getClass().toString());
		            System.out.println("Secure: " + cookie.getSecure());
		            */
		        }
		        
		        
		         returnValue = verifyToken(cookieValue, request);
		         if(returnValue) {
		        	 	//System.out.println(Instant.now().toString() + " " + request.getRequestURI() + " " + request.getMethod() + " authentication succeeded [" + cookieValue + "].");
		         } else {
		        	 	//System.out.println(Instant.now().toString() + " " + request.getRequestURI() + " " + request.getMethod() + " authentication failed [" + cookieValue + "].");
		         }
		    }
		}
		else
		{
		    returnValue = false;
		}   
		
		//System.out.println("authenticateToken: " + returnValue);
		return returnValue;
	}
	
	public static boolean verifyToken (String tokenValue, HttpServletRequest request) {
		boolean returnValue = false;
		// Opposite of creating a token
		if(tokenValue == null) {
			return false;
		}
		
		try {
			String tokenRaw = decodeToken(tokenValue);		
			
			String tokenCreateTime = tokenRaw.substring(0,  17);
			request.setAttribute("verifyToken_CreateTime", tokenCreateTime);
			
			String tokenMemberHex = tokenRaw.substring(17, 23);
			
			int tokenMemberID = getTokenMemberID(tokenMemberHex, tokenCreateTime);
			request.setAttribute("verifyToken_MemberID", tokenMemberID);
			
			boolean tokenHash = validateTokenHash(tokenRaw.substring(23), tokenCreateTime, request.getHeader("User-Agent"), request.getRemoteAddr(), tokenMemberID);
			
			if(tokenHash) { request.setAttribute("TokenMemberID", tokenMemberID); }
			
			returnValue = tokenHash;
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = false;
		}
		
		// System.out.println("verifyToken: " + returnValue);
		return returnValue;
	}
	
	public static boolean validateSignon(HttpServletRequest request, HttpServletResponse response)
	{
		boolean returnValue = false;
		
		try
		{
		String json = Json.createObjectBuilder()
				 .add("Command", "ValidateSignon")
				 .add("AuID", 1)
				 .add("IuID", 1)
				 .add("MemberName", request.getParameter("memberName"))
				 .add("PassphraseHash", request.getAttribute("passphraseHash").toString())
				 .build()
				 .toString(); 		
		
		String jsonParms = "";
		jsonParms = json;
		request.setAttribute("CommandText", jsonParms);
		
		request.getRequestDispatcher("/dbProcess").include(request, response);
		
		Command cmd = new Command(request.getAttribute("CommandResults").toString());
		
		/* 
		System.out.println("Status: " + cmd.CommandResults);
		System.out.println("Constant: " + cmd.COMMAND_SUCCESS);
		System.out.println("Comparison: " + cmd.ProcStatus.equals( cmd.COMMAND_SUCCESS));
		*/
		
		if(cmd.CommandResults.equals( cmd.COMMAND_SUCCESS)) {
			String token = createAuthenticationToken(request, response, Integer.parseInt(cmd.MemberID));
			request.setAttribute("MemberID", Integer.parseInt(cmd.MemberID));
			// System.out.println("Token: " + token);
			returnValue = true;
		}
		
		} catch (Exception ex)
		{
			System.out.println(ex.toString());
			ex.printStackTrace();
			returnValue = false;
		}
		
		return returnValue;
	}
	
	public static boolean authenticate(HttpServletRequest request, HttpServletResponse response) { 
		boolean returnValue = false;
		
		// Validate authentication - 
		String callingMethod = Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
		String greeting = "";
		
		try {
			if(AuthMod.authenticateToken(request)) {
				returnValue = true;
				greeting = AuthMod.getGreeting(Integer.parseInt(request.getAttribute("TokenMemberID").toString()), request, response, true);
				request.setAttribute("Greeting", greeting);
			} else {
				returnValue = false;
				greeting = AuthMod.getGreeting(0, request, response, false);;
			}
		} catch (Exception ex) {
			returnValue = false;
			System.out.println("Calling Method: " + callingMethod);
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	public static boolean printLoginMenu(HttpServletRequest request, HttpServletResponse response, boolean authenticated, int MemberID) { 
		boolean returnValue = false;
		
		// accountButton
		
		
		try { 
			if(authenticated) {
				String accountButton = "";
				
			} else {
				
			}
			
			returnValue = true;
		} catch (Exception ex) {
			returnValue = false;
			ex.printStackTrace();
		}
		
		
		return returnValue;
	}
	
}
