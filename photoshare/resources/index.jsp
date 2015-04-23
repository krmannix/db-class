<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.TagController" %>
<%@ page import="photoshare.UserController" %>
<%@ page import="photoshare.AlbumController" %>
<%@ page import="photoshare.Album" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

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
            <div class="row">
                <div class="col-sm-3">
                    <div class="panel panel-info">
                      <div class="panel-heading">
                        <h3 class="panel-title">
                            Hello <b><code><%= request.getUserPrincipal().getName() %></code></b>
                            <%
                                int user_id = UserController.getUserIdByEmail(request.getUserPrincipal().getName());
                            %>
                        </h3>
                      </div>
                      <div class="panel-body">
                        <div class="input-group">
                            <form action="SearchForFriendsHandler.jsp">
                                <input type="text" class="form-control" placeholder="Find friends" name="searchfield">
                                <span class="input-group-btn">
                                    <button class="btn btn-default" type="submit">Go!</button>
                                </span>
                            </form>
                        </div>
                        <ul class="list-group">
                            <li class="list-group-item"><a href="/photoshare/AllFriendsHandler.jsp">View Your Friends</a></li>
                            <li class="list-group-item"><a href="/photoshare/AllTagsHandler.jsp?user_id=<%= user_id %>">View Your Tags</a></li>
                            <li class="list-group-item"><a href="/photoshare/AllTagsHandler.jsp">View All Tags</a></li>
                            <li class="list-group-item"><a href="/photoshare/AllTagsHandler.jsp?popular=true">View Popular Tags</a></li>
                            <li class="list-group-item"><a href="#">Recommendations</a></li>
                            <li class="list-group-item"><a href="/photoshare/topusers.jsp">Activity Leaderboards</a></li>
                            <li class="list-group-item"><a href="/photoshare/delete.jsp">Delete Albums and Photos</a></li>
                        </ul>
                      </div>
                    </div>
                </div>
                <div class="col-sm-9">
                    <h2>Create a new album!</h2>
                    <form action="createAlbum.jsp" method="post">
                        Name: <input type="text" name="album_name">
                        <input type="submit" value="Create">
                    </form>
                    <hr />
                    <%
                        int self_id = UserController.getUserIdByEmail(request.getUserPrincipal().getName());
                        List<Album> foundAlbums = AlbumController.getAlbums(self_id);
                        if (foundAlbums.size() > 0) {
                    %>      
                            <h2>All Albums</h2>
                            <ul> 
                    <%
                            for (Album album : foundAlbums) {
                    %>
                                <li>
                                    <a href="/photoshare/album.jsp?album_id=<%= album.album_id%>"><%= album.name %> </a>
                                </li>
                    <%
                            }
                    %>      </ul>
                            <h2>Upload a new picture</h2>
                            <form action="index.jsp" enctype="multipart/form-data" method="post">
                                Filename: <input type="file" name="filename"/>
                                <select name="album_id">
                                    <%
                                        for (Album album : foundAlbums) {
                                    %>
                                            <option value="<%= album.album_id %>"><%= album.name %></option>
                                    <%
                                        }
                                    %>
                                </select>
                                <br />
                                <input type="text" name="tags" placeholder="Enter tags seperated by commas" size=100 />
                                <br />
                                <div class="input-group">
                                    <input type="text" name="caption" placeholder="Enter caption" size=100 />
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="submit">Upload</button>
                                    </spa>
                                </div>
                            </form>
                            <%
                                PictureDao pictureDao = new PictureDao();
                                try {
                                    Picture picture = imageUploadBean.upload(request);
                                    if (picture != null) {
                                        int p_id = pictureDao.save(picture);
                                        TagController.setPhotoTags(p_id, picture.getTags());
                                    }
                                } catch (FileUploadException e) {
                                    e.printStackTrace();
                                }
                            %>
                            <h2>Existing pictures</h2>
                            <table>
                                <tr>
                                    <%
                                        List<Integer> pictureIds = pictureDao.allPicturesIds();
                                        for (Integer pictureId : pictureIds) {
                                    %>
                                    <td>
                                        <a href="/photoshare/photo.jsp?photo_id=<%= pictureId %>">
                                            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
                                        </a>
                                    </td>
                                    <%
                                        }
                                    %>
                                </tr>
                            </table>
                    <!-- END foundAlbum.size() > 0 -->
                    <%
                        } 
                    %>
                </div>
            </div>
        </div>
    </body>
</html>
