<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Tus File: Register</title>
</head>
<body>
	<form action="/register" method="post">
		<table>
			<tr>
				<th>Username</th>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<th>Password</th>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<th>E-mail</th>
				</td><input type="text" name="email" /></td>
			</tr>
			<tr>
				<th>IPs</th>
				<td>
					<span>Please separate ips by comma or white spaces.</span><br/>
					<textarea name="ipList" cols="30" rows="5"></textarea>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
