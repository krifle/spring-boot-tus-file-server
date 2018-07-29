<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Admin - Exception</title>
</head>
	<h1>Exception</h1>
<body>
	<div>
		${exceptionMessage}
	</div>
	<#if (fallbackUrl)??><a href="${fallbackUrl}">Go back</a></#if>
</body>
</html>
