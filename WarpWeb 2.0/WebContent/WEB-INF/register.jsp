<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
    <head>
		<%@ include file="/htx/html.head.html"%>
		<title><fmt:message key="register.page_title" /></title>
    </head>
<body>
    		<header>
			<%@ include file="/htx/menu.html"%>
		</header>
        <form method="post" action="register" enctype="multipart/form-data" name="registration" autocomplete="off">
        <div>
	        <table>
	        		<tr>
			  		<td><br><label><fmt:message key="register.label.username" /></label></td>
			  		<td><input type="text" name="memberName" id="memberName" class="registerInput" value=${param["memberName"]}></td>
		  		</tr>
	        		<tr>
	        			<td><label><fmt:message key="register.label.country" /></label></td>
				  	<td>	
				  		<select name="countrySelector" id="countrySelector">
				  			<option value="0">- Choose One -</option>
						  	<option value="236">United States</option>
						  	<option value="46">China</option>
						  	<option value="41">Canada</option>
						</select>
				  	</td>
				  <tr>
				  	<td><label><fmt:message key="register.label.email1" /></label></td>
				  	<td><input type="text" name="emailAddress" id="emailAddress" class="registerInput" value=${param["emailAddress"]}></td>
				  </tr>
				  <tr>
				  	<td><label><fmt:message key="register.label.email2" /></label></td>
				  	<td><input type="text" name="emailAddress" id="emailAddress" class="registerInput" value=${param["emailAddress"]}></td>
				  </tr>
				  <tr>	
				  	<td>	<label><fmt:message key="register.label.firstname" /></label></td>
				  	<td><input type="text" name="firstName" id="firstName" class="registerInput" value=${param["firstName"]}></td>
				  </tr>
				  
				  <tr>
				  	<td>	<label><fmt:message key="register.label.lastname" /></label></td>
				  	<td>	<input type="text" name="lastName" id="lastName" class="registerInput" value=${param["lastName"]}></td>
				  </tr>
				  
				  <tr>
				  	<td>	<label><fmt:message key="register.label.birthdate"/></label></td>
				  	<td>	<input type="text" name="birthDate" id="birthDate" placeholder="YYYY-MM-DD" class="registerInput" value=${param["birthDate"]}></td>
				  </tr>
				  
				  <tr>	
				  	<td>	<label><fmt:message key="register.label.phonenumber" /></label></td>
				  	<td><input type="text" name="phoneNumber" id="phoneNumber" class="registerInput" value=${param["phoneNumber"]}></td>
				  </tr>
				  <tr>	
				  	<td>	<label><fmt:message key="register.label.passphrase1" /></label></td>
				  	<td><input type="password" name="passPhrase1" id="passPhrase1" class="registerInput"></td>
				  </tr>		
				  <tr>
				  	<td><label><fmt:message key="register.label.passphrase2" /></label></td>
				  	<td><input type="password" name="passPhrase2" id="passPhrase2" class="registerInput"></td>
				  </tr>		
				  <tr>
				  	<td><button type="button" onclick="toggleHide2('passPhrase1','passPhrase2')" id="passToggle" class="btn btn-primary">Show/Hide Passphrase</button></td>
				  	<td><textarea id="errorMessage" name="errorMessage" class="statusMessageBox" style='<%=request.getAttribute("ErrorMessageVisible")%>' readonly><%=request.getAttribute("ErrorMessage")%></textarea></td>
	  			</tr>
	  		</table>
  		</div>
  		<br>
		<div>
              <label for="captchaCode" class="prompt">Retype the characters from the picture:</label>

                 <!-- Adding BotDetect Captcha to the page -->
                 <botDetect:captcha id="exampleCaptcha" 
                             userInputID="captchaCode"
                             codeLength="4"
                             imageWidth="200"
                             codeStyle="ALPHA"
                              />

                 <div class="validationDiv">
                     <input id="captchaCode" type="text" name="captchaCode" />
                     <span class="incorrect">${messages.captchaIncorrect}</span>
                 </div>
			
		</div>
		<br/>
		<button type="submit" value="native" name="btnNative" class="btn btn-primary">Join WARP Now!</button>
		
  		  

	</form>
	<footer>
		<%@ include file="/htx/footer.html"%>
	</footer>
</body>
</html>