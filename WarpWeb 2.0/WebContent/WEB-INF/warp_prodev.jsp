<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="warp_prodev.page_title" /></title>
	</head>
	<body>
	<header>		
		<%@ include file="/htx/menu.html"%>
	</header>
	<form method="post" action="warp_prodev" name="warp_prodev" autocomplete="off">
			
		<h2><fmt:message key="warp_prodev.p1.00" /></h2>
		<div>	
			<img class="warpImage" src="imgx/consulting-professional-dev-800-450-resize.jpg" alt=<fmt:message key="landing.stem_alt" /> />
		</div>
		<div>
			<fmt:message key="warp_prodev.p1.10" />		
		</div>
		<div>
			<fmt:message key="warp_prodev.p1.99.99" />
		</div>
		<fmt:message key="warp_prodev.p1.20.01" /><br>
		<fmt:message key="warp_prodev.p1.20.02" />
	</form>
    <footer>
    		<%@ include file="/htx/footer.html" %>
    </footer>
	</body>
</html>