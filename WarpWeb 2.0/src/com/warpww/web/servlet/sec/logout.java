package com.warpww.web.servlet.sec;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AuthMod;
import com.warpww.sec.Hsx;


/**
 * Servlet implementation class logout
 */
@WebServlet("/logout")
public class logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthMod a = new AuthMod(request, response);
		a.authenticate();
		
		request.setAttribute("signin_out", "out");
		
		deleteCookie(request, response);
		
		String forwardServlet = "landing";
		request.getRequestDispatcher(forwardServlet).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	
	private boolean deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		
		boolean returnValue = false;
		
		// Set Cookie MaxAge to 0, deleting it when the browser processes the header.
		Hsx configW = (Hsx) request.getServletContext().getAttribute("configW");
		Cookie userCookie = new Cookie(configW.getCookieName(), "");
		userCookie.setMaxAge(0);      
		response.addCookie(userCookie);
		System.out.println("Authentication Cookie Deleted.");
		returnValue = true;
		
		return returnValue;
	}
	
	private boolean findCookie(HttpServletRequest request, HttpServletResponse response) {
		
		boolean returnValue = false;
		
		// Loop through available Cookes and find the correct one.
		String cookieValue = null;
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) 
		{
		    for(int i=0; i<cookies.length; i++) 
		    {
		    		
		        Cookie cookie = cookies[i];
		        
		        
		        Hsx configW = (Hsx) request.getServletContext().getAttribute("configW");
		        if (configW.getCookieName().equals(cookie.getName())) 
		        {
		        		/*
		            System.out.println("Domain: " + cookie.getDomain()); 
		            System.out.println("Comment: " + cookie.getComment());
		            System.out.println("MaxAge: " + cookie.getMaxAge());
		            System.out.println("Name: " + cookie.getName());
		            System.out.println("Path: " + cookie.getPath());
		            System.out.println("Value: " + cookie.getValue());
		            System.out.println("Cookie Name: " + cookie.getName());
		            */
		        		
		            cookieValue = cookie.getValue();
		            // System.out.println("Cookie Name: " + cookie.getName() + " [" + cookieValue + "]");
		            
		            /*
		            System.out.println("cookieValue: " + cookieValue);
		            System.out.println("Version: " + cookie.getVersion());
		            System.out.println("Class: " + cookie.getClass().toString());
		            System.out.println("Secure: " + cookie.getSecure());
		            */
		        }
		        
		        
		         returnValue = true;
		         if(returnValue) {
		        	 	//System.out.println(Instant.now().toString() + " " + request.getRequestURI() + " " + request.getMethod() + " authentication succeeded [" + cookieValue + "].");
		         } else {
		        	 	//System.out.println(Instant.now().toString() + " " + request.getRequestURI() + " " + request.getMethod() + " authentication failed [" + cookieValue + "].");
		         }
		    }
		}
		else
		{
		    returnValue = false;
		} 
		
		return returnValue;
	}
	
}
