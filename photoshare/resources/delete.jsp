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
                    User user_ = UserController.getUser(self_id);
                    if (request.getParameter("photo_id") != null) {
                        PictureDao pictureDao = new PictureDao();
                        pictureDao.deletePicture(Integer.parseInt(request.getParameter("photo_id")));
                    } else if (request.getParameter("album_id") != null) {
                        PictureDao pictureDao = new PictureDao();
                        AlbumController.deleteAlbum(
                            Integer.parseInt(request.getParameter("album_id")), 
                            pictureDao
                        );
                    }
            %>
                    <div class="page-header">
                      <h1>Albums <small>List of <%= user_.first_name %> <%= user_.last_name %>'s albums</small></h1>
                    </div>
                    <div class="all-albums">
                        <ul class="list-group">
            <%
                        List<Album> albums = AlbumController.getAlbums(user_.id);
                        for (Album album : albums) {
            %>
                            <li class="list-group-item">
                                <a href="/photoshare/album.jsp?album_id=<%= album.album_id %>"><span class="label label-default"><%= album.name %></span></a>
                                <a href="/photoshare/delete.jsp?album_id=<%= album.album_id %>"><span class="label label-danger"> Delete </span></a>
                            </li>
            <%
                        }
            %>
                        </ul>
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
                        for (int i = 0; i < pictures.size(); i++) {
                            int p_id = pictures.get(i).getId();
                            if (i%12 == 0) {
            %> 
                                <div class="row"> 
            <%
                            }
            %>
                        <div class="col-sm-1 text-center">
                            <li>
                                <a href="/photoshare/photo.jsp?photo_id=<%= p_id %>">
                                    <img src="/photoshare/img?t=1&picture_id=<%= p_id %>"/>
                                </a>
                                <br />
                                <a href="/photoshare/delete.jsp?photo_id=<%= p_id %>"><span class="label label-danger"> Delete </span></a>
                            </li>
                        </div>
            <%
                            if ((i+1)%12 == 0) {
            %> 
                                </div> 
            <%
                            }
                        }
            %>
                    </div>
            <%
                } else {
            %>
                    <h2>You must be <a href="/photoshare/index.jsp">logged in</a> to access this page</h2>
            <%
                }
            %>
        </div>
    </body>
</html>