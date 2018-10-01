package com.warpww.sec;

/** ***************************************************************************
 * hsc (High Security Class)  contains sensitive configuration information.
 * 
 * This class is <b>NOT</b> under source control because of the sensitivity
 * of the information it contains. Acces to production keys is restricted
 * to a small number of team members for security reasons.
 * 
 * Configuration information; should ultimately be in a config database 
 * rather than hard-coded in a config module. No methods, just accessors, 
 * mutators, and public variables. 
 * 
 * @author Warp Worldwide, LLC
 * @version 2.0
 * @since 2018.06.30
 *
* *************************************************************************** */
// TODO: Database values would need to be encrypted, key management would need to be determined.

public class hscX {

	
	//**********************************************************************
	// Private Variables
	//**********************************************************************
	private final String ell_sk_test = "fkelJ4bD";
	private final String ell_sk_prod = "fJuWlfiB";
	
	
	private final String ell_clientid_test = "2544";
	private final String ell_clientid_prod = "3945";
	
	
	private final String ell_ssouri_test = "https://sdb.elldevelopment.com";
	private final String ell_ssouri_prod = "http://sdb.ellcampus.com";
	
	private final String ell_apiuri_test = "https://api.elldevelopment.com";
	private final String ell_apiuri_prod = "https://api.ellcampus.com";
	
	public String pk_stripe_test = "pk_test_SVBFNF3t7mU3ycUFW8nEzENp";
	public String sk_stripe_test = "sk_test_STal0dCNUL1dUZ09CswwVe7E";
	
	public String pk_stripe_prod = "pk_live_9IU3u0bPEmnKRLb103pW4ILu";
	public String sk_stripe_prod = "sk_live_R3mT2ILaX6p0OdO6Dk9cotz6";
	
	public String pk_stripe = "";
	public String sk_stripe = "";
	
	private String resourceFile_chinese ="/com/warpww/web/i18n/warp.properties";
	private String resourceFile_english = "/com/warpww/web/i18n/warp-en-us.properties";
	

	// public String jdbcURI = "jdbc:mysql://localhost:3306/WarpAdmin2017";
	public String jdbcURI = "";
	public String jdbcUser = "root";
	public String jdbcPassword = "62XYhC;erw;zZaCmZVzrFEwW";
	
	//**********************************************************************
	// Public Variables
	//**********************************************************************
	public String ell_sk = null;
	public String ell_clientid = null;
	public String ell_ssouri = null;
	public String ell_apiuri = null;
	
	public String resourceFile = resourceFile_english;
	
     
 public String tokenSalt = "Action Comics #1, June 1939";
 public int tokenMemberPadding = 1100000; 		// Ensures a 6-byte hex value
	public int tokenExpirationDuration = 18000;  	// 5 hours
	public String cookieName = "com.warpww";
	public String cookieDomain = ""; // warpww.com  - 
	
	public boolean cookieSSL = false;
	
	public String currencySymbol = "$";
	
	public String systemMode = "Test";
	
	public String ellClientID = "0";
			
	public enum SystemMode {
	    TEST, TestCN, UAT, UatCN, PROD, ProdCN
	}
	
	public hscX() {
		setProdCN();
	}
	
	public hscX(SystemMode modeValue) {
		
		switch(modeValue) {
			case PROD:
				setProd();
				break;
				
			case ProdCN:
				setProdCN();
				break;
				
			case UAT:
				setUAT();
				break;
			case UatCN:
				setUatCN();
				break;
				
			case TEST:
				setTest();
				break;
				
			case TestCN:
				setTest();
				break;
				
			default:
				setTest();
				break;
		}
	}
	
	public boolean setTest() {
		boolean returnValue = false;
		
		ell_sk = this.ell_sk_test;
		ell_clientid = this.ell_clientid_test;
		ell_apiuri = this.ell_apiuri_test;
		ell_ssouri = this.ell_ssouri_test;
		
		pk_stripe = "pk_test_SVBFNF3t7mU3ycUFW8nEzENp";
		sk_stripe = "sk_test_STal0dCNUL1dUZ09CswwVe7E";

	    jdbcURI = "jdbc:mysql://localhost:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	    jdbcUser = "root";
	    jdbcPassword = "62XYhC;erw;zZaCmZVzrFEwW";
	        
	    tokenSalt = "Action Comics #1, June 1939";
	    tokenMemberPadding = 1100000; 		// Ensures a 6-byte hex value
		tokenExpirationDuration = 18000;  	// 5 hours
		cookieName = "com.warpww";
		cookieDomain = ""; // warpww.com  - 

		
		cookieSSL = false;
		
		ellClientID = "2544";
		
		currencySymbol = "$";
		
		systemMode = "Test";
		
		this.resourceFile = this.resourceFile_english;
						
		return returnValue;	
	}
	
	public boolean setTestCN() {
		boolean returnValue = false;
		
		ell_sk = this.ell_sk_test;
		ell_clientid = this.ell_clientid_test;
		ell_apiuri = this.ell_apiuri_test;
		ell_ssouri = this.ell_ssouri_test;
		
		pk_stripe = "pk_test_SVBFNF3t7mU3ycUFW8nEzENp";
		sk_stripe = "sk_test_STal0dCNUL1dUZ09CswwVe7E";

	    jdbcURI = "jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	    jdbcUser = "root";
	    jdbcPassword = "62XYhC;erw;zZaCmZVzrFEwW";
	        
	    tokenSalt = "Action Comics #1, June 1939";
	    tokenMemberPadding = 1100000; 		// Ensures a 6-byte hex value
		tokenExpirationDuration = 18000;  	// 5 hours
		cookieName = "cn.warpww";
		cookieDomain = ""; // warpww.com  - 
		
		cookieSSL = false;
		
		ellClientID = "2544";
		
		// currencySymbol = "¥";
		currencySymbol = "$";
		
		systemMode = "Test";
						
		this.resourceFile = this.resourceFile_chinese;
		
		return returnValue;	
	}
	
	public boolean setUAT() {
		boolean returnValue = false;
		
		ell_sk = this.ell_sk_test;
		ell_clientid = this.ell_clientid_test;
		ell_apiuri = this.ell_apiuri_test;
		ell_ssouri = this.ell_ssouri_test;
		
		pk_stripe = "pk_test_SVBFNF3t7mU3ycUFW8nEzENp";
		sk_stripe = "sk_test_STal0dCNUL1dUZ09CswwVe7E";

	 
	    jdbcURI = "jdbc:mysql://localhost:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	    jdbcUser = "warpAdmin";
	    jdbcPassword = "warpuatLive";
	        
	    tokenSalt = "Action Comics #1, June 1939";
	    tokenMemberPadding = 1100000; 		// Ensures a 6-byte hex value
		tokenExpirationDuration = 18000;  	// 5 hours
		cookieName = "com.warpww";
		cookieDomain = ""; // warpww.com  - 
		
		cookieSSL = false;
		
		ellClientID = "2544";
		
		currencySymbol = "$";
		
		systemMode = "UAT";
	
		return returnValue;
	}
	
	public boolean setUatCN() {
		boolean returnValue = false;
		
		ell_sk = this.ell_sk_test;
		ell_clientid = this.ell_clientid_test;
		ell_apiuri = this.ell_apiuri_test;
		ell_ssouri = this.ell_ssouri_test;
		
		this.resourceFile = this.resourceFile_chinese;
		
		return returnValue;
	}
	
	public boolean setProd() {
		boolean returnValue = false;

		ell_sk = this.ell_sk_prod;
		ell_clientid = this.ell_clientid_prod;
		ell_apiuri = this.ell_apiuri_prod;
		ell_ssouri = this.ell_ssouri_prod;
		
		pk_stripe = "pk_live_9IU3u0bPEmnKRLb103pW4ILu";
		sk_stripe = "sk_live_R3mT2ILaX6p0OdO6Dk9cotz6";
		

	    jdbcURI = "jdbc:mysql://singapore-db-1.cuwqizgjegfw.ap-southeast-1.rds.amazonaws.com:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	    jdbcUser = "warpdbm";
	    jdbcPassword = "warp-is-live-2018";
	   
	    tokenSalt = "Action Comics #1, June 1939";
	    tokenMemberPadding = 1100000; 		// Ensures a 6-byte hex value
		tokenExpirationDuration = 18000;  	// 5 hours
		cookieName = "com.warpww";
		cookieDomain = ""; // warpww.com  - 
		
		cookieSSL = false;
		
		ellClientID = "3945";
		
		currencySymbol = "$";
		
		systemMode = "Prod";		
		
		this.resourceFile = this.resourceFile_english;
		
		return returnValue;
	}
	
	public boolean setProdCN() {
		boolean returnValue = false;

		ell_sk = this.ell_sk_prod;
		ell_clientid = this.ell_clientid_prod;
		ell_apiuri = this.ell_apiuri_prod;
		ell_ssouri = this.ell_ssouri_prod;
		
		pk_stripe = "pk_live_9IU3u0bPEmnKRLb103pW4ILu";
		sk_stripe = "sk_live_R3mT2ILaX6p0OdO6Dk9cotz6";
		

	    jdbcURI = "jdbc:mysql://singapore-db-1.cuwqizgjegfw.ap-southeast-1.rds.amazonaws.com:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	    jdbcUser = "warpdbm";
	    jdbcPassword = "warp-is-live-2018";
	   
	    tokenSalt = "Action Comics #1, June 1939";
	    tokenMemberPadding = 1100000; 		// Ensures a 6-byte hex value
		tokenExpirationDuration = 18000;  	// 5 hours
		cookieName = "com.warpww";
		cookieDomain = ""; // warpww.com  - 
		
		cookieSSL = false;
		
		ellClientID = "3945";

		
		currencySymbol = "$";
		
		systemMode = "Prod";		
		
		this.resourceFile = this.resourceFile_chinese;
		
		return returnValue;
	}
		
	
}
