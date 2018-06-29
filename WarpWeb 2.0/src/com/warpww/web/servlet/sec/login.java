package com.warpww.web.servlet.sec;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AuthMod;
import com.warpww.sec.Password;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		AuthMod a = new AuthMod(request, response);
		a.authenticate();
			
		String forwardServlet = "landing";
		if(validate(request, response)) {
			request.setAttribute("signin_out", "in");
			request.getRequestDispatcher(forwardServlet).forward(request, response);
			
		} else { 
			request.getRequestDispatcher(forwardServlet).forward(request, response);
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	protected boolean validate(HttpServletRequest request, HttpServletResponse response)
	{
		boolean validated = false;
		
		// Ensure member name and passphrase were typed, hash passphrase before continuing. 
		try {
			if(!request.getParameter("memberName").trim().isEmpty() && request.getParameter("memberName").trim() != null )
			{
				if(!request.getParameter("passPhrase").trim().isEmpty() && request.getParameter("passPhrase").trim() != null)
				{ 
					AuthMod authmod = new AuthMod();
					String passphraseHash = Password.createHash(request.getParameter("passPhrase"));
					request.setAttribute("passphraseHash", passphraseHash);
					validated = authmod.validateSignon(request, response);
				}
			}
			
		} catch (Exception ex)
		{
			validated = false;
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
		
		return validated;
		/* 
		try {		
			if(validated) {
	
					request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
				
				
			} else {
				request.getRequestDispatcher("/WEB-INF/landing2.jsp").forward(request, response);
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
