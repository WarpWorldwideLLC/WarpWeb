package com.warpww.web.servlet.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warpww.sec.hsc;
import com.warpww.util.Util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Servlet implementation class registerUserAccount
 */
@WebServlet("/dbProcess")
public class dbProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dbProcess() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		processCommand(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//String statusCode =  getClass().getName() + ".doGet()" + ".." + System.currentTimeMillis() + ";";
		//String statusCode2 = (String) request.getAttribute("ResultCode"); 
		//request.setAttribute("ResultCode", statusCode2);
		// System.out.println(System.getProperty("java.runtime.version"));
				
		processCommand(request, response);

	}
	
	private void processCommand(HttpServletRequest request, HttpServletResponse response) 
	{	
		//******************************************************************************
		// Debug Switch 
		//******************************************************************************
		boolean debugMode = false;
		
		String json = "";
		json = (String) request.getAttribute("CommandText");
		
		String spName = "";

		JsonReader reader = Json.createReader(new ByteArrayInputStream(json.getBytes()));
		JsonObject jsonst = reader.readObject();
        // spName = translateCommand("RegisterUserAccount");
		spName = translateCommand(jsonst.getString("Command"));
		
		if(debugMode) {
			System.out.println("spName: " + spName);
			System.out.println("Command: " + json);
		}
		
		// If the command is invalid,stop processing and return an error.
		if(spName == "Invalid_Command") {
			 request.setAttribute("CommandResults", "{\"MemberID\": -1, \"MemberName\": \"\", \"EmailAddress\": \"\", \"ProcStatus\": \"ERROR\", \"ProcMessage\": \"Invalid Command: '" + jsonst.getString("Command") + "'\"}");
			 /* {"MemberID": 43, "ProcStatus": "ERROR", "ProcMessage": "Invalid Command: '"+ jsonst.getString("Command") + "'"} */
			 return;
		}
		
		try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            // Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/WarpAdmin2017", "root", "62XYhC;erw;zZaCmZVzrFEwW");
            hsc hscObject = new hsc();
            Connection conn = DriverManager.getConnection(hscObject.jdbcURI, hscObject.jdbcUser, hscObject.jdbcPassword);   
            
            // System.out.println("dbprocess.processCommand:StoredProc: " + spName);
            CallableStatement cStmt = conn.prepareCall("{call " + spName + "(?)}");
            cStmt.setString(1, json);
            
            boolean hadResults = cStmt.execute();
            while (hadResults) 
            {
            		ResultSet rs = cStmt.getResultSet();
            		ResultSetMetaData rsmd = rs.getMetaData(); 
                String name = rsmd.getColumnName(1);

                while(rs.next())
                {
                 request.setAttribute("CommandResults", rs.getString(1));
                }
                hadResults = cStmt.getMoreResults();
            }      
            
            if(debugMode) {
            		System.out.println("Command Results: " + request.getAttribute("CommandResults"));
            }
 
        } catch (Exception e2) 
        {
            System.out.println(e2);
        }	
	}

	private String translateCommand(String command)
	{
			String spName = "foo";
	        switch (command) {
	            case "RegisterMember":  spName = "registerMember";
	                     break;
	            case "GeneratePasswordResetToken": spName = "generatePasswordResetToken";
	            		break;
	            case "GetMemberList": spName = "getMemberList";
	            		break;
	            case "ValidateSignon": spName = "validateSignon";
	            		break;
	            case "SaveCampData":  spName = "saveCampData";
                		break;
	            case "SaveStripeCampData":  spName = "saveStripeCampData";
                		break;
	            case "GetGreeting":  spName = "getGreeting";
        				break;
	            case "AddEntitySolution": spName = "addEntitySolytion";
	            		break;
	            case "AddMemberSolution": spName = "addMemberSolution";
	            		break;
	            case "AddSolutionToCart": spName = "addSolutionToCart";
	            		break;
	            case "ClearCart": spName="clearCart";
	            		break;
	            case "GetCart": spName = "getCart";
	            		break;
	            case "RemoveSolutionFromCart" : spName = "removeSolutionFromCart";
	            		break;
	            case "MarkCartPending" : spName = "markCartPending";
	            		break;
	            case "MarkCartSold" : spName = "markCartSold";
	            		break;
	            case "GetCartPendingItemsOnly" : spName = "getCartPendingItemsOnly";
        				break;
	            case "GetCartSoldItemsOnly" : spName = "getCartSoldItemsOnly";
        				break;
	            case "GetMemberSolution" : spName = "getMemberSolution";
        				break;
	            case "SetMemberSolution" : spName = "setMemberSolution";
        				break;
	            case "GetMemberInfo" : spName = "getMemberInfo";
        				break;
	            case "GetEllNewUserData" : spName = "getEllNewUserData";
        				break;
	            case "AddMemberEllUserID" : spName = "AddEntityEllUserId";
	            		break;
	            case "GetEllEntitySolutions" : spName = "getEllEntitySolutions";
        				break;
	            case "SetEllSolutionSso" : spName = "setEllSolutionSso";
        				break;
	            default: spName = "Invalid_Command";
	                break;
	        }
	        
	        return spName;
	}
	
	public void mapParamsOld(String titleText, HttpServletRequest request)
	{
		System.out.println("**** HTTP Request Data for" + titleText + " ****");
		/* 
		Map params = request.getParameterMap();
	    Iterator i = params.keySet().iterator();
	    while ( i.hasNext() )
		{
		    String key = (String) i.next();
		    String value = ((String[]) params.get( key ))[ 0 ];
		    
		    System.out.println(key + ": " + value);		    
		    
		}
	   */
		System.out.println("All request attributes");
		
		Enumeration enAttr = request.getAttributeNames(); 
		while(enAttr.hasMoreElements()){
		 String attributeName = (String)enAttr.nextElement();
		 System.out.println("Attribute Name - "+attributeName+", Value - "+(request.getAttribute(attributeName)).toString());
		}
		
		System.out.println("All request parameters:");
		
		Enumeration enParams = request.getParameterNames(); 
		while(enParams.hasMoreElements()){
		 String paramName = (String)enParams.nextElement();
		 System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
	
		
	}
	
}
