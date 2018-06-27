<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="mysolutions.page_title" /></title>
	</head>
	<body>
		<header>
			<%@ include file="/htx/menu.html"%>
		</header>
		<form action="mysolutions" method="post" id="shopping-cart">	
		<fmt:message key="mysolutions.page_title" /><br>
		<div class="form-row" id="my">
			<fieldset>
				${mySolutions}
			</fieldset>
		</div>			
	

		</form>
	    <footer>
	    		<%@ include file="/htx/footer.html" %>
	    </footer>
	</body>
</html>