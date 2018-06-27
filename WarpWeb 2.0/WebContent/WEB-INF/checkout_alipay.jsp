<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="checkout_alipay.page_title" /></title>
		<script src="https://js.stripe.com/v3/"></script>
		<style>
		/**
		 * The CSS shown here will not be introduced in the Quickstart guide, but shows
		 * how you can use CSS to style your Element's container.
		 */
		.StripeElement {
		  background-color: white;
		  height: 40px;
		  padding: 10px 12px;
		  border-radius: 4px;
		  border: 1px solid transparent;
		  box-shadow: 0 1px 3px 0 #e6ebf1;
		  -webkit-transition: box-shadow 150ms ease;
		  transition: box-shadow 150ms ease;
		}
		
		.StripeElement--focus {
		  box-shadow: 0 1px 3px 0 #cfd7df;
		}
		
		.StripeElement--invalid {
		  border-color: #fa755a;
		}
		
		.StripeElement--webkit-autofill {
		  background-color: #fefde5 !important;
		}
		</style>
		 
		<script>
			
			function stripeSourceHandler(source) {
				  // Insert the token ID into the form so it gets submitted to the server
				  var form = document.getElementById('payment-form');
				  var hiddenInput = document.createElement('input');
				  hiddenInput.setAttribute('type', 'hidden');
				  hiddenInput.setAttribute('name', 'stripeSourceId');
				  hiddenInput.setAttribute('value', source.id);
				  form.appendChild(hiddenInput);
		
				  var  authURL = document.createElement('input');
				  authURL.setAttribute('type', 'hidden');
				  authURL.setAttribute('name', 'authURL');
				  authURL.setAttribute('value', source.redirect.url);
				  form.appendChild(authURL);
				  
				  var  clientSecret = document.createElement('input');
				  clientSecret.setAttribute('type', 'hidden');
				  clientSecret.setAttribute('name', 'clientSecret');
				  clientSecret.setAttribute('value', source.client_secret);
				  form.appendChild(clientSecret);
		
				  // alert('authURL: ' + source.redirect.url);
				  // Submit the form
				  form.submit();
				}
			
		</script>
		
	</head>
	<body>
		<header>
			<%@ include file="/htx/menu.html"%>
		</header>
		<form action="checkout_alipay" method="post" id="payment-form">
		
		<fmt:message key="checkout_alipay.label" />
		<div class="form-row" id="productDescription]">
			<fieldset>
				${displayCart}
			</fieldset>
		</div>	
		<div><p><fmt:message key="checkout.currency_conversion" /></p></div>
		<br>
		<div class="form-row" id="aliPayInfo">

			   
		</div>
		
		<br><br>
		<button id="SubmitPayment" name="SubmitPayment" value="posted"><fmt:message key="checkout_alipay.button.submit_payment" /></button>
		  
		</form>
	
		<script>
			// Create a Stripe client.
			var stripe = Stripe('${paymentPublicKey}');
				
			// Handle form submission.
			var form = document.getElementById('payment-form');
			
					form.addEventListener('submit', function(event) {
					  event.preventDefault();
					  
					  stripe.createSource({
						  type: 'alipay',
						  amount: '${ShoppingCartTotalCost}',
						  currency: 'usd',
						  redirect: {
						    return_url: '${stripe_return_url}',
						  },
						}).then(function(result) {
					    if (result.error) {
					      // Inform the user if there was an error
					      var errorElement = document.getElementById('card-errors');
					      errorElement.textContent = result.error.message;
					      //alert("fail");
					    } else {
					      // Send the source to your server
					      // alert("success");
					      stripeSourceHandler(result.source);
					      
					    }
					  });
					});
	
		
	
	</script>

    </body>
    		<footer>
				<%@ include file="/htx/footer.html"%>
		</footer>
</html>