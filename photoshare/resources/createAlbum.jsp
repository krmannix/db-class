<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.AlbumController" %>
<%@ page import="photoshare.UserController" %>
<%@ page import="photoshare.User" %>
<%@ page import="photoshare.Album" %>
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
            <h2><a href="/photoshare/index.jsp">Go back</a></h2>
            <%
                int self_id = UserController.getUserIdByEmail(request.getUserPrincipal().getName());
                if (request.getParameter("album_name") != null) {
                    AlbumController.makeAlbum(self_id, request.getParameter("album_name"));
                }
                List<Album> foundAlbums = AlbumController.getAlbums(self_id);   
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
                    %>     
                </ul>
        </div>
    </body>
</html>





