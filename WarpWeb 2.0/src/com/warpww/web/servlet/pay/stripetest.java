package com.warpww.web.servlet.pay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.stripe.model.Customer;

/**
 * Servlet implementation class stripetest
 */
@WebServlet("/stripetest")
public class stripetest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public stripetest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String customerId = addToCustomer("", "john.arp@warpww.com");
		System.out.println(customerId);
		processPayment("", customerId, 138800);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected String addToCustomer(String sourceId, String emailAddress) {
		String returnValue = null;
		
		Stripe.apiKey = "";

		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("email", emailAddress);
		customerParams.put("source", sourceId);

		try {
			Customer customer = Customer.create(customerParams);
			returnValue = customer.getId();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		
		
		return returnValue;
	}
	
	protected String processPayment(String sourceId, String customerId, int paymentAmount) { 
		String returnValue = null;
		
		// Set your secret key: remember to change this to your live secret key in production
		// See your keys here: https://dashboard.stripe.com/account/apikeys
		
		Stripe.apiKey = "";

		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("amount", paymentAmount);
		chargeParams.put("currency", "usd");
		chargeParams.put("customer", customerId);
		chargeParams.put("source", sourceId);


		System.out.println("checkoutconfirm ChargeParams");
		System.out.println("  amount: " + paymentAmount);
		System.out.println("  currency: usd");
		System.out.println("  customer: " + customerId);
		System.out.println("  source: " + sourceId);
		
		try {
			Charge charge = Charge.create(chargeParams);
			System.out.println("Charge Status: " + charge.getStatus());
		} catch (AuthenticationException  | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			
			System.out.println("Message: " + e.getMessage());

		}
		
		return returnValue;
	}
	
}
