<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.UserController" %>
<%@ page import="photoshare.FriendController" %>
<%@ page import="photoshare.User" %>
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
                User user_ = UserController.getUser(request.getParameter("user_id"));
                if (request.getParameter("user_id") == null || user_ == null) {
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
            %>
                    <div class="page-header">
                      <h1>Albums <small>List of <%= user_.first_name %> <%= user_.last_name %>'s albums</small></h1>
                    </div>
                    <div class="all-albums">
            <%
                        List<Album> albums = AlbumController.getAlbums(user_.id);
                        for (Album album : albums) {
            %>
                            <a href="/photoshare/album.jsp?album_id=<%= album.album_id %>"><span class="label label-default"><%= album.name %></span></a>
            <%
                        }
            %>
                    </div>
                    <br />
                    <br />
                    <br />
                    <div class="page-header">
                      <h1>Photos <small>List of <%= user_.first_name %> <%= user_.last_name %>'s photos</small></h1>
                    </div>
                    <div class="all-photos">
            <%
                        PictureDao pictureDao = new PictureDao(); 
                        List<Picture> pictures = pictureDao.getPicturesByUserID(user_.id);
                        for (Picture picture : pictures) {
                        int p_id = picture.getId();
            %>
                            <a href="/photoshare/photo.jsp?photo_id=<%= p_id %>">
                                <img src="/photoshare/img?t=1&picture_id=<%= p_id %>"/>
                            </a>
            <%
                        }
            %>
                    </div>
            <%
                }
            %>
        </div>
    </body>
</html>