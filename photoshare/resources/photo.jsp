<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.UserController" %>
<%@ page import="photoshare.User" %>
<%@ page import="photoshare.TagController" %>
<%@ page import="photoshare.Tag" %>
<%@ page import="photoshare.Album" %>
<%@ page import="photoshare.AlbumController" %>
<%@ page import="photoshare.Comment" %>
<%@ page import="photoshare.CommentController" %>
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
            <% if (request.getParameter("photo_id") != null) {
                    int photo_id = Integer.parseInt(request.getParameter("photo_id"));
                    if (request.getParameter("comment_text") != null && request.getUserPrincipal() != null) {
                        int user_id = UserController.getUserIdByEmail(request.getUserPrincipal().getName());
                        CommentController.makeComment(user_id, photo_id, request.getParameter("comment_text"));
                        out.println("IN HERE " + request.getParameter("comment_text"));
                    }
                    PictureDao pictureDao = new PictureDao();
                    Picture pic = pictureDao.load(photo_id);
                    List<Comment> comments = CommentController.getCommentsForPhoto(pic.getId());
            %>
                <div class="row">
                    <div class="col-sm-6">
                        <img src="/photoshare/img?picture_id=<%= photo_id %>" />
                    </div>
                    <div class="col-sm-6">
                        <ul class="list-group">
                            <li class="list-group-item disabled">Comments</li>
                        <% 
                            for (Comment comment : comments) {
                        %>
                                <li class="list-group-item"> 
                                    <p class="text-left">
                                        <a href="/photoshare/user.jsp?user_id=<%= comment.user_id%>">
                                            <%= comment.first_name %> <%= comment.last_name %>
                                        </a>
                                    </p>
                                    <p class="text-right"><i>"<%= comment.text %>"</i></p> 
                                </li>
                        <%
                            }
                        %>
                            <li class="list-group-item">
                                Add comment: 
                                <div class="input-group">
                                    <form action="photo.jsp">
                                        <input type="text" class="form-control" placeholder="Add comment" name="comment_text">
                                        <input type="hidden" name="photo_id" value="<%= photo_id %>">
                                        <span class="input-group-btn">
                                            <button class="btn btn-default" type="submit">Comment</button>
                                        </span>
                                    </form>
                                </div>
                            </li>
                        </ul>
                    </div>
            <%
                } else {
            %>
                    <h2>Sorry, looks like that photo doesn't exist!</h2>
            <%
                }
            %>
        </div>
    </body>
</html>