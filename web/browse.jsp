<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.dey.sayantan.thrillio.constants.BookmarkType" %>
<%@ page import="com.dey.sayantan.thrillio.entities.Book" %>
<%@ page import="com.dey.sayantan.thrillio.entities.Movie" %>
<%@ page import="com.dey.sayantan.thrillio.entities.WebLink" %>
<%@ page import="java.util.Collection" %>
<%--
  Created by IntelliJ IDEA.
  User: Sayantan
  Date: 11/23/2018
  Time: 12:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thrill.IO</title>
    <link rel="stylesheet" href="style.css">
</head>
<body style="font-family:Arial;font-size:20px;"
      <c:if test="${requestScope.reg_success == true}">onload="myFunction()"</c:if>></body>
<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;">
    <br><b>
    <a href=""
       style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">Thrill.<i>IO</i></a></b>
    <div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
        <b>
            <a href="<%=request.getContextPath()%>/browse/mybooks?show=all"
               style="font-size:16px;color:white;margin-left:1170px;text-decoration:none;">My Books</a>
            <c:choose>
                <c:when test="${sessionScope.userId == 0}">
                    <a href="index.jsp" style="font-size:16px;color:white;margin-left:15px;text-decoration:none;">Log
                        In</a>
                </c:when>
            </c:choose>
            <a href="<%=request.getContextPath()%>/auth/logout"
               style="font-size:16px;color:white;margin-left:15px;text-decoration:none;">Logout</a>
        </b>
    </div>
</div>
<br><br>
<table>
    <%-------------------------------------------Books row-------------------------------%>
    <tr>
        <td style="font-size:18px; color: #0058A6;font-weight:bold;text-decoration: none">Books</td>
    </tr>
    <tr>
        <%
            Collection<Book> myBooks = (Collection<Book>) request.getAttribute("booksList");
            for (Book book : myBooks) {
        %>
        <td>
            <table cellpadding="5px" style="padding-bottom: 3%; box-shadow: 0 10px 16px 0 rgba(0,0,0,.30)">
                <tr>
                    <td colspan="2">
                        <img src="<%=book.getImageUrl()%>" width="150" height="200">
                    </td>
                </tr>
                <tr>
                    <td><span style="font-size: 14px">Title:</span></td>
                    <td><span
                            style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=book.getTitle()%></span>
                    </td>
                </tr>
                <%--<br>--%>
                <tr>
                    <td><span style="font-size: 14px">By:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=book.getAuthors()[0]%></span></td>
                </tr>
                <%--<br>--%>
                <tr>
                    <td><span style="font-size: 14px">Rating:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=book.getAmazonRating()%></span></td>
                </tr>
                <%--<br>--%>
                <tr>
                    <td><span style="font-size: 14px">Year:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=book.getPublicationYear()%></span></td>
                </tr>
                <%--<br><br>--%>
                <tr>
                    <td colspan="2">
                        <a href="<%=request.getContextPath()%>/browse/save?bid=<%=book.getId()%>&type=<%=BookmarkType.BOOK%>"
                           style="font-size:18px; color: #0058A6;font-weight:bold;text-decoration: none"><img
                                src="///png.icons8.com/bookmark/color/30" title="Save"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px;"></a>
                        <a href="#"
                           style="font-size:18px; color: gray;font-weight:bold;text-decoration: none"><img
                                src="///png.icons8.com/save_the_children/color/30" title="Mark as Kid-Friendly"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px"></a>
                        <a href="#"
                           style="font-size:18px; color: gray;font-weight:bold;text-decoration: none"><img
                                src="https://img.icons8.com/android/30/e74c3c/share.png" title="Share"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px"></a></td>
                </tr>
            </table>
        </td>
        <%
            }
        %>

    </tr>

    <%-------------------------------------------------------------Movies row---------------------------------------------     --%>

    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td style="font-size:18px; color: #0058A6;font-weight:bold;text-decoration: none">Movies</td>
    </tr>
    <tr>
        <%
            Collection<Movie> myMovies = (Collection<Movie>) request.getAttribute("moviesList");
            for (Movie movie : myMovies) {
        %>
        <td>
            <table cellpadding="5px" style="padding-bottom: 3%; box-shadow: 0 10px 16px 0 rgba(0,0,0,.30)">
                <tr>
                    <td colspan="2">
                        <img src="<%=movie.getImageUrl()%>" width="150" height="200">
                    </td>
                </tr>
                <tr>
                    <td><span style="font-size: 14px">Title:</span></td>
                    <td><span
                            style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=movie.getTitle()%></span>
                    </td>
                </tr>
                <%--<br>--%>
                <tr>
                    <td><span style="font-size: 14px">By:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=movie.getDirectors()[0]%></span></td>
                </tr>

                <tr>
                    <td><span style="font-size: 14px">Cast:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=movie.getCast()[0]%></span></td>
                </tr>
                <%--<br>--%>
                <tr>
                    <td><span style="font-size: 14px">Year:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=movie.getReleaseYear()%></span></td>
                </tr>
                <%--<br>--%>
                <tr>
                    <td><span style="font-size: 14px">Rating:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=movie.getImdbRating()%></span></td>
                </tr>
                <%--<br><br>--%>
                <tr>
                    <td colspan="2">
                        <a href="<%=request.getContextPath()%>/browse/save?bid=<%=movie.getId()%>&type=<%=BookmarkType.MOVIE%>"
                           style="font-size:18px; color: #0058A6;font-weight:bold;text-decoration: none"><img
                                src="///png.icons8.com/bookmark/color/30" title="Save"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px;"></a>
                        <a href="#"
                           style="font-size:18px; color: gray;font-weight:bold;text-decoration: none"><img
                                src="///png.icons8.com/save_the_children/color/30" title="Mark as Kid-Friendly"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px"></a>
                        <a href="#"
                           style="font-size:18px; color: gray;font-weight:bold;text-decoration: none"><img
                                src="https://img.icons8.com/android/30/e74c3c/share.png" title="Share"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px"></a></td>
                </tr>
            </table>
        </td>
        <%
            }
        %>

    </tr>


    <%--------------------------------------------------------web_links row---------------------------------------------%>

    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td style="font-size:18px; color: #0058A6;font-weight:bold;text-decoration: none">Web-Links</td>
    </tr>
    <tr>
        <%
            Collection<WebLink> myWeblinks = (Collection<WebLink>) request.getAttribute("weblinksList");
            for (WebLink webLink : myWeblinks) {
        %>
        <td>
            <table cellpadding="5px" style="padding-bottom: 3%; box-shadow: 0 10px 16px 0 rgba(0,0,0,.30)">
                <tr>
                    <td colspan="2">
                        <img src="<%=webLink.getImageUrl()%>" width="150" height="200">
                    </td>
                </tr>
                <tr>
                    <td><span style="font-size: 14px">Title:</span></td>
                    <td><span
                            style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=webLink.getTitle()%></span>
                    </td>
                </tr>
                <%--<br>--%>
                <tr>
                    <td><span style="font-size: 14px">URL:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=webLink.getUrl()%></span></td>
                </tr>

                <tr>
                    <td><span style="font-size: 14px">Host:</span></td>
                    <td><span style="font-size: 13px; color: #B13100;display: block;width: 115px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><%=webLink.getHost()%></span></td>
                </tr>
                <%--<br><br>--%>
                <tr>
                    <td colspan="2">
                        <a href="<%=request.getContextPath()%>/browse/save?bid=<%=webLink.getId()%>&type=<%=BookmarkType.WEB_LINK%>"
                           style="font-size:18px; color: #0058A6;font-weight:bold;text-decoration: none"><img
                                src="///png.icons8.com/bookmark/color/30" title="Save"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px;"></a>
                        <a href="#"
                           style="font-size:18px; color: gray;font-weight:bold;text-decoration: none"><img
                                src="///png.icons8.com/save_the_children/color/30" title="Mark as Kid-Friendly"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px"></a>
                        <a href="#"
                           style="font-size:18px; color: gray;font-weight:bold;text-decoration: none"><img
                                src="https://img.icons8.com/android/30/e74c3c/share.png" title="Share"
                                style="box-shadow:  0 5px 10px 0 rgba(0,0,0,.12); padding: 5px"></a></td>
                </tr>
            </table>
        </td>
        <%
            }
        %>

    </tr>
</table>

<c:if test="${requestScope.reg_success == true}">
    <div id="snackbar">Successfully Registered</div>
</c:if>
<script>
    function myFunction() {
        // Get the snackbar DIV
        var x = document.getElementById("snackbar");

        // Add the "show" class to DIV
        x.className = "show";

        // After 3 seconds, remove the show class from DIV
        setTimeout(function () {
            x.className = x.className.replace("show", "");
        }, 5000);
    }
</script>
</body>
</html>
