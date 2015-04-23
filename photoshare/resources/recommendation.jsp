<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.UserController" %>
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
                if (request.getUserPrincipal() != null) {
                    PictureDao pictureDao = new PictureDao();
                    int user_id = UserController.getUserIdByEmail(request.getUserPrincipal().getName());
                    List<Integer> pictureIds = pictureDao.getRecommendedPictures(user_id);
                    for (Integer pictureId : pictureIds) {
            %>
                        <td>
                            <a href="/photoshare/photo.jsp?photo_id=<%= pictureId %>">
                                <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
                            </a>
                        </td>
            <%
                    }
                } else {
            %>
                <h2>You must be <a href="/photoshare/index.jsp">logged in</a> to see recommendations.</h2>
            <%
                }
            %>
        </div>
    </body>
</html>