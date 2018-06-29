package com.warpww.web.servlet.ux;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.AuthMod;
import com.warpww.util.Util;

/**
 * Servlet implementation class mysolutions
 */
@WebServlet("/mysolutions")
public class mysolutions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mysolutions() {
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
			request.setAttribute("mySolutions", Util.getMemberSolution(request, response, a.getMemberID()));
		} else {
			request.setAttribute("mySolutions", "You must be logged in to view your solutions.");
		}
		
		if(request.getParameter("olcCmd") != null) {
			try {
				String httpDest = request.getParameter("olcCmd").toString();
				Util.debugPrint(true, "olcCmd Value; " , httpDest);
				
				if(httpDest.startsWith("LGO:")) {
					response.sendRedirect(httpDest.replaceAll("LGO:", ""));
				} else {
					Util.getMemberInfo(request, response, a.getMemberID());
					String s = Util.sendGetOLC(a.getMemberID(), request.getAttribute("MemberName").toString(), request.getAttribute("MemberFirstName").toString(), request.getAttribute("MemberLastName").toString(), request.getAttribute("MemberEmail").toString(), request.getParameter("olcCmd").toString());
					response.getWriter().append(s).append(request.getContextPath());
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { 
			request.getRequestDispatcher("/WEB-INF/mysolutions.jsp").forward(request, response);
		}
		
		// request.getRequestDispatcher("/WEB-INF/mysolutions.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
