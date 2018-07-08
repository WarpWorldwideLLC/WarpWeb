package com.warpww.sec;
/** ***************************************************************************
 * Hsx (High Security eXtreme Class) -  sensitive configuration information.
 * 
 *   This class is <b>NOT</b> under source control because of the sensitivity
 *   of the information it contains. Access to production keys is restricted
 *   to a small number of team members for security reasons.
 * 
 *   Five pieces of data are committed to a JSON formatted string, encrypted and 
 *   stored in web.xml. The key to this data is stored in this program.
 *   
 *   The data is:
 *   	WarpSec URI	: 				WarpSec database location (JDBC)
 *   	WarpSec User: 				WarpSec database user id (JDBC)
 *   	WarpSec Password: 			WarpSec database password (JDBC)			
 *   	WarpSec Environment: 		WarpSec Environment
 *   	WarpSec Key:				Key to decode data in WarpSec
 *   	
 *   Ultimately, both keys should be moved to a key service/HSM for better 
 *   separation. 
 * 
 *   Once 
 * 
 * The data is ultimately stored as strings, and this is subject to a 
 * memory scraping attack. But as the data needs to be used as a string, there 
 * is no viable workaround to that. 
 * 
 * 
 * 
 * @author Warp Worldwide, LLC
 * @version 2.0
 * @since 2018.06.30
 *
* *************************************************************************** */

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Hsx {

	//**********************************************************************
	// Public Variables
	//**********************************************************************
	public enum SystemMode {
	    TEST, TestCN, UAT, UatCN, PROD, ProdCN
	}
	
	//**********************************************************************
	// Private Variables
	//**********************************************************************
	private final String ell_sk_test = "fkelJ4bD";
	private final String ell_sk_prod = "fJuWlfiB";
	private String ell_sk = null;
	
	private final String ell_clientid_test = "2544";
	private final String ell_clientid_prod = "3945";
	private String ell_clientid = null;
	
	private final String ell_ssouri_test = "https://sdb.elldevelopment.com";
	private final String ell_ssouri_prod = "http://sdb.ellcampus.com";
	private String ell_ssouri = null;
	
	private final String ell_apiuri_test = "https://api.elldevelopment.com";
	private final String ell_apiuri_prod = "https://api.ellcampus.com";
	private String ell_apiuri = null;
	
	private String pk_stripe_test = "pk_test_SVBFNF3t7mU3ycUFW8nEzENp";
	private String pk_stripe_prod = "pk_live_9IU3u0bPEmnKRLb103pW4ILu";
	private String pk_stripe = null;
	
	private String sk_stripe_test = "sk_test_STal0dCNUL1dUZ09CswwVe7E";
	private String sk_stripe_prod = "sk_live_R3mT2ILaX6p0OdO6Dk9cotz6";
	private String sk_stripe = null;
	
	private String resourceFile_chinese ="/com/warpww/web/i18n/warp.properties";
	private String resourceFile_english = "/com/warpww/web/i18n/warp-en-us.properties";
	private String resourceFile = null;

	// public String jdbcURI = "jdbc:mysql://localhost:3306/WarpAdmin2017";
	
	private String jdbcURI_test_us = "jdbc:mysql://localhost:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	private String jdbcUser_test_us = "root";
	private String jdbcPassword_test_us = "62XYhC;erw;zZaCmZVzrFEwW";
	
	private String jdbcURI_test_cn = "jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	private String jdbcUser_test_cn = "root";
	private String jdbcPassword_test_cn = "62XYhC;erw;zZaCmZVzrFEwW";
	
	private String jdbcURI_uat_us = "jdbc:mysql://localhost:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	private String jdbcUser_uat_us = "warpAdmin";
	private String jdbcPassword_uat_us = "warpuatLive";
	
	private String jdbcURI_prod_us = "jdbc:mysql://singapore-db-1.cuwqizgjegfw.ap-southeast-1.rds.amazonaws.com:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	private String jdbcUser_prod_us = "warpdbm";
	private String jdbcPassword_prod_us = "warp-is-live-2018";
   
	private String jdbcURI_prod_cn = "jdbc:mysql://singapore-db-1.cuwqizgjegfw.ap-southeast-1.rds.amazonaws.com:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8";
	private String jdbcUser_prod_cn = "warpdbm";
	private String jdbcPassword_prod_cn = "warp-is-live-2018";
	
	
	private String jdbcURI = null;
	private String jdbcUser = null;
	private String jdbcPassword = null;
	
	
	private String tokenSalt = "Action Comics #1, June 1939";
	private int tokenMemberPadding = 1100000; 		// Ensures a 6-byte hex value
	private int tokenExpirationDuration = 18000;  	// 5 hours
	public String cookieName = "com.warpww";
	private String cookieDomain = ""; // warpww.com  - 
	
	private boolean cookieSSL = false;
	
	private String currencySymbol = "$";
	
	private String systemMode = "Test";

	//**********************************************************************
	// Accessors and Mutators
	//**********************************************************************
	public String getEllSecretKey() {
		return this.ell_sk;
	}
	
	public String getEllClientID() {
		return this.ell_clientid;
	}
	
	public String getEllSsoUri() {
		return this.ell_ssouri;
	}
	
	public String getEllApiUri() {
		return this.ell_apiuri;
	}

	public String getStripeSecretKey() {
		return this.sk_stripe;
	}
	
	public String getStripePublicKey() {
		return this.pk_stripe;
	}
	
	public String getResourceFileName() {
		return this.resourceFile;
	}
	
	
	public String getJdbcUri() {
		return this.jdbcURI;
	}
	
	public String getJdbcUser() {
		return this.jdbcUser;
	}
	
	public String getJdbcPassword() {
		return this.jdbcPassword;
	}
	
	public String getTokenSalt() {
		return this.tokenSalt;
	}
	
	public int getTokenMemberPadding() {
		return this.tokenMemberPadding;
	}
	
	public int getTokenExirationDuration() {
		return this.tokenExpirationDuration;
	}
	
	public String getCookieName() {
		return this.cookieName;
	}
	
	public String getCookieDomain() {
		return this.cookieDomain;
	}
	
	public boolean getCookieSsl() {
		return this.cookieSSL;
	}

	public String getCurrencySymbol() {
		return this.currencySymbol;
	}
	
	public String getSystemMode() {
		return this.systemMode;
	}
	
	//**********************************************************************
	// Private Methods
	//**********************************************************************
	/** 
	 * 
	 * @return
	 */
	private String getContextKey() {
		return "";
	}

	//**********************************************************************
	// Public Methods
	//**********************************************************************
	
	/** ***************************************************************************
	 * EncryptObject commits all the objects values to a JSON string, then encrypts
	 * the string and stores it in the private variable encryptedObject, which 
	 * can be accessed through its public accessor getEncryptedObject.
	* *************************************************************************** */
	public void encryptConfigObject() {
		
		// Build the JSON String 
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add("CommandName","EncryptConfigObject");
		jsonBuilder.add("CommandVersion", "1.0");
		jsonBuilder.add("AuID", "1");
		jsonBuilder.add("PuID", "1");
		jsonBuilder.add("EllSecretKey",  this.getEllSecretKey());
		
		jsonBuilder.add("EllClientID", this.getEllClientID());
		jsonBuilder.add("EllSsoUri", this.getEllSsoUri());
		jsonBuilder.add("EllApiUri", this.getEllApiUri());
		
		
		jsonBuilder.add("EllClientID", this.getEllClientID());
		jsonBuilder.add("EllClientID", this.getEllClientID());
		jsonBuilder.add("EllClientID", this.getEllClientID());
		jsonBuilder.add("EllClientID", this.getEllClientID());
		
		
		String returnValue = jsonBuilder.build().toString();

		
	}

	/** ***************************************************************************
	 * 
	 * @author Warp Worldwide, LLC
	 * @version 2.0
	 * @since 2018.06.30
	 *
	 * DecryptObject decrypts the value in the local variable encryptedObject and 
	 * populates the Hsx object with the decrypted values. 
	* *************************************************************************** */
	public void DecryptObject() { 
		
		
		
		
	}








}
