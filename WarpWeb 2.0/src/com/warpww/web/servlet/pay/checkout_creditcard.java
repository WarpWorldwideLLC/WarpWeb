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
import com.warpww.sec.hsc;
import com.warpww.util.Util;

import sun.security.util.Debug;


/**
 * Servlet implementation class checkout
 */
@WebServlet("/checkout_creditcard")
public class checkout_creditcard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkout_creditcard() {
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
		
		int memberID = 0;
		String authTime = null;
		boolean authenticated = false;
		
		// Authenticate the User via Cookie; populate memberID and authTime fields.
		AuthMod authmod = new AuthMod();
		if(authmod.authenticate(request, response)) {
			memberID = Integer.parseInt(request.getAttribute("verifyToken_MemberID").toString());
			authTime = request.getAttribute("verifyToken_CreateTime").toString();
			authenticated = true;
		} else {
			authenticated = false;
			memberID = 0;	
		}
		
		if(authenticated) {
			
			// determine if a remove button was pressed
			String buttonRemove = request.getParameter("Remove");
			if(buttonRemove != null) {
				Util.removeSolutionFromCart(request, response, memberID, Integer.parseInt(buttonRemove));
			}
			
			// Public Keys
			hsc hscObject = new hsc();
			request.setAttribute("paymentPublicKey", hscObject.pk_stripe);
			
			// And retrieve the ShoppingCart
			Util.getShoppingCart(request, response, memberID, false, Util.CartContents.Pending);
			
			// After the user confirms payment, forward to the checkout confirmation screen. 
			// Check to see if there is a Stripe Source ID
			String tokenTest = request.getParameter("stripeSourceId");
			if(tokenTest != null && !tokenTest.isEmpty()) {
				System.out.println("Stripe Source Id Found");
	
				request.getRequestDispatcher("checkoutconfirm?vid=" + request.getParameter("stripeSourceId")).forward(request, response);
							
			} else {
				System.out.println("Stripe Source Id Not Found");
				request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
			}

					
		} else {
			
			hsc configW = new hsc();
			
			Properties prop = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();           
			InputStream stream = loader.getResourceAsStream(configW.resourceFile);
			prop.load(stream);
		
			String notLoggedIn = prop.getProperty("checkout.not_logged_in");
			
			request.setAttribute("displayCart", "<p style=\"color:red\"><b>" + notLoggedIn + "</b></p>");
			request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("checkout_pb", 1);

		doGet(request, response);
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
		hsc hscObject = new hsc();
		Stripe.apiKey = hscObject.sk_stripe;
		
		
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
		
		if(returnValue == true) {
			System.out.println("Payment Succeeded!");
			request.setAttribute("paymentMessage", "Your payment was successful! Thank you, and we will be contacting you soon! Please email us at WarpCustomer@warpww.com if you have any questions before then.");
			System.out.println("Charge ID: " + charge.getId());
		} else {
			System.out.println("Payment Failed!");
			request.setAttribute("paymentMessage", "There was a problem processing your payment. Please contact us via the Contact Us link and we will be happy to help resolve any issues.");

			System.out.println("Charge ID: " + charge.getId());
			System.out.println("Failure Code: " + charge.getFailureCode());
			System.out.println("Failure Message: " + charge.getFailureMessage());
		}
		
		return returnValue;
	}

	/* ********************************************************************************************** */
	/* ********************************************************************************************** */
	/* ********************************************************************************************** */
	
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
	
}
