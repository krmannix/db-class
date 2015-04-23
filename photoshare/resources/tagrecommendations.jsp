<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.UserController" %>
<%@ page import="photoshare.User" %>
<%@ page import="photoshare.TagController" %>
<%@ page import="photoshare.Tag" %>
<%@ page import="photoshare.Album" %>
<%@ page import="photoshare.AlbumController" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
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
            <div class="page-header">
                <h3>Enter some tags to get recommended tags</h3>
                <form action="tagrecommendations.jsp">
                    <div class="input-group">
                        <input type="text" class="form-control" name="tags" placeholder="Enter tags seperated by a comma" />
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="submit">Recommendations!</button>
                        </spa>
                    </div>
                </form>
            </div>
            <% 
                if (request.getParameter("tags") != null) {
                    List<Tag> tags = TagController.getRecommendedTags(request.getParameter("tags"));
                    if (tags.size() == 0) {
            %>          
                        <h2>There are no recommended tags! Go Back <a href="/photoshare/index.jsp">home</a></h2>
            <%
                    } else {
            %>          <h2>Recommended Tags based on your input </h2>
                        <br />
                        <ol> 
            <%
                            for (Tag t : tags) {
            %>
                                <li>
                                    <a href="/photoshare/tag.jsp?tag_id=<%= t.tag_id %>"><%= t.tag_name %></a>
                                </li>
            <%
                            }
            %>          
                        </ol> 
            <%

                    }
                } else {
            %>
                    <h2>Go back and enter some tags! Go Back <a href="/photoshare/index.jsp">home</a></h2>
            <%
                }
            %>
        </div>
    </body>
</html>