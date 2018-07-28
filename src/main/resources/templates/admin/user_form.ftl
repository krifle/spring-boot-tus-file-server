<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Admin - User Form</title>
</head>
<body>
	<h1>User Form</h1>
	<form method="post" action="/admin/user/form">
		<table>
			<tr>
				<th>User Name</th>
				<td>
					<#if edit>
					${user.username}
					<input type="hidden" name="username" value="${user.username}" />
					<#else>
					<input type="text" name="username" />
					</#if>
				</td>
			</tr>
			<tr>
				<th>Email</th>
				<td><input type="text" name="email" value="${(user.email)!}" /></td>
			</tr>
			<tr>
				<th>IPs</th>
				<td>
					<textarea name="ipList" cols="30" rows="5"><#list user.ips as ip>${ip.ip}<#sep>
</#sep></#list></textarea>
				</td>
			</tr>
			<tr>
				<th>Authorities</th>
				<td>
					<select name="authorityList" multiple>
						<#list authorities as authority>
						<option value="${authority}" ${user.authorityNames?seq_contains(authority)?string("selected", "")}>${authority}</option>
						</#list>
					</select>
				</td>
			</tr>
			<#if edit>
			<tr>
				<th>Registered</th>
				<td>${user.regDate?string["yyyy-MM-dd"]}</td>
			</tr>
			<tr>
				<th>Last Modified</th>
				<td>${user.modDate?string["yyyy-MM-dd"]}</td>
			</tr>
			<tr>
				<th>Last Login</th>
				<td>${user.lastLogin?string["yyyy-MM-dd"]}</td>
			</tr>
			<tr>
				<th>Account Expired</th>
				<td>${user.accountNonExpired?then("Y", "N")} <a href="./">Toggle</a></td>
			</tr>
			<tr>
				<th>Account Locked</th>
				<td>${user.accountNonLocked?then("Y", "N")} <a href="./">Toggle</a></td>
			</tr>
			<tr>
				<th>Credential Expired</th>
				<td>${user.credentialsNonExpired?then("Y", "N")} <a href="./">Toggle</a></td>
			</tr>
			<tr>
				<td>Enabled</td>
				<td>${user.enabled?then("Y", "N")} <a href="./">Toggle</a></td>
			</tr>
			</#if>
		</table>
		<input type="submit"/>
	</form>
</body>
</html>
