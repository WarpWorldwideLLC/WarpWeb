package com.warpww.web.servlet.pay;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.warpww.sec.AuthMod;
import com.warpww.sec.Hsx;
import com.warpww.util.Util;

import sun.security.util.Debug;


/**
 * Servlet implementation class checkout
 */
@WebServlet("/checkout_alipay")
public class checkout_alipay extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkout_alipay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set PageName & System Mode
		String uri = request.getRequestURI();
		String pageName = uri.substring(uri.lastIndexOf("/")+1);
		request.setAttribute("pageName", pageName);
		
		// Set return URL for stripe.Setting it dynamically allows it to work in all environments, and regardless of the URL used to get to the site.
		String stripeReturnUrl = request.getRequestURL().toString();
		stripeReturnUrl = stripeReturnUrl.replaceAll("checkout_alipay", "checkoutconfirm");
		request.setAttribute("stripe_return_url", stripeReturnUrl);
		
		int memberID = 0;
		String authTime = null;
		boolean authenticated = false;
		
		// Authenticate the User via Cookie; populate memberID and authTime fields.
		AuthMod authmod = new AuthMod(request, response);
		if(authmod.authenticate(request, response)) {
			memberID = Integer.parseInt(request.getAttribute("verifyToken_MemberID").toString());
			authTime = request.getAttribute("verifyToken_CreateTime").toString();
			authenticated = true;
		} else {
			authenticated = false;
			memberID = 0;	
		}
		
		Hsx configW = (Hsx) request.getServletContext().getAttribute("configW");
		if(authenticated) {
			
			// determine if a remove button was pressed
			String buttonRemove = request.getParameter("Remove");
			if(buttonRemove != null) {
				Util.removeSolutionFromCart(request, response, memberID, Integer.parseInt(buttonRemove));
			}
			
			// Public Keys
			// hsc hscObject = new hsc();
			
			
			request.setAttribute("paymentPublicKey", configW.getStripePublicKey());
			
			// And retrieve the ShoppingCart
			Util.getShoppingCart(request, response, memberID, false, Util.CartContents.Pending);
			
			// After the user confirms payment, forward to the checkout confirmation screen. 
			// Check to see if there is a Stripe Source ID
			String sourceTest = request.getParameter("stripeSourceId");
			if(sourceTest != null && !sourceTest.isEmpty()) {
				

				response.sendRedirect(request.getParameter("authURL").toString());
							
			} else {
				
				request.getRequestDispatcher("/WEB-INF/checkout_alipay.jsp").forward(request, response);
			}

					
		} else {
			
			// hsc configW = new hsc();
			
			Properties prop = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();           
			InputStream stream = loader.getResourceAsStream(configW.getResourceFileName());
			prop.load(stream);
		
			
			String notLoggedIn = prop.getProperty("checkout.not_logged_in");
			
			request.setAttribute("displayCart", "<p style=\"color:red\"><b>" + notLoggedIn + "</b></p>");
			request.getRequestDispatcher("/WEB-INF/checkout_alipay.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("checkout_pb", 1);

		doGet(request, response);
	}
			
	private String genStripeScriptSource(HttpServletRequest request) {
		String returnValue = "";
		
		
		
		return returnValue;
	}
	
	private String validateParameterField(HttpServletRequest request, String parameterName, String inputMessage) {

		String returnValue = inputMessage;
		if(request.getParameter(parameterName) + "" == "") {
			if(returnValue == "") {returnValue += ", ";}
			returnValue += "Traveler Name";
		}
		
		return returnValue;
	}
	
	// Sources Payment Processing via Stripe Sources
	private boolean processPayment(HttpServletRequest request, HttpServletResponse response) {
		boolean returnValue = false;
		
		//***********************************************************************************
		// Set your secret key: remember to change this to your live secret key in production
		// See your keys here: https://dashboard.stripe.com/account/apikeys
		//***********************************************************************************
		Hsx configW = (Hsx) request.getServletContext().getAttribute("configW");
		Stripe.apiKey = configW.getStripeSecretKey();
		
		
		// Token is created using Checkout or Elements!
		// Get the payment token ID submitted by the form:
		String token = request.getParameter("stripeToken");

		// Charge the user's card:
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", request.getParameter("paymentAmount"));
		params.put("currency", "usd");
		params.put("description", request.getParameter("paymentDescription"));
		params.put("source", token);
		
		//Meta-data
		Map<String, String> initialMetadata = new HashMap<String, String>();
		String metadata = request.getParameter("travelerEMailAddress") + "; " + request.getParameter("travelerPrimaryPhoneNumber") + "; " + request.getParameter("travelerAlternatePhoneNumber");
		
		initialMetadata.put("metadata", metadata);
		params.put("metadata", initialMetadata);

		// Create the charge and process the payment. 
		Charge charge = new Charge();
		try {
			charge = Charge.create(params);
			returnValue = true;
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
				| APIException e) {
			e.printStackTrace();
		}
		
		
		
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream(configW.getResourceFileName());
		try {
			prop.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		String paymentSuccess = prop.getProperty("checkout.payment_successful");
		String paymentFailure = prop.getProperty("checkout.payment_failed");
		
		if(returnValue == true) {
			System.out.println("Payment Succeeded!");
			request.setAttribute("paymentMessage", paymentSuccess);
			System.out.println("Charge ID: " + charge.getId());
			
		} else {
			System.out.println("Payment Failed!");
			request.setAttribute("paymentMessage", paymentFailure);

			System.out.println("Charge ID: " + charge.getId());
			System.out.println("Failure Code: " + charge.getFailureCode());
			System.out.println("Failure Message: " + charge.getFailureMessage());
		}
		
		return returnValue;
	}

	/* ********************************************************************************************** */
	/* ********************************************************************************************** */
	/* ********************************************************************************************** */
	
	private boolean validateRegistration(HttpServletRequest request, HttpServletResponse response) {
		boolean returnValue = false;
		String validateRegistrationMessage = "";

		
		validateParameterField(request, "travelerName", validateRegistrationMessage);

		validateParameterField(request, "parentName", validateRegistrationMessage);

		validateParameterField(request, "travelerAddress", validateRegistrationMessage);

		validateParameterField(request, "travelerPrimaryPhoneNumber", validateRegistrationMessage);

		validateParameterField(request, "travelerAlternatePhoneNumber", validateRegistrationMessage);

		validateParameterField(request, "travelerEMailAddress", validateRegistrationMessage);

		validateParameterField(request, "travelerGender", validateRegistrationMessage);
				
		validateParameterField(request, "travelerDateOfBirth", validateRegistrationMessage);
				
		validateParameterField(request, "travelerComments", validateRegistrationMessage);
		
		validateParameterField(request, "travelerComments", validateRegistrationMessage);
				
		

		validateParameterField(request, "paymentAmountText", validateRegistrationMessage);
				
		validateParameterField(request, "paymentAmount", validateRegistrationMessage);
				
		validateParameterField(request, "travelerName", validateRegistrationMessage);
				
		validateParameterField(request, "paymentNotes", validateRegistrationMessage);
		
		request.setAttribute("validateRegistrationMessage", validateRegistrationMessage);
	
		return returnValue;
	}
	
	private String genStripeScript (HttpServletRequest request) {
		String returnValue = "";
		
		returnValue = "<script \n"
		  + "src=\"https://checkout.stripe.com/checkout.js\" class=\"stripe-button\"\n"
		  + "data-key='" + request.getAttribute("paymentPublicKey") + "'\n"
		  + "data-name=\"WARP Worldwide, LLC\"\n"
		  + "data-image=\"https://stripe.com/img/documentation/checkout/marketplace.png\"\n"
		  + "data-locale=\"auto\"\n"
    		  + "data-allow-remember-me=\"false\"\n"
    		  + "data-zip-code=\"true\"\n"
    		  + "data-billing-address=\"true\"\n"
    		  + "\n"
    		  + "data-amount='" + request.getAttribute("paymentAmount") + "'\n"
    		  + "data-email='" + request.getAttribute("emailAddress") + "'\n"
    		  + "data-description='" + request.getAttribute("paymentDescription") + "'\n"
    		  + ">\n"
    		  + "</script>\n";
		
		return returnValue;
	}
	
	private boolean saveCampData(HttpServletRequest request, HttpServletResponse response) {
		boolean returnValue = false;
		try {
			
			// Create the command JSON.
			String json = Json.createObjectBuilder()
					 .add("Command", "SaveCampData")
					 .add("AuID", 1)
					 .add("IuID", 1)
					 .add("travelerName", request.getParameter("travelerName"))
					 .add("parentName", request.getParameter("parentName"))
					 .add("travelerAddress", request.getParameter("travelerAddress"))
					 .add("travelerPrimaryPhoneNumber",  request.getParameter("travelerPrimaryPhoneNumber"))
					 .add("travelerAlternatePhoneNumber",  request.getParameter("travelerAlternatePhoneNumber"))
					 .add("travelerEMailAddress",  request.getParameter("travelerEMailAddress"))
					 .add("travelerGender",  request.getParameter("travelerGender"))
					 .add("travelerDateOfBirth",  request.getParameter("travelerDateOfBirth"))
					 .add("travelerComments",  request.getParameter("travelerComments"))
					 .add("paymentType",  request.getParameter("paymentType"))
					 .add("paymentAmountText",  request.getParameter("paymentAmountText"))
					 .add("paymentAmount",  request.getParameter("paymentAmount"))
					 .add("paymentDescription",  request.getParameter("paymentDescription"))
					 .add("paymentNotes",  request.getParameter("paymentNotes"))
					 
					 .build()
					 .toString(); 		
			
			/*
			All request parameters:
	
				Parameter Name - stripeToken, Value - tok_1BkGAZDm8rfcoBsJt8IWbrxr
				Parameter Name - stripeTokenType, Value - card
				Parameter Name - stripeEmail, Value - john.arp@warpww.com
				Parameter Name - stripeBillingName, Value - John Arp
				Parameter Name - stripeBillingAddressCountry, Value - United States
				Parameter Name - stripeBillingAddressCountryCode, Value - US
				Parameter Name - stripeBillingAddressZip, Value - 9999
				Parameter Name - stripeBillingAddressLine1, Value - steeet
				Parameter Name - stripeBillingAddressCity, Value - coty
			*/


			String jsonParms = "";
		
			jsonParms = json;
			request.setAttribute("CommandText", jsonParms);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dbProcess");
			dispatcher.include(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
			
					
		return returnValue;
	}
	

}
