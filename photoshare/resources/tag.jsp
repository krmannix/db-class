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
        	<% 
                int user_id = UserController.getUserIdByEmail(request.getUserPrincipal().getName());
                PictureDao pictureDao = new PictureDao();
                Tag tag = TagController.getTagById(
                    Integer.parseInt(request.getParameter("tag_id"))
                );
                if (request.getParameter("user_id") != null) {
                    // Only show pictures for the user
                    List<Picture> allPics = pictureDao.getPicturesByTagIDandUserID(
                        Integer.parseInt(request.getParameter("tag_id")),
                        Integer.parseInt(request.getParameter("user_id"))
                    );
                %>
                    <h1>All photos tagged with <b><%= tag.tag_name %></b> by <b><code><%= request.getUserPrincipal().getName() %></code></b> </h1>
                <%
                    for (Picture picture : allPics) {
                        int pictureId = picture.getId();
                %>
                        <a href="/photoshare/img?picture_id=<%= pictureId %>">
                            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
                        </a>
                <%
                    }
                    if (allPics.size() == 0) {
                %>
                        <h4>No pictures in this album</h4>
                <%
                    }
                } else {
                    // Show all pictures with this tag
                    List<Picture> allPics = pictureDao.getPicturesByTagID(
                        Integer.parseInt(request.getParameter("tag_id"))
                    );
                %>
                    <h1>All photos tagged with <b><%= tag.tag_name %></b></h1>
                    <%
                            for (Picture picture : allPics) {
                                int pictureId = picture.getId();
                    %>
                                <a href="/photoshare/img?picture_id=<%= pictureId %>">
                                    <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
                                </a>
                    <%
                            }
                            if (allPics.size() == 0) {
                    %>
                                <h4>No pictures in this album</h4>
                    <%
                            }
                 }
                    %>
        </div>
    </body>
</html>