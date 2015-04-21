<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Photoshare Login</title>
		<link href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/custom.css" rel="stylesheet"/>
	</head>
	<body class="login-page">
		<div class="row">
			<div class="col-md-6">
				<div class="box">
					<h2>Login</h2>
					<form method="POST" action="j_security_check">
					    <table>
					        <tr><th>Email</th><td><input type="text" name="j_username"></td></tr>
					        <tr><th>Password</th><td><input type="password" name="j_password"></td>
					        </tr>
					        <tr><td colspan="2" align="right"><input type="submit" value="Login"/>
					        </td></tr>
					    </table>
					</form>
				</div>
			</div>
			<div class="col-md-6">
				<div class="box">
					<h2>Sign up</h2>
					<form method="POST" action="RegistrationHandler.jsp">
					    <table>
					        <tr><th>Email</th><td><input type="text" name="j_username"></td></tr>
					        <tr><th>Password</th><td><input type="password" name="j_password"></td>
					        </tr>
					        <tr><td colspan="2" align="right"><input type="submit" value="Login"/>
					        </td></tr>
					    </table>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>