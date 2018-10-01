<%@ include file="/htx/pagehead.html"%>
<c:out value="${requestScope.resourceFileName}" />

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="configure_warpweb.page_title" /></title>
	</head>

	<body >
		<header>		
			<%@ include file="/htx/menu.html"%>
		</header>

		<form method="post" action="landing" name="landing" autocomplete="off">
			
			<input type="text" name="resourceFileName" id="resourceFileName" class="registerInput" value=${resourceFileName}>
			
			<p><fmt:message key="configure-warpweb.form_title" /></p>
			<fmt:message key="configure-warpweb.form_title" />
			
			<p><b><fmt:message key="configure-warpweb.ell_section_title" /></b></p>
				<fmt:message key="configure-warpweb.ell_client_id" />
				<fmt:message key="configure-warpweb.ell_secret_key" />
				<fmt:message key="configure-warpweb.ell_single_sign_on_uri" />
				<fmt:message key="configure-warpweb.ell_api_uri" />
			
			<p><b><fmt:message key="configure-warpweb.stripe_section_title" /></b></p>
				<fmt:message key="configure-warpweb.stripe_secret_key" />
				<fmt:message key="configure-warpweb.stripe_public_key" />
			
			<p><b><fmt:message key="configure-warpweb.resource_section_title" /></b></p>
				<fmt:message key="configure-warpweb.resource_file" />
				
			<p><b><fmt:message key="configure-warpweb.database_section_title" /></b></p>
				<fmt:message key="configure-warpweb.jdbc_uri" />
				<fmt:message key="configure-warpweb.jdbc_user" />
				<fmt:message key="configure-warpweb.jdbc_password" />
			
			<p><b><fmt:message key="configure-warpweb.cookie_section_title" /></b></p>
				<fmt:message key="configure-warpweb.token_salt" />
				<fmt:message key="configure-warpweb.token_member_padding" />
				<fmt:message key="configure-warpweb.token_exipration_duration" />
				<fmt:message key="configure-warpweb.cookie_name" />
				<fmt:message key="configure-warpweb.cookie_domain" />
				<fmt:message key="configure-warpweb.cookie_ssl" />
			
			<p><b><fmt:message key="configure-warpweb.system_mode_section_title" /></b></p>
				<fmt:message key="configure-warpweb.system_mode" />
			
			<p><b><fmt:message key="configure-warpweb.currency_section_title" /></b></p>
				<fmt:message key="configure-warpweb.currency_symbol" />
			
		</form>
		<footer>
    		<%@ include file="/htx/footer.html"%>
    	</footer>
	</body>

</html>