<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
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
                            <li class="list-group-item"><a href="#">View Your Friends</a></li>
                            <li class="list-group-item"><a href="#">View Your Tags</a></li>
                            <li class="list-group-item"><a href="#">View All Tags</a></li>
                            <li class="list-group-item"><a href="#">View Popular Tags</a></li>
                            <li class="list-group-item"><a href="#">Recommendations</a></li>
                        </ul>
                      </div>
                    </div>
                </div>
                <div class="col-sm-9">
                    <h2>Upload a new picture</h2>
                    <form action="index.jsp" enctype="multipart/form-data" method="post">
                        Filename: <input type="file" name="filename"/>
                        <input type="submit" value="Upload"/><br/>
                    </form>
                    <%
                        PictureDao pictureDao = new PictureDao();
                        try {
                            Picture picture = imageUploadBean.upload(request);
                            if (picture != null) {
                                pictureDao.save(picture);
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
                                <a href="/photoshare/img?picture_id=<%= pictureId %>">
                                    <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
                                </a>
                            </td>
                            <%
                                }
                            %>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
