package com.warpww.web.servlet.ux;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AuthMod;
import com.warpww.util.PageElements;

/**
 * Servlet implementation class landing
 */
@WebServlet("/landing")
public class landing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
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
	
		PageElements p = new PageElements();
		request.setAttribute("jspPageDirectives", p.getJspPageDirectives());
		//request.setAttribute("HtmlBodyHeader", p.getHtmlBodyHeader());
		request.setAttribute("testValue", "Test Valeu From Servlet.");
		
		request.getRequestDispatcher("/WEB-INF/landing.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}


}
