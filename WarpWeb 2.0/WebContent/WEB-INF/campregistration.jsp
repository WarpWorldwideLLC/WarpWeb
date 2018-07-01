<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="campregistration.page_title" /></title>
	</head>
    <body>
		<header>
			<%@ include file="/htx/menu.html"%>
		</header>
    		<form method="post" action="campregistration" name="campregistration" autocomplete="off">
 		
 		<label id="paymentMessage" name = "paymentMessage" style="color: blue">${paymentMessage}</label><br>
		Camp Registration<br><br>

				Traveler Name:<br>
				<input type="text" id="travelerName" name="travelerName" size="40" value='${param["travelerName"]}' required><br>

				Parent/Guardian Email Address:<br>
				<input type="text" id="parentName" name="parentName" size="40" value='${param["parentName"]}' required><br>

				Traveler Address:<br> 
				<textarea id="travelerAddress" name="travelerAddress" cols="43" rows="5"  aria-required="true" aria-invalid="false" required>${param["travelerAddress"]}</textarea><br>

				Traveler Primary Phone Number:<br> 
				<input type="text" id="travelerPrimaryPhoneNumber" name="travelerPrimaryPhoneNumber" value='${param["travelerPrimaryPhoneNumber"]}' size="40" required><br>

				Traveler Alternative Phone Number:<br> 
				<input type="text" id="travelerAlternatePhoneNumber" name="travelerAlternatePhoneNumber" value='${param["travelerAlternatePhoneNumber"]}' size="40" required><br>

				Traveler EmailAddress:<br> 
				<input type="text" id="travelerEMailAddress" name="travelerEMailAddress" size="40" value='${param["travelerEMailAddress"]}' required><br>
				
				Traveler Gender:<br> 
				<input type="text" id="travelerGender" name="travelerGender" size="40" value='${param["travelerGender"]}' required><br>

				Traveler Date of Birth:<br> 
				<input type="text" id="travelerDateOfBirth" name="travelerDateOfBirth" size="40" value='${param["travelerDateOfBirth"]}' required><br>

				Comments:<br> 
				<textarea id="travelerComments" name="travelerComments" cols="43" rows="10" aria-required="true" aria-invalid="false">${param["travelerComments"]}</textarea><br>

				Payment Type:<br>
				<input type="text" id="paymentType" name="paymentType" value='${paymentType}' size="40" readonly="readonly"><br>
				
				Payment Amount:<br>
				<input type="text" id="paymentAmountText" name="paymentAmountText" value='${paymentAmountText}' size="40" readonly="readonly"><br>
				<input type="text" id="paymentAmount" name="paymentAmount" value='${paymentAmount}' size="40" readonly="readonly" hidden="true" ><br>
				<input type="text" id="paymentDescription" name="paymentDescription" value='${paymentDescription}' size="40" readonly="readonly" hidden="true" ><br>
				Payment Notes:<br>
				<textarea name="paymentNotes" cols="43" rows="10"  aria-required="true" aria-invalid="false" readonly="readonly">${paymentNotes}</textarea><br>
				

		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		
        </form>
	    <footer>
	    		<%@ include file="/htx/footer.html"%>
	    </footer>
    </body>
</html>