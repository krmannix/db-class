<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.FriendController" %>
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
                if (request.getUserPrincipal() != null) {
                    int self_id = UserController.getUserIdByEmail(request.getUserPrincipal().getName());
                    List<User> allFriends = FriendController.getFriends(self_id);
                    if (allFriends.size() == 0) {
            %>
                        <h2>You have no friends!Go Back <a href="/photoshare/index.jsp">home</a></h2>
            <%
                    } else {
                    %> <ul> <%
                        for (User u : allFriends) {
            %>
                        <li><a href="/photoshare/user.jsp?user_id=<%= u.id%>"><%= u.first_name %> <%= u.last_name %></a></li>
            <%
                        }
                        %> </ul> <%
                    }
                } else {
            %>
                <h2><a href="/photoshare/index.jsp">Log In</a> to see your friends!</h2>
            <% 
                } 
            %>