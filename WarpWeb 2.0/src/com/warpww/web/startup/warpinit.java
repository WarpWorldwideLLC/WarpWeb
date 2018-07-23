package com.warpww.web.startup;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.Hsx;
import com.warpww.util.Util;

/**
 * Servlet implementation class warpinit
 */
@WebServlet("/warpinit")
public class warpinit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public warpinit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.debugPrint(true, "warpinit.doGet:", "Got Here.");
		
		String initKey = this.getServletContext().getInitParameter("WarpInit").toString();
		Hsx configW = new Hsx();
		configW.LoadFromKey(initKey);
		
		
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.debugPrint(true, "warpinit.doPost:", "Got Here.");
		doGet(request, response);
	}

}
