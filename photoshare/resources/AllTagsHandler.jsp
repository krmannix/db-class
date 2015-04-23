<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.TagController" %>
<%@ page import="photoshare.Tag" %>
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
                if (request.getParameter("popular") != null) {
                    // Show the most popular tags
                    List<Tag> popTags = TagController.getPopularTags();
                    if (popTags.size() == 0) {
                        %> <h2>There are no used tags! Go Back <a href="/photoshare/index.jsp">home</a></h2> <%
                    } else {
                        %> <h2>Popular Tags </h2><br /><ol> <%
                            for (Tag t : popTags) {
            %>
                                <li>
                                    <a href="/photoshare/tag.jsp?tag_id=<%= t.tag_id %>"><%= t.tag_name %></a>
                                </li>
            <%
                            }
                        %> </ol> <%

                    }      
            %>
            <%
                } else {
                    if (request.getParameter("user_id") == null) {
                        List<Tag> allTags = TagController.getAllTags();
                        if (allTags.size() == 0) {
            %>
                            <h2>There are no tags! Go Back <a href="/photoshare/index.jsp">home</a></h2>
            <%
                        } else {
                            %> <ul> <%
                            for (Tag t : allTags) {
            %>
                                <li>
                                    <a href="/photoshare/tag.jsp?tag_id=<%= t.tag_id %>"><%= t.tag_name %></a>
                                </li>
            <%
                            }
                            %> </ul> <%
                        }
                    } else {
                        int user_id = Integer.parseInt(request.getParameter("user_id"));
                        List<Tag> allTags = TagController.getTagsByUserId(user_id);
                        if (allTags.size() == 0) {
            %>
                            <h2>There are no tags! Go Back <a href="/photoshare/index.jsp">home</a></h2>
            <%
                        } else {
                            %> <ul> <%
                            for (Tag t : allTags) {
            %>
                                <li>
                                    <a href="/photoshare/tag.jsp?tag_id=<%= t.tag_id %>&user_id=<%= user_id %>"><%= t.tag_name %></a>
                                </li>
            <%
                            }
                            %> </ul> <%
                        }
                    }
                }
            %>
        </div>
    </body>
</html>