<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Admin - User List</title>
</head>
<body>
	<h1>User List</h1>
	<table>
		<tr>
			<th>User Name</th>
			<th>Email</th>
			<th>Registered</th>
			<th>Last Modified</th>
			<th>Last Login</th>
			<th>Account Expired</th>
			<th>Account Locked</th>
			<th>Credential Expired</th>
			<th>Enabled</th>
			<th>IPs</th>
			<th>Authorities</th>
			<th></th>
		</tr>
		<#list userList as user>
		<tr>
			<td><a href="/admin/user/form/${user.username}">${user.username}</a></td>
			<td>${user.email}</td>
			<td>${user.regDate?string["yyyy-MM-dd"]}</td>
			<td>${user.modDate?string["yyyy-MM-dd"]}</td>
			<td>${user.lastLogin?string["yyyy-MM-dd"]}</td>
			<td>${user.accountNonExpired?then("Y", "N")}</td>
			<td>${user.accountNonLocked?then("Y", "N")}</td>
			<td>${user.credentialsNonExpired?then("Y", "N")}</td>
			<td>${user.enabled?then("Y", "N")}</td>
			<td><#list user.ips as ip>${ip.ip}<#sep>, </#sep></#list></td>
			<td><#list user.authorities as authority>${authority.authority}<#sep>, </#sep></#list></td>
			<th><a href="/admin/user/delete/${user.username}">Delete</a></td>
		</tr>
		</#list>
	</table>

	<h2>Search</h2>
	<form action="/admin/users" method="get">
		<table>
			<tr>
				<th>User Name</th>
				<td><input type="text" name="username" /></td>
			</tr>
		</table>
	</form>

	<div>
		<a href="/admin/user/form/">Add user</a>
	</div>
</body>
</html>
