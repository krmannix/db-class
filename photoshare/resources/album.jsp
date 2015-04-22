<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.UserController" %>
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
        		if (request.getParameter("album_id") != null) {
        			int album_id = Integer.parseInt(request.getParameter("album_id"));
	                PictureDao pictureDao = new PictureDao();
	                Album thisAlbum = AlbumController.getAlbum(album_id);
	                List<Picture> allPics = pictureDao.getPicturesByAlbumId(album_id);
	        %>
	        		<h2><%= thisAlbum.name %></h2>
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
        	%>
        		<h2>Please select an album. <a href="/photoshare/index.jsp">Go back</a></h2>
        	<%
        		}
        	%>
        </div>
    </body>
</html>