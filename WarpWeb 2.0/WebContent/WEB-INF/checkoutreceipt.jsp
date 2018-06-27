<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="checkoutreceipt.page_title" /></title>
	</head>
	<body>
		<header>
			<%@ include file="/htx/menu.html"%>
		</header>
		<form action="checkoutreceipt" method="post" id="shopping-cart">		
			
			<fmt:message key="checkoutconfirm.page_label" />
			<div class="form-row" id="productDescription]">
				<fieldset>
					${displayCart}
				</fieldset>
			</div>	
			<div><p><fmt:message key="checkout.currency_conversion" /></p></div>
		</form>
	    <footer>
	    		<%@ include file="/htx/footer.html"%>
	    </footer>
	</body>
</html>