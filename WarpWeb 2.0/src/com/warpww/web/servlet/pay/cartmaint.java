package com.warpww.web.servlet.pay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AuthMod;
import com.warpww.sec.Hsx;
import com.warpww.util.Util;

/**
 * Servlet implementation class cartmaint
 */
@WebServlet("/cartmaint")
public class cartmaint extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public cartmaint() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthMod a = new AuthMod(request, response);
		a.authenticate();
		
		if(a.getAuthenticated()) {
			
			Util.markCartPending(request, response, a.getMemberID());
			// request.setAttribute("ReceiptNumber", Util.getJsonValueString(request.getAttribute("CommandResults").toString(),"ReceiptNumber"));
			
				
			// determine if a remove button was pressed
			String buttonRemove = request.getParameter("remove");
			if(buttonRemove != null) {
				Util.removeSolutionFromCart(request, response, a.getMemberID(), Integer.parseInt(buttonRemove));
			}
			
			// And retrieve the full ShoppingCart - on cartmaint, always pull the full cart. 
			Util.getShoppingCart(request, response, a.getMemberID()); 

			// Go back to shopping
			if(request.getParameter("continueShopping") != null) { 
				request.getRequestDispatcher("landing").forward(request, response);
			
			// Proceed to payment
			} else if(request.getParameter("completePurchase") != null) {
				
				// Determine  Payment Method
				
				switch(request.getParameter("payment").toString()){
					case "payment-ach":
						
						break;
					
					case "payment-card":
						request.getRequestDispatcher("checkout_creditcard").forward(request, response); 
						break;
					
					case "payment-alipay":
						request.getRequestDispatcher("checkout_alipay").forward(request, response); 
						break;
					
					default:
						break;
			
					
				}
				
			// Not sure what happened, go back to this page. 
			} else { 
				request.getRequestDispatcher("/WEB-INF/cartmaint.jsp").forward(request, response);
			}
			
		} else {
			
			
			Hsx configW = (Hsx) request.getServletContext().getAttribute("configW");
			
			Properties prop = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();           
			InputStream stream = loader.getResourceAsStream(configW.getResourceFileName());
			prop.load(stream);
		
			
			String notLoggedIn = "";
			notLoggedIn += prop.getProperty("checkout.not_logged_in_1");
			notLoggedIn += prop.getProperty("checkout.not_logged_in_2");
			notLoggedIn += prop.getProperty("checkout.not_logged_in_3");
			notLoggedIn += prop.getProperty("checkout.not_logged_in_4");
			notLoggedIn += prop.getProperty("checkout.not_logged_in_5");
			notLoggedIn += prop.getProperty("checkout.not_logged_in_6");
			notLoggedIn += prop.getProperty("checkout.not_logged_in_7");
			
			request.setAttribute("displayCart", "<p style=\"color:red\"><b>" + notLoggedIn + "</b></p>");
			request.getRequestDispatcher("/WEB-INF/cartmaint.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
