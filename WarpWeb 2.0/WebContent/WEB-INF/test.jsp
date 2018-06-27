<%@ include file="/htx/pagehead.html"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="test.page_title" /></title>
		<script>
			$(document).ready(function(){
			    $("button").click(function(){
			        var txt = "";
			        txt += "Document width/height: " + $(document).width();
			        txt += "x" + $(document).height() + "\n";
			        txt += "Window width/height: " + $(window).width();
			        txt += "x" + $(window).height();
			        alert(txt);
			    });
			});
		</script>
	</head>
	<body>
		<header class="row" class="col-md-3" style="width:90%">
			<%@ include file="/htx/menu.html"%>
		</header>
		<form method="post" action="test" name="test" autocomplete="off">
			<!--  request-time attribute value example. -->
			<jsp:setProperty name="logEntry" property="entryTime" value="<%= new java.util.Date( ) %>" />
		    <b>Tomcat Version</b> : <%= application.getServerInfo() %><br>    
    			<b>Servlet Specification Version :</b> 
			<%= application.getMajorVersion() %>.<%= application.getMinorVersion() %> <br>    
    			<b>JSP version :</b>
			<%=JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %><br>
		
			<b>Java Class Path :</b>b
			<%=System.getProperty("java.class.path") %><br>
			<b>Java VM Version :</b>
			<%=System.getProperty("java.vm.version") %><br>
			<b>Java Version :</b>
			<%=System.getProperty("java.version") %><br>
			
			<b>User-Agent:</b> 
			<%=request.getHeader("User-Agent") %><br>
			<b>Headers:</b><br>
			<textarea id="headers" name="headers" rows="4" cols="120" style="overflow-y: scroll;"><%=  request.getAttribute("headers")  %></textarea><br>
			<b>IP Address</b>
			<%=request.getRemoteAddr() %><br>
			
			<b>Cookie</b><br>
			<%=request.getRemoteAddr() %><br>
			
			<button id="refresh" name="refresh">Refresh</button>
		</form>
		<footer>
			
		</footer>
	</body>
</html>