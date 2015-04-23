<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.FriendController" %>
<%@ page import="photoshare.UserController" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.Picture" %>
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
                PictureDao pictureDao = new PictureDao();
                if (request.getParameter("search_term") != null) {
                    List<Integer> pictureIds = pictureDao.allPicturesByTag(request.getParameter("search_term"));
                    if (pictureIds.size() == 0) {
            %>
                        <h2>No photos match your search!</h2>
            <%
                    } else {
                        for (Integer pictureId : pictureIds) {
            %>
                            <td>
                                <a href="/photoshare/photo.jsp?photo_id=<%= pictureId %>">
                                    <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
                                </a>
                            </td>
            <%
                        }
                    }
                } else {
                    List<Integer> pictureIds = pictureDao.allPicturesIds();
                    if (pictureIds.size() == 0) {
            %>
                        <h2>No photos match your search!</h2>
            <%
                    } else {
                        for (Integer pictureId : pictureIds) {
            %>
                            <td>
                                <a href="/photoshare/photo.jsp?photo_id=<%= pictureId %>">
                                    <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
                                </a>
                            </td>
            <%
                        }
                    }
                }
            %>
        </div>
    </body>
</html>