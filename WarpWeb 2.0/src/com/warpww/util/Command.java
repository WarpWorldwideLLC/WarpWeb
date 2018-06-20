package com.warpww.util;

import java.io.StringReader;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonObject;


public class Command 
{
	public String MemberName = "";
	public String EmailAddress = "";
	public String ErrorMessage = "";
	public String CommandResults = "";
	public String CommandText = "";
	public String CommandName = "";
	public int AuID = 0;
	public int IuID = 0;
	public String NewMemberName = "";
	public String MemberNameKey = "";
	public String MemberID = "";
	public String ProcStatus = "";
	public String MessageSource = "";
	public String MessageCode = "";
	public String FirstName = "";
	public String LastName = "";
	

	// Public Constants
	final public String COMMAND_SUCCESS = "Success";
	final public String COMMAND_ERROR = "Error";

	public Command(String pJsonInput) {
		
		// Parse the InputJSON
		JsonReader reader = Json.createReader(new StringReader(pJsonInput));
		JsonObject inputJson = reader.readObject();
		try {
			
			if(pJsonInput.indexOf("MemberName") > -1) {MemberName = inputJson.getString("MemberName").toString();}
			if(pJsonInput.indexOf("EmailAddress") > -1) {EmailAddress = inputJson.getString("EmailAddress").toString();}
			if(pJsonInput.indexOf("ErrorMessage") > -1) {ErrorMessage = inputJson.getString("ErrorMessage").toString();}
			if(pJsonInput.indexOf("ErrorMessage") > -1) {ErrorMessage = inputJson.getString("ErrorMessage").toString();}
			if(pJsonInput.indexOf("CommandResults") > -1) {CommandResults = inputJson.getString("CommandResults").toString();}
			if(pJsonInput.indexOf("CommandText") > -1) {CommandText = inputJson.getString("CommandText").toString();}
			if(pJsonInput.indexOf("CommandName") > -1) {CommandName = inputJson.getString("CommandName").toString();}
			if(pJsonInput.indexOf("AuID") > -1) {AuID = inputJson.getJsonNumber("AuID").intValue();}
			if(pJsonInput.indexOf("IuID") > -1) {IuID = inputJson.getJsonNumber("IuID").intValue();}
			if(pJsonInput.indexOf("NewMemberName") > -1) {NewMemberName = inputJson.getString("NewMemberName").toString();}
			if(pJsonInput.indexOf("MemberNameKey") > -1) {MemberNameKey = inputJson.getString("MemberNameKey").toString();}
			if(pJsonInput.indexOf("MemberID") > -1) {MemberID = inputJson.getString("MemberID").toString();}
			if(pJsonInput.indexOf("MemberNumber") > -1) {MemberID = inputJson.getJsonNumber("MemberNumber").toString();}
			if(pJsonInput.indexOf("ProcStatus") > -1) {ProcStatus = inputJson.getString("ProcStatus");}
			if(pJsonInput.indexOf("MessageSource") > -1) {MessageSource = inputJson.getString("MessageSource").toString();}
			if(pJsonInput.indexOf("MessageCode") > -1) {MessageCode = inputJson.getString("MessageCode").toString();}
			if(pJsonInput.indexOf("FirstName") > -1) {FirstName = inputJson.getString("FirstName").toString();}
			if(pJsonInput.indexOf("LastName") > -1) {LastName = inputJson.getString("LastName").toString();}
			

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}


	}
	
	// Take request object and convert Parameters and Attributes to JSON objects for ease of handling
	public static String createRequestInput(HttpServletRequest request)
	{
		Util.foo();
		int inputCount = 0;
		String inputName = "NO_PARAMETERS";
		String inputValue = "NO_PARAMETERS";
		
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
				
		// Attributes First - Server Side data
		Enumeration<String> enAttr = request.getAttributeNames(); 
		while(enAttr.hasMoreElements())
		{
			inputName = (String)enAttr.nextElement();
			inputValue = request.getAttribute(inputName).toString();
			jsonBuilder.add(inputName, inputValue);
			inputCount +=1;
		}
		
		Enumeration<String> enParams = request.getParameterNames(); 
		while(enParams.hasMoreElements())
		{
			inputName = (String)enParams.nextElement();
			inputValue = request.getParameter(inputName).toString();
		 jsonBuilder.add(inputName, inputValue);
		 inputCount +=1;
		}
		
		// Empty Values
		if(inputCount <1 )
		{
			jsonBuilder.add(inputName, inputValue);
		}
		
		jsonBuilder.add("PARAMETER_COUNT", inputCount);
		
		String returnValue = jsonBuilder.build().toString();
		request.setAttribute("inputList", returnValue);
		return returnValue;

	}

	public static String createCommand(String pCommandName, String pInputJSON)
	{
		String returnValue = "";
		
		switch(pCommandName) 
		{
			case "AddAccount":
				break;
			case "GetMemberNameFromEmailAddress":
				GetMemberNameFromEmailAddress(pCommandName, pInputJSON);
			default:
				break;
		}
		
		
		return returnValue;
	}
	
	public static String getCommandResultStatus(String jsonResults) {
		String returnValue = "";
		
		// Load and Parse the InputJSON
		JsonReader reader = Json.createReader(new StringReader(jsonResults));
		JsonObject originalDoc = reader.readObject();
		returnValue = originalDoc.getJsonString("procStatus").toString();
		
		return returnValue;
	}
	
	public static String getElement(String jsonResults, String requestedElement) {
		String returnValue = "";
		
		// Load and Parse the InputJSON
		JsonReader reader = Json.createReader(new StringReader(jsonResults));
		JsonObject originalDoc = reader.readObject();
		returnValue = originalDoc.getJsonString(requestedElement).toString();
		
		return returnValue;
	}
	
	private static String GetMemberNameFromEmailAddress(String pCommandName, String pInputJSON) {
		String returnValue = "";
		
		// Parse the InputJSON
		JsonReader reader = Json.createReader(new StringReader(pInputJSON));
		JsonObject originalDoc = reader.readObject();
		JsonString temp = originalDoc.getJsonString("emailAddress");
		System.out.println("EMailValue:" + temp);
		
		
		// Build the Command JSON 
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add("CommandName", pCommandName);
		jsonBuilder.add("CommandVersion", originalDoc.getJsonString("1.0"));
		jsonBuilder.add("AuID", originalDoc.getJsonString("emailAddress"));
		jsonBuilder.add("PuID", originalDoc.getJsonString("emailAddress"));
		jsonBuilder.add("EMailAddress", originalDoc.getJsonString("emailAddress"));

		 returnValue = jsonBuilder.build().toString();
		
		/*
		
		
		// Attributes First - Server Side data
		Enumeration<String> enAttr = request.getAttributeNames(); 
		while(enAttr.hasMoreElements())
		{
			inputName = (String)enAttr.nextElement();
			inputValue = request.getAttribute(inputName).toString();
			jsonBuilder.add(inputName, inputValue);
			inputCount +=1;
		}
		
		String returnValue = jsonBuilder.build().toString(););
		*/
		
		return returnValue;
	}
	
	
}
