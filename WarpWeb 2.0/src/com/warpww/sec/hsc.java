package com.warpww.sec;

//WARP High Security Class - not under source control. 
public class hsc {

	private final String ell_sk_test = "fkelJ4bD";
	private final String ell_sk_prod = "fJuWlfiB";
	public String ell_sk = null;
	
	private final String ell_clientid_test = "2544";
	private final String ell_clientid_prod = "3945";
	public String ell_clientid = null;
	
	public final String ell_ssouri_test = "https://sdb.elldevelopment.com";
	public final String ell_ssouri_prod = "http://sdb.ellcampus.com";
	public String ell_ssouri = null;
	
	private final String ell_apiuri_test = "https://api.elldevelopment.com";
	private final String ell_apiuri_prod = "https://api.ellcampus.com";
	public String ell_apiuri = null;
	
	public String pk_stripe = "pk_test_SVBFNF3t7mU3ycUFW8nEzENp";
	public String sk_stripe = "sk_test_STal0dCNUL1dUZ09CswwVe7E";
	
	// checkout_alipay201804.jsp Line 112
	public String stripe_return_url = "https://localhost:8080/com.warpww.web/checkoutconfirm201804";

	// public String jdbcURI = "jdbc:mysql://localhost:3306/WarpAdmin2017";
	public String jdbcURI = "";
	public String jdbcUser = "root";
	public String jdbcPassword = "62XYhC;erw;zZaCmZVzrFEwW";
     
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
	
	public hsc() {
		setTestCN();
	}
	
	public hsc(SystemMode modeValue) {
		
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
		stripe_return_url = "http://localhost:8080/com.warpww.web/checkoutconfirm201804";

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
		stripe_return_url = "http://localhost:8080/com.warpww.web/checkoutconfirm201804";

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
		stripe_return_url = "http://localhost:8080/com.warpww.web/checkoutconfirm201804";

	 
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
		stripe_return_url = "https://warpww.com/checkoutconfirm201804";
		

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
		
		systemMode = "Prod";		return returnValue;
	}
	
	public boolean setProdCN() {
		boolean returnValue = false;

		ell_sk = this.ell_sk_prod;
		ell_clientid = this.ell_clientid_prod;
		ell_apiuri = this.ell_apiuri_prod;
		ell_ssouri = this.ell_ssouri_prod;
		
		pk_stripe = "pk_live_9IU3u0bPEmnKRLb103pW4ILu";
		sk_stripe = "sk_live_R3mT2ILaX6p0OdO6Dk9cotz6";
		stripe_return_url = "https://warpww.cn/checkoutconfirm201804";
		

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
		
		systemMode = "Prod";		return returnValue;
	}
		
	
}
