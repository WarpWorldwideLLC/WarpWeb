package com.warpww.web.servlet.ux;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.json.Json;


import javax.servlet.RequestDispatcher;

import com.warpww.sec.AuthMod;
import com.warpww.sec.Password;
import com.warpww.util.*;
import com.captcha.botdetect.web.servlet.Captcha;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
@MultipartConfig
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
    }

    protected String actionMode;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if(null == request.getAttribute("ErrorMessage")) {
			request.setAttribute("ErrorMessage", "");
		}
		
		AuthMod a = new AuthMod(request, response);
		a.authenticate();
		
		
		// Process the request
		setRequestState(request, response);
		
		request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		AuthMod a = new AuthMod(request, response);
		a.authenticate();
		
		
		// Clear any existing error messages.
		Util.clearErrorMessage(request);
		
		// Check the Captcha first. 
		Captcha captcha = Captcha.load(request, "exampleCaptcha");
		// validate the Captcha to check we're not dealing with a bot
		boolean isHuman = captcha.validate(request.getParameter("captchaCode"));
		if (isHuman) {  // Captcha validation passed
			
			
			// Process the request (registration)		
			try 
			{
				setRequestState(request, response);
				processRequest(request, response);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}

			String jsonResults = request.getAttribute("CommandResults").toString();
			
			System.out.println("CommandResults: " + jsonResults);
			System.out.println("MemberName: " + Util.getJsonValue(jsonResults, "MemberName"));
			System.out.println("EmailAddress: " + Util.getJsonValue(jsonResults, "EmailAddress"));
			
			switch(Util.getJsonValue(jsonResults, "ProcStatus")) {
				case "SUCCESS":
					request.setAttribute("MemberName", Util.getJsonValue(jsonResults, "MemberName"));
					request.setAttribute("EmailAddress", Util.getJsonValue(jsonResults, "EmailAddress"));		
					
					
					request.getRequestDispatcher("/WEB-INF/registrationconfirmation.jsp").forward(request, response);
					break;
					
				case "ERROR":
					System.out.println("MemberName: " + Util.getJsonValue(jsonResults, "MemberName"));
					System.out.println("EmailAddress: " + Util.getJsonValue(jsonResults, "EmailAddress"));
					
					request.setAttribute("MemberName", "");
					request.setAttribute("EmailAddress", "");
		
					Util.setErrorMessage(request);	
					
					request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
					break; 
					
				default: 
					request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
					break;	
			}	
			
			
			// Pass control to RegistrationConfirmation
			//request.getRequestDispatcher("/registrationconfirmation").forward(request, response);
			
		} else {  // Captcha validation failed, show error message
		 
			request.setAttribute("ErrorMessage", "Captcha Validation Failed.");
			request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
		}
		
	}

	public void setRequestState(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		
		// Determine which button was pressed. 
		Map params = request.getParameterMap();
	    Iterator i = params.keySet().iterator();
	    actionMode = "None";
	    while ( i.hasNext() )
		{
		    String key = (String) i.next();
		    String value = ((String[]) params.get( key ))[ 0 ];
		    
			if(key.substring(0, 3).contains("btn"))
			{
				actionMode = value;
		    	}
		    
		}
	    			        
	}
	
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//Hash the password. 
		String passphraseHash = Password.createHash(request.getParameter("passPhrase1"));
		
		// Create the command JSON.
		String json = Json.createObjectBuilder()
				 .add("Command", "RegisterMember")
				 .add("AuID", 1)
				 .add("IuID", 1)
				 .add("MemberName", request.getParameter("memberName"))
				 .add("EmailAddress", request.getParameter("emailAddress"))
				 .add("PassphraseHash", passphraseHash)
				 .add("PhoneNumber", request.getParameter("phoneNumber"))
				 .add("FirstName", request.getParameter("firstName"))
				 .add("LastName", request.getParameter("lastName"))
				 .add("BirthDate", request.getParameter("birthDate"))
				 .add("CountryID", request.getParameter("countrySelector"))
				 .build()
				 .toString(); 		
		
		if(actionMode == null)
		{
			actionMode = "NullAction";
		}
		
		switch(actionMode)
		{
		case "native":
			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);
			
			
			break;
		default: 
			
			break;
		}
		
		
	}
	
}
