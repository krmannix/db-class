<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.UserController" %>
<%@ page import="photoshare.FriendController" %>
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
                User user_ = UserController.getUser(request.getParameter("user_id"));
                if (user_ == null) {
            %> 
                    <h3>Sorry, this user doesn't exist! Go <a href="/photoshare/index.jsp">back</a></h3> 
            <%
                } else {
            %>
                    <h1>
                        <%= user_.first_name %>
                        <%= user_.last_name %>
                    </h1>
            <% 
                    if (request.getUserPrincipal() != null) {
                        int self_id = UserController.getUserIdByEmail(request.getUserPrincipal().getName());
                        if (request.getParameter("user_id") != null && request.getParameter("self_id") != null) {
                            FriendController.makeFriends(user_.id, self_id);
            %>
                            <h5>You are friends!</h5>
            <%
                        } else if (FriendController.areFriends(user_.id, self_id)) {
            %>
                            <h5>You are friends!</h5>
            <%
                        } else if (self_id != user_.id) {
            %>
                                <a href="user.jsp?user_id=<%= user_.id %>&self_id=<%= self_id %>">
                                    <input type="submit" value="Add as friend" />
                                </a>
            <%  
                        }
                    }
                }
            %>
        </div>
    </body>
</html>