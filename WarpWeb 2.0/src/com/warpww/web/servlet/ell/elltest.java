package com.warpww.web.servlet.ell;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.ell.ellutil;
import com.warpww.sec.DES;

/**
 * Servlet implementation class elltest
 */
@WebServlet("/elltest")
public class elltest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public elltest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 

		try {
			
			ellutil eu = new ellutil();
			
			Date date = new Date();
			String formattedDate = new SimpleDateFormat("ssSSS").format(date);
			System.out.println(formattedDate);
			
			// Create SSO 
			
			String ellUserID = "229478";
			String ssoString = eu.CreateSSO(ellUserID);
			System.out.println("SSO URI for " + ellUserID + " : " + ssoString);
			
					
			
			// Create New User
			/*
			String newUserId = "";
			int memberID = 2;
			
			// Create ELL Account     
			eu.getMemberDataFromDb(memberID, request, response);
			eu.createNewUser();
			eu.addMemberEllUserId(memberID, request, response);
			if(eu.ellResponseStatus.equals("0")) {
				eu.addLicenseToAllSolutionsForMember(request, response, memberID);
			}
			*/
			
			
			/*
			eu.setEllUserID("Warp" + formattedDate);
			eu.setPassword("abcdef");
			eu.setEmail("warp" + formattedDate + "@ansebbian.com");
			eu.setFirstName("Johnny");
			eu.setLastName("BeGoode");
			eu.setBirthDate("2017-01-01");
			eu.setLanguage("english");
			eu.setCountry("China");
			
			eu.createNewUser();
			*/
			
			// Add License to User
			// eu.assignLicense("229466", "1551");
			
			
			
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
