package com.warpww.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.hsc;

/** ***************************************************************************
 * The Util class contains a variety of miscellaneous static functions. 
 * The class has no constructor and cannot be instantiated. 
 * 
 * @author Warp Worldwide, LLC
 * @version 2.0
 * @since 2018.06.30
 *
* *************************************************************************** */
// TODO: Review functions, determine if methods are appropriately located or if they need to be moved. Medium Priority.

public final class Util {
	
	
	public static boolean isEmpty(String inputValue) {
		boolean returnValue = true;
		
		if(inputValue != null && !inputValue.isEmpty()) {
			returnValue = true;
		}
		
		return returnValue;
	}
	
	public static String GetFunctionName()
	{
		return "FunctionName";
	}
	
	public static void debugPrint(boolean debugMode, String valueName, String valueToBePrinted) {
		
		if(debugMode) {
			System.out.println(valueName + " : " + valueToBePrinted);
		}
	}
	
	public static void debugPrint(boolean debugMode, String valueName, int valueToBePrinted) {
		
		debugPrint(debugMode, valueName, Integer.toString(valueToBePrinted));
	}
	
	public static void foo()
	{
		// Util x = new Util();
		
		//String statusCode =  getClass().getName() + ".doGet()" + ".." + System.currentTimeMillis() + ";";
		//String statusCode2 = (String) request.getAttribute("ResultCode"); 
		//statusCode2 = Thread.currentThread().getStackTrace()[1].getClassName();
		//statusCode2 += "." + Thread.currentThread().getStackTrace()[1].getMethodName();
		//statusCode2 += x.GetFunctionName();
		//statusCode2 += statusCode;
		//request.setAttribute("ResultCode", statusCode2);
	}
	
	public static void printParams(String titleText, HttpServletRequest request)
	{
		System.out.println();
		System.out.println("**** HTTP Servlet Request Data for " + titleText + " " + Instant.now().toString() + " ****");
		
		System.out.println("Parameter Map:");
		Map params = request.getParameterMap();
	    Iterator i = params.keySet().iterator();
	    while ( i.hasNext() )
		{
		    String key = (String) i.next();
		    String value = ((String[]) params.get( key ))[ 0 ];
		    
		    System.out.println(key + ": " + value);		    
		    
		}
	  
		System.out.println("All request attributes:");
		Enumeration enAttr = request.getAttributeNames(); 
		while(enAttr.hasMoreElements()){
		 String attributeName = (String)enAttr.nextElement();
		 System.out.println("Attribute Name - "+attributeName+", Value - "+(request.getAttribute(attributeName)).toString());
		}
		
		System.out.println("All request parameters:");
		Enumeration enParams = request.getParameterNames(); 
		while(enParams.hasMoreElements()){
		 String paramName = (String)enParams.nextElement();
		 System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}

	}

	public static boolean isAttribute(HttpServletRequest request, String attributeName) {
		
		boolean returnValue = false;
		
		if(null == request.getAttribute(attributeName)) {
			System.out.println("Attribute " + attributeName + " was not found");
		} else {
			System.out.println("Attribute " + attributeName + " was found");
			returnValue = true;
		}
		
		return returnValue;
		
	}
	
	public static boolean isParam(HttpServletRequest request, String paramName) {
		
		boolean returnValue = false;
		
		String paramTest = request.getParameter(paramName);
		if(paramTest != null && !paramTest.isEmpty()) {
			System.out.println("Parameter " + paramTest + " was found");
			returnValue = true;
						
		} else {
			System.out.println("Parameter " + paramTest + " was not found");
		}
		
		return returnValue;
		
	}
	
	// Returns null on error, prints error to the system log.
	public static InputStream StringToStream(String inputString)
	{
		try 
		{
			InputStream stream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8.name()));
			return stream;
		} catch (Exception ex)
		{
			System.out.println(ex.toString());
			System.out.println(ex.getStackTrace().toString());
			return null;
		}
	}
	
	// Returns an unquoted JSON value
	public static String getJsonArrayValue(String jsonInput, String arrayName, String keyName) {
		String returnValue = "";
		
		try {
			InputStream stream = Util.StringToStream(jsonInput);
			JsonReader reader = Json.createReader(stream);
			JsonObject jsonst = reader.readObject();
			reader.close();
			// Get the array
			JsonArray jsonArr = jsonst.getJsonArray(arrayName);
			
			returnValue = jsonArr.getJsonString(0).getString();
			
			// returnValue = jsonst.getJSONArray("arrayName").getJsonString(keyName).getString();
		} catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println(ex.getStackTrace().toString());
			returnValue =  null;
		}
		
		return returnValue;
	}
		
	// Returns an unquoted JSON value
	public static String getJsonValue(String jsonInput, String keyName) {
		String returnValue = "";
		
		try {
			InputStream stream = Util.StringToStream(jsonInput);
			JsonReader reader = Json.createReader(stream);
			JsonObject jsonst = reader.readObject();
			returnValue = jsonst.getJsonString(keyName).getString();
		} catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println(ex.getStackTrace().toString());
			returnValue =  null;
		}
		
		return returnValue;
	}
	
	public static boolean setErrorMessage(HttpServletRequest request) {
		boolean returnValue = false;
		
		try {
			String jsonResponse = request.getAttribute("jsonResponse").toString();
			
			String procStatus = getJsonValue(jsonResponse, "ProcStatus");
			String procMessage = getJsonValue(jsonResponse, "ProcMessage");
			String messageSource = getJsonValue(jsonResponse, "MessageSource");
			String messageCode = getJsonValue(jsonResponse, "MessageCode");
	
			String userMessage = "(" + messageSource + ") " + messageCode + ": " + procMessage;
			request.setAttribute("ErrorMessage", userMessage);
			request.setAttribute("ErrorMessageVisible", "\"display:none;\"");
			returnValue = true;
			
		} catch (Exception ex) {
			returnValue = false;
		}
		
		return returnValue;
	}
	
	public static boolean clearErrorMessage(HttpServletRequest request) {
		boolean returnValue = false;
		
		try {
				
			String userMessage = "";
			request.setAttribute("ErrorMessage", userMessage);
			request.setAttribute("ErrorMessageVisible", "\"display:none;\"");
			returnValue = true;
			
		} catch (Exception ex) {
			returnValue = false;
		}
		
		return returnValue;
	}
	
	public static String getErrorCode (String jsonResponse) {
		String returnValue = "";
		
		
		return returnValue;
	}
	
	public static String getErrorMessage(String jsonResponse) {
		String returnValue = "";
		
		
		return returnValue;
	}

	private String sendPost(String destinationUrl, String userID, String firstName, String lastName, String eMail, String testName) throws Exception {
		
		String USER_AGENT = "Mozilla/5.0";
		
		// Clear Text Redirects - Curren
		String url = "http://warpauth.petersons.com/WarpTest/Plainauthenticate";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
		String urlParameters = "_RequestVerificationToken=";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Response Code: " + responseCode);

		InputStream _is;
		
		if (con.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
		    _is = con.getInputStream();
		} else {
		     /* error from server */
		    _is = con.getErrorStream();
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(_is));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		// System.out.println(response.toString());
		return response.toString();

	}

	public static String sendPostOLC(String userID, String firstName, String lastName, String eMail, String testName) throws Exception {
		
		String USER_AGENT = "Mozilla/5.0";
		
		// Clear Text Redirects - Curren
		String url = ""; 
		// url = "http://warpauth.petersons.com/WarpTest/Plainauthenticate";
		// url = "http://warpauth.petersons.com/olc/PlainAuthenticate";
		
		url = "http://warpauth.petersons.com//olc/PlainAuthenticate";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
		String urlParameters = "_RequestVerificationToken=";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		String pForm = "";
		pForm = "<!DOCTYPE html>";
		pForm += "	<html>";
		pForm += "		<head>";
		pForm += "		    <meta charset=\"utf-8\" />";
		pForm += "		</head>";
		pForm += "		<body>";
		pForm += "			<form>";
		pForm += "			    <input name=\"userData\" value=\"";
		pForm += "								    <proxieduser>";
		pForm += "								  		<ACCESSCODE>BELLEVUE13</ACCESSCODE>";
		pForm += "								  		<SPONSORID>1</SPONSORID>";
		pForm += "								  		<SUBSPONSORID>1</SUBSPONSORID>";
		pForm += "								  		<USERID>JohnnyWarp</USERID>";
		pForm += "								  		<ROLEID>6</ROLEID>";
		pForm += "								  		<FNAME>John</FNAME>";
		pForm += "								  		<LNAME>Arp</LNAME>";
		pForm += "								  		<EMAIL>test@warp.cn</EMAIL>";
		pForm += "								  		<SELTESTCATEGORY>ACT2016</SELTESTCATEGORY>";
		pForm += "									</proxieduser>\" ";
		pForm += "									type=\"hidden\" />";
		pForm += "			    <input name=\"clientId\" value=\"111\" type=\"hidden\" />";
		pForm += "			    <input name=\"symKey\" type=\"hidden\" />";
		pForm += "			    <input type=\"submit\" value=\"Go\" />";
		pForm += "			</form>";
		pForm += "		</body>";
		pForm += "	</html>";
		pForm += "";
		pForm += "";
		
		wr.writeBytes(pForm);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Response Code: " + responseCode);

		InputStream _is;
		
		if (con.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
		    _is = con.getInputStream();
		} else {
		     /* error from server */
		    _is = con.getErrorStream();
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(_is));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		// System.out.println(response.toString());
		return response.toString();

	}

	public static String sendGetOLC(int userID, String memberName, String firstName, String lastName, String eMail, String testName) {
		String returnValue = null;
		
		String pForm = "";
		pForm = "<!DOCTYPE html>";
		pForm += "	<html>";
		pForm += "		<head>";
		pForm += "		    <meta charset=\"utf-8\" />";
		pForm += "		</head>";
		pForm += "		<body>";
		pForm += "			<form action=\"http://warpauth.petersons.com//olc/PlainAuthenticate\" method=\"post\">";
		pForm += "			    <input name=\"userData\" value=\"";
		pForm += "								    <proxieduser>";
		pForm += "								  		<ACCESSCODE>BELLEVUE13</ACCESSCODE>";
		pForm += "								  		<SPONSORID>1</SPONSORID>";
		pForm += "								  		<SUBSPONSORID>1</SUBSPONSORID>";
		pForm += "								  		<USERID>" + memberName + "</USERID>";
		pForm += "								  		<ROLEID>6</ROLEID>";
		pForm += "								  		<FNAME>" + firstName +"</FNAME>";
		pForm += "								  		<LNAME>" + lastName + "</LNAME>";
		pForm += "								  		<EMAIL>" + eMail + "</EMAIL>";
		pForm += "								  		<SELTESTCATEGORY>" + testName + "</SELTESTCATEGORY>";
		pForm += "									</proxieduser>\" ";
		pForm += "									type=\"hidden\" />";
		pForm += "			    <input name=\"clientId\" value=\"111\" type=\"hidden\" />";
		pForm += "			    <input name=\"symKey\" type=\"hidden\" />";
		pForm += "			    <input type=\"submit\" value=\"Go To Test\" />";
		pForm += "			</form>";
		pForm += "		</body>";
		pForm += "	</html>";
		pForm += "";
		pForm += "";
		
		returnValue = pForm;
		
		return returnValue;
	}
	
	// Retrieve the contents of the shopping cart for display to the payor. 
	public static String getShoppingCart (HttpServletRequest request, HttpServletResponse response, int memberID) {
		String returnValue = null;
		
		returnValue = getShoppingCart(request, response, memberID, true, Util.CartContents.All);
		
		return returnValue;
	}
	
	public enum CartContents {
		All, Pending, Sold
	}
	
	// Base Shopping Cart Display
	public static String getShoppingCart (HttpServletRequest request, HttpServletResponse response, int memberID, boolean showButtons, CartContents itemsToInclude) {
		String returnValue = null;
		
		
		try {
			
			String commandValue = "";
			switch(itemsToInclude) {
			case Pending: 
				commandValue = "GetCartPendingItemsOnly";
				break;
			case Sold: 
				commandValue = "GetCartSoldItemsOnly";
			default: 
				commandValue = "GetCart";
				break;
			}
			
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
			request.setAttribute("CommandText", jsonParms);
			
			// Retrieve the shopping cart information in JSON format.
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
			
			//* ******************************************************************************************************************************* */
			//* ******************************************************************************************************************************* */
			// Format the data for display on the web page. 
			//* ******************************************************************************************************************************* */
			//* ******************************************************************************************************************************* */
			
			String displayCart = "";
			int solutionCost = 0;
			int totalCost = 0;
			
			// Load and Parse the InputJSON
			if(request.getAttribute("CommandResults") != null ) {
				JsonReader reader = Json.createReader(new StringReader("{\"CartItems\": [" + request.getAttribute("CommandResults").toString() + "]}"));
				JsonObject originalDoc = reader.readObject();
				// returnValue = originalDoc.getJsonString("CommandResults").toString();
				// System.out.println("Checkout getShoppingCart ProcStatus: " + returnValue);
				
				displayCart += "<table class=\"table1\">";
				displayCart += "<tr><td>&nbsp</td><td>Code</td><td>Name</td><td>Price</td><td>&nbsp</td></tr>";
	
				hsc configW = new hsc();
				
				Properties prop = new Properties();
				ClassLoader loader = Thread.currentThread().getContextClassLoader();           
				InputStream stream = loader.getResourceAsStream(configW.resourceFile);
				prop.load(stream);
				
				hsc hscObject = new hsc();
				
				String removeButtonText = prop.getProperty("cart.remove_button");
				
				javax.json.JsonArray cart = originalDoc.getJsonArray("CartItems");
				for (int i = 0; i < cart.size(); i++) {
					
					JsonObject explrObject = cart.getJsonObject(i);
						
					solutionCost = (int)Double.parseDouble(explrObject.getJsonNumber("SolutionCost").toString());
					totalCost += solutionCost;
					String solutionCode = explrObject.getJsonString("SolutionCode").toString().replaceAll("\"", "");
					
					String localizedSolutionName = prop.getProperty("solution.name." + solutionCode);
					
					
					displayCart += "<tr>";
					displayCart += "<td>" + explrObject.getJsonNumber("CartID").toString() + "</td>";
				    displayCart += "<td>" + explrObject.getJsonString("SolutionCode").toString().replaceAll("\"", "") + "</td>";
				    displayCart += "<td>" + localizedSolutionName + "</td>";
				    displayCart += "<td>" +  hscObject.currencySymbol + " " + String.format("%,.2f", (double)solutionCost/100) + "</td>";
				    if(showButtons) {
				    		displayCart += "<td>" + "<button name=\"remove\" class=\"btn btn-primary\" value=\"" + explrObject.getJsonNumber("CartID").toString() + "\"><fmt:message key=\"warp_vega.p101.payment\" />" + removeButtonText +"</button></td>";
				    } else {
				    	displayCart += "<td>&nbsp</td>";
				    }
				    displayCart += "</tr>";
				}
				System.out.println("totalCost from getShoppingCart: " + totalCost);
				request.setAttribute("ShoppingCartTotalCost", totalCost);
				displayCart += "<tr><td>&nbsp</td><td>&nbsp</td><td>Total Due: </td><td>" +  hscObject.currencySymbol + " " + String.format("%,.2f", (double)totalCost/100) + "</td><td>&nbsp</td></tr>";
				displayCart += "</table>";
				displayCart += "<br><br>";
				request.setAttribute("displayCart", displayCart);
				// System.out.println("Cart: " + displayCart);
			} else {
				displayCart = "No items in your cart.";
			}
			/*
			javax.json.JsonArray value = javax.json.Json.createArrayBuilder()
				     .add(javax.json.Json.createObjectBuilder()
				             .add("type", "home")
				             .add("number", "212 555-1234"))
				     .add(javax.json.Json.createObjectBuilder()
				             .add("type", "fax")
				             .add("number", "646 555-4567"))
				     .build();

				for (javax.json.JsonValue jsonValue : value) {
				    System.out.println(jsonValue);
				}
			*/
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return returnValue;
	}
	
	// Add the solution passed to the shopping cart
	public static boolean addSolutionToCart(HttpServletRequest request, HttpServletResponse response, int memberID, int solutionID) { 
		boolean returnValue = false;
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "AddSolutionToCart")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .add("SolutionID", solutionID)
					 .add("BillingEventID", 0)
					 .build()
					 .toString(); 		


			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	// Remove the solution from the shopping cart
	public static boolean removeSolutionFromCart(HttpServletRequest request, HttpServletResponse response, int memberID, int cartID) { 
		boolean returnValue = false;
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "RemoveSolutionFromCart")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .add("CartID", cartID)
					 .build()
					 .toString(); 		


			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	// Remove the solution from the shopping cart
	public static boolean markCartPending(HttpServletRequest request, HttpServletResponse response, int memberID) { 
		boolean returnValue = false;
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "MarkCartPending")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .build()
					 .toString(); 		


			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
			
			
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	public static String getJsonValueString(String jsonResults, String retrievalKey) {
		String returnValue = "";
		
		// Load and Parse the InputJSON
		JsonReader reader = Json.createReader(new StringReader(jsonResults));
		JsonObject originalDoc = reader.readObject();
		returnValue = originalDoc.getJsonString(retrievalKey).toString().replaceAll("\"", "");
		
		return returnValue;
	}
	
	// Remove the solution from the shopping cart
	public static boolean markCartSold(HttpServletRequest request, HttpServletResponse response, int memberID) { 
		boolean returnValue = false;
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "MarkCartSold")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .build()
					 .toString(); 		


			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return returnValue;
	}

	// Add Solution to Member's available content.
	public static boolean addMemberSolutionOLD(HttpServletRequest request, HttpServletResponse response, int memberID, int solutionID) { 
		boolean returnValue = false;
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "AddMemberSolution")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .add("SolutionID", solutionID)
					 .build()
					 .toString(); 		

			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	// Add Solution to Member's available content.
	public static boolean setMemberSolution(HttpServletRequest request, HttpServletResponse response, int memberID) { 
		boolean returnValue = false;
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "SetMemberSolution")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .build()
					 .toString(); 		

			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return returnValue;
	}
			
	// Retrieve all Solutions & Products for a  member. 
	public static String getMemberSolution(HttpServletRequest request, HttpServletResponse response, int memberID) { 
		String returnValue = "";
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "GetMemberSolution")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .build()
					 .toString(); 		

			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			Util.debugPrint(true, "Cart Json: ", request.getAttribute("CommandText").toString());
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
			
			
			//* ******************************************************************************************************************************* */
			//* ******************************************************************************************************************************* */
			// Format the data for display on the web page. 
			//* ******************************************************************************************************************************* */
			//* ******************************************************************************************************************************* */
			
			String displayCart = "";
			
			// Load and Parse the InputJSON
			if(request.getAttribute("CommandResults") != null ) {
				JsonReader reader = Json.createReader(new StringReader("{\"MySolutions\": [" + request.getAttribute("CommandResults").toString() + "]}"));
				JsonObject originalDoc = reader.readObject();
				// returnValue = originalDoc.getJsonString("CommandResults").toString();
				// System.out.println("Checkout getShoppingCart ProcStatus: " + returnValue);
				
				hsc configW = new hsc();
				
				Properties prop = new Properties();
				ClassLoader loader = Thread.currentThread().getContextClassLoader();           
				InputStream stream = loader.getResourceAsStream(configW.resourceFile);
				prop.load(stream);
				
				displayCart += "<table class=\"table1\">";
				displayCart += "<tr>"
						+ "<td><b>Solution Code</b></td>"
						+ "<td><b>Solution Name</b></td>"
						+ "<td><b>Product Code</b></td>"
						+ "<td><b>Product Name</b></td>"
						+ "<td><b>Link</b></td>"
						+ "<td><b>Start Date</b></td>"
						+ "</tr>"; 
	
				
				
				javax.json.JsonArray cart = originalDoc.getJsonArray("MySolutions");
				for (int i = 0; i < cart.size(); i++) {
					
					
					JsonObject explrObject = cart.getJsonObject(i);
					
					
					String solutionCode = explrObject.getJsonString("SolutionCode").toString().replaceAll("\"", "");
					String productCode = explrObject.getJsonString("ProductCode").toString().replaceAll("\"", "");
					String rawDate = explrObject.getJsonString("StartDate").toString().replaceAll("\"", "").substring(0, 10);
					
					// String formattedDate = LocalDate.parse(rawDate, );
					String localizedProductName = prop.getProperty("product.name." + productCode);
					String localizedSolutionName = prop.getProperty("solution.name." + solutionCode);
					
					displayCart += "<tr>";
				    displayCart += "<td>" + solutionCode + "</td>";
				    displayCart += "<td>" + localizedSolutionName + "</td>";
				    displayCart += "<td>" + productCode + "</td>";
				    displayCart += "<td>" + localizedProductName + "</td>";
				    String keyValue = explrObject.getJsonString("ProductExternalKey").toString().replaceAll("\"", "");
				    String memberKeyValue = explrObject.getJsonString("MemberSolutionExternalKey").toString().replaceAll("\"", "");
				    
				    // Create Hyperlink on the MySolutions page for users to go to. 
				    System.out.println("MemberKeyValue" + memberKeyValue);
				    switch(keyValue.substring(0, 4)) {
				    case "ILR:": 
				    	 	displayCart += "<td>" + "<a href=\"" + keyValue.replaceAll("ILR:", "") + "\" class=\"btn btn-primary\" target=\"_blank\" >Go Now!</a>";
				    		break;
				    case "OLC:": 
				    		displayCart += "<td>" + "<button name=\"olcCmd\" class=\"btn btn-primary\" value=\"" + keyValue.replaceAll("OLC:", "") + "\">Go Now!</button>";
				    		break;
				    case "LGO:":
				    	displayCart += "<td>" + "<button name=\"olcCmd\" class=\"btn btn-primary\" value=\"" + memberKeyValue + "\">Go Now!</button>";
				    		break;
				    case "N/A:":
				    	displayCart += "<td>" + "&nbsp";
				    		break;
				    	default:
				    		displayCart += "<td>" + "<a>&nbsp</a>";
				    		break;
				    }
				    // displayCart += "<td>" + "<a href=\"" + explrObject.getJsonString("ProductExternalKey").toString().replaceAll("\"", "") + "\" class=\"btn btn-primary\" target=\"_blank\" >Go Now!</a>";
				    // displayCart += "<td>" + explrObject.getJsonString("ProductExternalKey").toString().replaceAll("\"", "") + "</td>";
				    displayCart += "<td>" + rawDate + "</td>";
				    displayCart += "</tr>";
				}
				
				displayCart += "</table>";
				displayCart += "<br><br>";
				displayCart += "<form>";
				request.setAttribute("displayCart", displayCart);
				// System.out.println("Cart: " + displayCart);
			} else {
				// displayCart = "No Solutions Active or Available.";
				
				hsc configW = new hsc();
				
				Properties prop = new Properties();
				ClassLoader loader = Thread.currentThread().getContextClassLoader();           
				InputStream stream = loader.getResourceAsStream(configW.resourceFile);
				prop.load(stream);
				
				// displayCart = "No Solutions Active or Available.";
				displayCart = prop.getProperty("mysolutions.no_solutions");
			}
			
			returnValue = displayCart;
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	public static String getMemberInfo(HttpServletRequest request, HttpServletResponse response, int memberID) {
		String returnValue = null;
		
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "GetMemberInfo")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("MemberID", memberID)
					 .build()
					 .toString(); 		

			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
			
			String MemberName = getJsonValueString(request.getAttribute("CommandResults").toString(), "MemberName");
			String MemberFirstName = getJsonValueString(request.getAttribute("CommandResults").toString(), "MemberFirstName");
			String MemberLastName = getJsonValueString(request.getAttribute("CommandResults").toString(), "MemberLastName");
			String MemberEmail = getJsonValueString(request.getAttribute("CommandResults").toString(), "MemberEmail");
			
			request.setAttribute("MemberName", MemberName);
			request.setAttribute("MemberFirstName", MemberFirstName);
			request.setAttribute("MemberLastName", MemberLastName);
			request.setAttribute("MemberEmail", MemberEmail);
			
			returnValue = MemberName;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return returnValue;
	}
}
