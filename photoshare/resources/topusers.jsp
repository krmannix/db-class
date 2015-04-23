<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.UserController" %>
<%@ page import="photoshare.User" %>
<%@ page import="java.util.List" %>
<html>
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
                    <a class="navbar-brand" href="#">Photoshare</a>
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
                List<User> users = UserController.getTopTenUsers();
            %> <ul class="list-group"> <%
                    for (User u : users) {
            %>
                        <li class="list-group-item">
                            <a href="/photoshare/user.jsp?user_id=<%= u.id%>"><%= u.first_name %> <%= u.last_name %> <span class="badge"><%= u.total_points %></span></a>
                        </li>
            <%
                    }
            %> </ul>
        </div>
    </body>
</html>