package com.warpww.web.servlet.ux;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AuthMod;

/** ***************************************************************************
 * The landing servlet is the default first page for all visitors.<br> 
 * 
 * <b>landing</b> contains the main menu, as do all pages, and brief descriptions 
 * of featured products with links to the product pages.
 * 
 * @author Warp Worldwide, LLC
 * @version 2.0
 * @since 2018.06.30
 *
* *************************************************************************** */
// TODO: Actions: Incomplete, should be refactored or removed. Medium Priority, but small to deal with. 

@WebServlet("/landing")
public class landing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * <b>landing</b> class constructor. 
     * @see HttpServlet#HttpServlet()
     */
    public landing() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Check to see if this page was loaded after a sign in or sign out, authorize accordingly.
		AuthMod a;
		if(null == request.getAttribute("signin_out")) {
			a = new AuthMod(request, response);
			a.authenticate();	
		} else if(request.getAttribute("signin_out").toString() == "in") {
			a = new AuthMod(request, response, AuthMod.Sign.in);
		} else {
			a = new AuthMod(request, response, AuthMod.Sign.out);
		}
		
		request.getRequestDispatcher("/WEB-INF/landing.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}


}
