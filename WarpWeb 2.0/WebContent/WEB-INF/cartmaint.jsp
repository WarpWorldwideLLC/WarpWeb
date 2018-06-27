<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="cartmaint.page_title" /></title>
	</head>
	<body>
		<header>
			<%@ include file="/htx/menu.html"%>
		</header>
		<form action="cartmaint" method="post" id="shopping-cart">
			<fmt:message key="cartmaint.page_title" />
			<div class="form-row" id="productDescription">
				<fieldset>
					${displayCart}
				</fieldset>
			</div>	
			<div><p><fmt:message key="checkout.currency_conversion" /></p></div>
			<section>
	          <h2><fmt:message key="cartmaint.payment_type_label" /></h2>
		          <nav id="payment-methods">
		            <ul>
		          
		              <li>
		              	<div class="warpRow">
		              		<div class="warpItem">
			                		<input type="radio" name="payment" id="payment-card" value="payment-card" checked>
			                </div>
			                <div class="warpItem">
			                		<label for="payment-card"><fmt:message key="cartmaint.payment_type.creditcard" /></label>
			                </div>
			                		<div class="warpItem">
			                <img src="imgx/pymt_logo_credit.jpg" alt=<fmt:message key="cartmaint.payment_type.creditcard_alt" /> height="55" width="70">
			                </div>
		                </div>
		              </li>
		              
		              <li>
		              	<div class="warpRow">
			                <div class="warpItem">
				                <input type="radio" name="payment" id="payment-alipay" value="payment-alipay">
				       		</div>
			                <div class="warpItem">
				                <label for="payment-alipay"><fmt:message key="cartmaint.payment_type.alipay" /></label>
				            	</div>
			                <div class="warpItem">
				                <img src="imgx/pymt_logo_alipay.png" alt=<fmt:message key="cartmaint.payment_type.alipay_alt" /> height="100" width="135">
				            </div>
				        </div>
		              </li>
		            </ul>
		          </nav>
			</section>
			<button name="completePurchase" class="btn btn-primary" value="completePurchase"><fmt:message key="cartmaint.button.complete_purchase" /></button>
			<button name="continueShopping" class="btn btn-primary" value="continueShopping"><fmt:message key="cartmaint.button.continue_shopping" /></button>
		</form>
	    <footer>
	    		<%@ include file="/htx/footer.html"%>
	    </footer>
	</body>
</html>