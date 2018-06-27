<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="checkoutconfirm.page_title" /></title>
	</head>
	<body>
		<header>
			<%@ include file="/htx/menu.html"%>
		</header>
		<form action="checkoutconfirm" method="post">
		<div class="form-row" id="productDescription">
			<fieldset>
				${displayCart}
			</fieldset>
		</div>	
		<div><p><fmt:message key="checkout.currency_conversion" /></p></div>
		<br><br>
			<input id="paymentSourceId" name="paymentSourceId" type="hidden" value=${param["vid"]}>
			<input id="email-address" name=email-address type="hidden" value=${param["email-address"]}>
			<div style=" margin-top: 1em;">
			<button id="confirmPayment" name="confirmPayment" type="submit" class="btn btn-primary"><fmt:message key="checkoutconfirm.button.confirm_payment" /></button>
			</div><br>
		</form>
		<footer>
				<%@ include file="/htx/footer.html"%>
		</footer>
	</body>
</html>