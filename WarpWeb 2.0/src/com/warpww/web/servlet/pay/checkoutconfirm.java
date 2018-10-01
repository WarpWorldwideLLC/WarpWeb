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
import com.warpww.ell.ellutil;
import com.warpww.sec.AuthMod;
import com.warpww.sec.Hsx;
import com.warpww.util.Util;

/**
 * Servlet implementation class checkoutconfirm
 */
@WebServlet("/checkoutconfirm")
public class checkoutconfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkoutconfirm() {
        super();
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
		AuthMod authmod = new AuthMod(request, response);
		if(authmod.authenticate(request, response)) {
			memberID = Integer.parseInt(request.getAttribute("verifyToken_MemberID").toString());
			authTime = request.getAttribute("verifyToken_CreateTime").toString();
			authenticated = true;
		} else {
			authenticated = false;
			memberID = 0;	
		}

		// And retrieve the ShoppingCart
		Util.getShoppingCart(request, response, memberID, false, Util.CartContents.Pending);
		
		int totalCost = 0;		
		
		
		System.out.println(request.getAttribute("ShoppingCartTotalCost").toString());
		
		if (Util.isAttribute(request, "ShoppingCartTotalCost")) {
			String editType = request.getAttribute("ShoppingCartTotalCost").toString();
			if(editType!=null && !editType.isEmpty()){
				totalCost = Integer.parseInt(request.getAttribute("ShoppingCartTotalCost").toString());
				System.out.println("checkoutconfirm Found Param.");
			}

	    }

		
		if(request.getParameter("confirmPayment") != null) {
			String customerId = addToCustomer(request, request.getParameter("paymentSourceId"), request.getParameter("email-address"));
			processPayment(request, request.getParameter("paymentSourceId"), customerId, totalCost);
			/*
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.out.println("Payment Source ID: " + request.getParameter("paymentSourceId"));
			System.out.println("Email Address: " + request.getParameter("email-address"));
			System.out.println("Customer ID: " + customerId);
			*/
			Util.markCartSold(request, response, memberID);
			Util.setMemberSolution(request, response, memberID);
			
			/* ****************************************************************/
			/* ****************************************************************/
			/* Lingo Logic                                                    */
			/* After MemberSolution is complete, create the Lingo user account,
			 * Assing a license, and update the MemberSolution record.
			 * 
			 * Loop through all the existing EntitySolution records, assigning licenses
			 *  to each. ELL's API will reject duplicate requests for a single user. 
			 *  
			 *  It's not efficient, but it will work until it can be refactored.
			 * 
			 */
			/* ****************************************************************/
			/* ****************************************************************/
			ellutil eu = new ellutil(request);
			eu.getMemberDataFromDb(memberID, request, response);
			eu.createNewUser();
			eu.addMemberEllUserId(memberID, request, response);
			if(eu.ellResponseStatus.equals("0")) {
				eu.addLicenseToAllSolutionsForMember(request, response, memberID);
			}
			eu.setEllSolutionSso(memberID, request, response);
			
			
			/* ****************************************************************/
			/* ****************************************************************/
			
			request.getRequestDispatcher("checkoutreceipt").forward(request, response);
			
		} else if(request.getParameter("source") != null) {
		
			String customerId = addToCustomer(request, request.getParameter("source"), request.getParameter("email-address"));
			processPayment(request, request.getParameter("source"), customerId, totalCost);
			/*
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.out.println("Payment Source ID: " + request.getParameter("paymentSourceId"));
			System.out.println("Email Address: " + request.getParameter("email-address"));
			System.out.println("Customer ID: " + customerId);
			*/
			Util.markCartSold(request, response, memberID);
			Util.setMemberSolution(request, response, memberID);
			
			/* ****************************************************************/
			/* ****************************************************************/
			/* Lingo Logic                                                    */
			/* After MemberSolution is complete, create the Lingo user account,
			 * Assing a license, and update the MemberSolution record.
			 * 
			 * Loop through all the existing EntitySolution records, assigning licenses
			 *  to each. ELL's API will reject duplicate requests for a single user. 
			 *  
			 *  It's not efficient, but it will work until it can be refactored.
			 * 
			 */
			/* ****************************************************************/
			/* ****************************************************************/
			ellutil eu = new ellutil(request);
			eu.getMemberDataFromDb(memberID, request, response);
			eu.createNewUser();
			eu.addMemberEllUserId(memberID, request, response);
			if(eu.ellResponseStatus.equals("0")) {
				eu.addLicenseToAllSolutionsForMember(request, response, memberID);
			}
			eu.setEllSolutionSso(memberID, request, response);
			
			request.getRequestDispatcher("checkoutreceipt").forward(request, response);
			
			
		} else {
			request.getRequestDispatcher("/WEB-INF/checkoutconfirm.jsp").forward(request, response);
		}
		
		// request.setAttribute("stripeSourceId", request.getAttribute("stripeSourceId"));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	
	}
	
	protected String addToCustomer(HttpServletRequest request, String sourceId, String emailAddress) {
		String returnValue = null;
		
		Hsx configW = (Hsx) request.getServletContext().getAttribute("configW");
		
		Stripe.apiKey = configW.getStripeSecretKey();

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
	
	protected String processPayment(HttpServletRequest request, String sourceId, String customerId, int paymentAmount) { 
		String returnValue = null;
		
		// Set your secret key: remember to change this to your live secret key in production
		// See your keys here: https://dashboard.stripe.com/account/apikeys
		
		Hsx configW = (Hsx) request.getServletContext().getAttribute("configW");
		Stripe.apiKey = configW.getStripeSecretKey();

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
