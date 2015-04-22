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
					    	<tr><th>First Name</th><td><input type="text" name="firstName"></td></tr>
					    	<tr><th>Last Name</th><td><input type="text" name="lastName"></td></tr>
					        <tr><th>Email</th><td><input type="text" name="email"></td></tr>
					        <tr><th>Password</th><td><input type="password" name="password"></td></tr>
					        <tr><th>Gender</th><td>
					        	<select name="gender">
					        		<option value="0">Female</option>
					        		<option value="1">Male</option>
					        	</select>
					        </td></tr>
					        <tr><th>Birthday</th><td>
					        	<select name="month">
					        		<option value="1">Jan</option>
					        		<option value="2">Feb</option>
					        		<option value="3">Mar</option>
					        		<option value="4">Apr</option>
					        		<option value="5">May</option>
					        		<option value="6">Jun</option>
					        		<option value="7">Jul</option>
					        		<option value="8">Aug</option>
					        		<option value="9">Sept</option>
					        		<option value="10">Oct</option>
					        		<option value="11">Nov</option>
					        		<option value="12">Dec</option>
					        	</select>
					        	<select name="day">
					        		<option value="1">1</option>
					        		<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>	
					        	</select>
					        	<input type=text name="year" size=4 value=1993>
					        </td></tr>
					        <tr><th>Home City</th><td><input type="text" name="h_city"></td></tr>
					        <tr><th>Home State</th><td><input type="text" size=2 maxlength="2" name="h_state"></td></tr>
					        <tr><th>Home Country</th><td><input type="text" name="h_country"></td></tr>
					        <tr><th>Current City</th><td><input type="text" name="c_city"></td></tr>
					        <tr><th>Current State</th><td><input type="text" size=2 maxlength="2" name="c_state"></td></tr>
					        <tr><th>Current Country</th><td><input type="text" name="c_country"></td></tr>
					        <tr><td colspan="2" align="right"><input type="submit" value="Submit"/>
					        </td></tr>
					    </table>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>