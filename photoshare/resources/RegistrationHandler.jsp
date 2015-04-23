<%@ page import="photoshare.RegistrationController" %>
<%@ page import="photoshare.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <head>
        <title>Photoshare</title>
        <link href="css/bootstrap.min.css" rel="stylesheet"/>
        <link href="css/custom.css" rel="stylesheet"/>
    </head>
    <body>
        <!-- NAV BAR -->
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/photoshare/index.jsp">Photoshare</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="/photoshare/logout.jsp">Log Out</a></a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
			<%
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				String bday = request.getParameter("day");
				String bmonth = request.getParameter("month");
				String byear = request.getParameter("year");
				String gender = request.getParameter("gender");
				String c_city = request.getParameter("c_city");
				String c_state = request.getParameter("c_state");
				String c_country = request.getParameter("c_country");
				String h_city = request.getParameter("h_city");
				String h_state = request.getParameter("h_state");
				String h_country = request.getParameter("h_country");
				if (RegistrationController.validateUser(firstName, lastName, email, password, gender, bday, bmonth, byear, c_city, c_state, c_country, h_city, h_state, h_country)) {
					User newUser = new User(firstName, lastName, email, password, gender, bday, bmonth, byear, c_city, c_state, c_country, h_city, h_state, h_country);
					RegistrationController.registerUser(newUser);
					// Display page
			%>
				<h2>You registered! Awesome. Now, <a href="/photoshare/index.jsp">sign back in</a></h2>
			<%
				} else {
			%>
				<h2>Error with registering. Please try again <a href="/photoshare/index.jsp">here.</a></h2>
			<% } %>
		</div>
	</body>
</html>
