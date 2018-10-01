<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="news.page_title" /></title>
	</head>
	<body>
		<header>
			<%@ include file="/htx/menu.html"%>
		</header>
		<form  action="product?productPage=news" method="post" id="news">
			<p><fmt:message key="news.page_title" /></p>
			<div style=" margin-top: 1em;">
  				<!--  <p class="lead">Download the full brochure by clicking below.</p>  -->
  				<a target="_blank" href="filx/news_lingo.pdf"><fmt:message key="warp_news.lingo1" /></a>
			</div>
			<div style=" margin-top: 1em;">
  				<a target="_blank" href="filx/news_cultural_immersion.pdf"><fmt:message key="warp_news.xaiu1" /></a>
			</div>

		</form>
		<footer>
	    		<%@ include file="/htx/footer.html"%>
	    </footer>
	</body>
</html>