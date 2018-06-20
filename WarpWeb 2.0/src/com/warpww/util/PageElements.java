package com.warpww.util;

public class PageElements {
	
	//**********************************************************************
	// Private Variables
	//**********************************************************************
	
	
	//**********************************************************************
	// Accessors and Mutators (Getters and Setters)
	//**********************************************************************
	//----------------------------------------------------------------------
	// getJspPageDirectives - was htx/pagehead.html
	//----------------------------------------------------------------------
	public String getJspPageDirectives() {
		String returnValue = null;
		
		returnValue += this.getJspPageDirectives201804();
		
		return returnValue;
	}
	
	private String getJspPageDirectives201804() {
		String returnValue = null;
		
		returnValue += "";
		// returnValue += "<%@page trimDirectiveWhitespaces=\"true\"%>/n";
		// returnValue += "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>/n"; 
		// returnValue += "<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>"; 
		// returnValue += "<%@ taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\" %>"; 
		// returnValue += "<%@taglib prefix=\"botDetect\" uri=\"https://captcha.com/java/jsp\"%>"; 
		// returnValue += "<%@page import=\"com.captcha.botdetect.web.servlet.Captcha\"%>"; 
		// returnValue += "<c:set var=\"language\" value=\"${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}\" scope=\"session\" />"; 
		// returnValue += "<fmt:setLocale value=\"${language}\" />"; 
		returnValue += "<fmt:setBundle basename=\"com.warpww.web.i18n.warp\" />";
		
		return returnValue;
	}
	
	
	public String getHtmlHead() {
		String returnValue = null;
		
		returnValue += "";
		returnValue += "<!--  meta tags -->";
		returnValue += "<meta name=\"\" content=\"\">";

		return returnValue;
	}
	
	
	public String getHtmlBodyHeader() {
		String returnValue = null;
		
		returnValue += "" + 
				"<div id=\"TopMenu\" class=\"topMenu\">\n" + 
				"		  <ul>\n" + 
				"			<li class=\"topMenuItem\"><a href=\"landing201804\" ><img src=\"imgx/warp_logo3.png\" title=\"WARP Worldwide, LLC\" width=\"142\" height=\"80\" /></a>	\n" + 
				"			<li class=\"topMenuItem\"><a>1-866-341-8535</a>\n" + 
				"			<li class=\"topMenuItem\"><a href=\"landing201804\">Home</a>\n" + 
				"			<li class=\"topMenuItem\"><a href=\"#openModalLogin\">Login</a>\n" + 
				"			<li class=\"topMenuItem\"><a href=\"register201804\">Create Account</a>\n" + 
				"			<li class=\"topMenuItem\"><a href=\"cartmaint201804\">Shopping Cart</a>\n" + 
				"			<li class=\"topMenuItem\"><a href=\"mysolutions201804\">My Solutions</a>\n" + 
				"			<!-- \n" + 
				"			<li class=\"topMenuItem\"><a href=\"register201804\">Join / Membership</a>\n" + 
				"			 -->\n" + 
				"			<li class=\"topMenuItem\"><a href=\"contact201804\">Contact</a>\n" + 
				"		  </ul>\n" + 
				"		  <div class=\"account\" align=\"right\">\n" + 
				"			<!--  <label class=\"accountbtn\"><fmt:message key=\"menu.label.greeting\" /><%=request.getAttribute(\"Greeting\")%></label>  -->\n" + 
				"			${accountButton}\n" + 
				"		  </div>\n" + 
				"	  <br><hr/>\n" + 
				"</div>\n" + 
				"<div id=\"ValueStatment\" class=\"valueStatement\" style=\"color: gray;\">\n" + 
				"<br>\n" + 
				"\"My heart is to create opportunities for people all over the world to achieve at the highest level throughout their lives while embracing a global worldview.\"\n" + 
				"<br> - Grace Arp, President and Founder, WARP Worldwide \n" + 
				"<br>\n" + 
				"</div>\n" + 
				"<div id=\"BottomMenu\" class=\"bottomMenu\">\n" + 
				"	<ul id=\"navList\">\n" + 
				"		<li class=\"bottomMenuItem\"><a href=\"warp_sol\">Warp Sol<br>American K12 Online</a>\n" + 
				"		<li class=\"bottomMenuItem\"><a href=\"warp_vega\">Warp Vega<br>STEM Hubs</a>\n" + 
				"		<li class=\"bottomMenuItem\"><a href=\"warp_sirius\">Warp Sirius<br>American Master Certificates</a>	\n" + 
				"		<li class=\"bottomMenuItem\"><a href=\"partneralliance\">Partner<br>Alliance</a>			\n" + 
				"	</ul>	\n" + 
				"	<form method=\"post\" action=\"login\" name=\"login\" autocomplete=\"off\">\n" + 
				"		<div id=\"openModalLogin\" class=\"modalDialog\">\n" + 
				"			<div>\n" + 
				"				<a href=\"#close\" title=\"Close\" class=\"close\">X</a>\n" + 
				"				<h2>Login</h2>\n" + 
				"				<br><input class=\"form-control text-box single-line\" id=\"memberName\" name=\"memberName\" type=\"text\" placeholder=\"MemberName\" />\n" + 
				"				<br><input class=\"form-control text-box single-line\" id=\"passPhrase\" name=\"passPhrase\" type=\"password\" placeholder=\"Passphrase\" />\n" + 
				"				<div style=\" margin-top: 1em;\">\n" + 
				"  					<input type=\"submit\" class=\"btn btn-primary\"></a>\n" + 
				"				</div>\n" + 
				"				<!--\n" + 
				"				<p><a href=\"Forgot your passphrase?</p>\n" + 
				"				-->\n" + 
				"				<p><a href=\"register201804\">Not a member? Click here to sign up for free!</a></p>\n" + 
				"			</div>\n" + 
				"		</div> 	\n" + 
				"	</form>\n" + 
				"\n" + 
				"	<form method=\"post\" action=\"logout\" name=\"logout\" autocomplete=\"off\">\n" + 
				"		<div id=\"openModalLogout\" class=\"modalDialog\">\n" + 
				"			<div>\n" + 
				"				<a href=\"#close\" title=\"Close\" class=\"close\">X</a>\n" + 
				"				<h2>Logout</h2>\n" + 
				"				Logout Now?\n" + 
				"				<div style=\" margin-top: 1em;\">\n" + 
				"  					<input type=\"submit\" class=\"btn btn-primary\"></a>\n" + 
				"				</div>\n" + 
				"			</div>\n" + 
				"		</div> 	\n" + 
				"	</form>\n" + 
				"	<!-- \n" + 
				"	<form>\n" + 
				"	    <select id=\"language\" name=\"language\" onchange=\"submit()\">\n" + 
				"	      <option value=\"en\" ${language == 'en' ? 'selected' : ''}><fmt:message key=\"footer.language.selector.en\" /></option>\n" + 
				"	      <option value=\"es\" ${language == 'es' ? 'selected' : ''}><fmt:message key=\"footer.language.selector.es\" /></option>\n" + 
				"	      <option value=\"zh\" ${language == 'zh' ? 'selected' : ''}><fmt:message key=\"footer.language.selector.zh\" /></option>\n" + 
				"	    </select>\n" + 
				"	</form>\n" + 
				"	 -->\n" + 
				" </div>\n" + 
				"<br>";


		return returnValue;		
	}
	
	//**********************************************************************
	// Enumerations
	//**********************************************************************
	
	//**********************************************************************
	// Constructors
	//**********************************************************************
	public PageElements() {
		
	}
	
	//**********************************************************************
	// Methods
	//**********************************************************************
	//----------------------------------------------------------------------
	// Set Header Records
	//----------------------------------------------------------------------
	

}
