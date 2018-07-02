package com.warpww.web.servlet.ux;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AuthMod;
import com.warpww.util.Util;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		if(request.getAttribute("logTest") != null) {
			System.out.println("LogTest: " + request.getAttribute("logTest").toString());
		}
		
		AuthMod a = new AuthMod(request, response);
		a.authenticate();
		*/
		
		// Read Properties File
		//Properties prop = new Properties();
		//ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		//InputStream stream = loader.getResourceAsStream("/com.warpww.web/src/com/warpww/web/i18n/warp.properties");
		//prop.load(stream);
				
		
		java.util.Date dt = new java.util.Date();
		request.setAttribute("DateTime", dt.toString());
		
		// List all the headers with this request.
		String headerNameList = "";
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headerNameList += headerName + " = " + request.getHeader(headerName) + "\n";
		}
		request.setAttribute("headers", headerNameList);
		
		request.getRequestDispatcher("/WEB-INF/test.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
