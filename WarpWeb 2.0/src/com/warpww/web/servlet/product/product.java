package com.warpww.web.servlet.product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AuthMod;
import com.warpww.util.Util;

/**
 * Servlet implementation class product
 */
@WebServlet("/product")
public class product extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public product() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthMod a = new AuthMod(request, response);
		a.authenticate();
		
		String purchase_action = request.getParameter("purchase");
		if(purchase_action == null || purchase_action.isEmpty())
		{
			purchase_action = "-1";
		}
		


		if(Integer.parseInt(purchase_action) >= 0) {
			Util.addSolutionToCart(request, response, a.getMemberID(), Integer.parseInt(purchase_action));
			// request.getRequestDispatcher("checkout").forward(request, response);
			request.getRequestDispatcher("cartmaint").forward(request, response);
		} else {
			String productPage = request.getParameter("productPage") + "";
			if(productPage.trim().length() >0 ) {
				productPage = "/WEB-INF/" + request.getParameter("productPage").toString() + ".jsp";
			} else { 
				productPage = "/WEB-INF/landing.jsp";
			}
			request.getRequestDispatcher(productPage).forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
