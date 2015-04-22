<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.SearchForFriendsController" %>
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
        		String search_field = request.getParameter("searchfield");
        		if (search_field.length() > 0) {
        			List<User> foundUsers = SearchForFriendsController.searchForFriends(search_field);
        			if (foundUsers.size() == 0) {
        	%> 
        			<h2>No results found for <%=  search_field %>. Go Back <a href="/photoshare/index.jsp">home</a></h2>
        	<%
        			} else {
        			%> <ul> <%
	        			for (User u : foundUsers) {
	        %>
	            		<li><a href="/photoshare/user.jsp?user_id=<%= u.id%>"><%= u.first_name %> <%= u.last_name %></a></li>
	        <%
	            		}
	            		%> </ul> <%
            		}
        		} else {
        	%>
        		<h2>Looks like you didn't search for anything. Go <a href="index.jsp">back</a> to the home screen.</h2>
        	<%
        		}

        	%>
        </div>
    </body>
</html>