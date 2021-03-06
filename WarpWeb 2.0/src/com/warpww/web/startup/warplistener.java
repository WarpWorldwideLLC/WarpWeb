package com.warpww.web.startup;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.warpww.sec.Hsx;
import com.warpww.util.Util;

/**
 * Application Lifecycle Listener implementation class warplistener
 *
 */
@WebListener
public class warplistener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public warplistener() {
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
        
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
		Util.debugPrint(true, "warplistener.contextInitialized", "");
		
		ServletContext sc = sce.getServletContext();
		
		String initKey = sc.getInitParameter("WarpInit").toString();
		Hsx configW = new Hsx();
		configW.LoadFromKey(initKey);
		
		sc.setAttribute("configW", configW);
		Hsx cw = (Hsx) sc.getAttribute("configW");
		Util.debugPrint(true, "JDBC_URI" , cw.getJdbcUri());
		
    }
	
}
