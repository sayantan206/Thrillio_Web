<%@ page import="com.dey.sayantan.thrillio.constants.BookmarkType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Sayantan
  Date: 11/24/2018
  Time: 6:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>thrill.io</title>
</head>
<body style="font-family:Arial;font-size:20px;">
<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;">
    <br><b>
    <a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>
    <div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
        <b>
            <a href="<%=request.getContextPath()%>/browse" style="font-size:16px;color:white;margin-left:1170px;text-decoration:none;">Browse</a>
            <a href="<%=request.getContextPath()%>/auth/logout" style="font-size:16px;color:white;margin-left:15px;text-decoration:none;">Logout</a>
        </b>
    </div>
</div>
<br><br>

<div style="font-size: 24px;color: #333333;padding: 15px 0px 0px;border-bottom: #333333 1px solid;clear: both;">Saved
    Items
</div>
<br><br>

<c:choose>
    <c:when test="${!empty(booksList)}">
        <table>
            <c:forEach var="userReview" items="${booksList}">
                <tr>
                    <td>
                        <img src="${userReview.imageUrl}" width="150" height="200">
                    </td>

                    <td style="color:gray;">
                        Title: <span style="color: #B13100;">${userReview.title}</span>
                        <br><br>
                        By <span style="color: #B13100;">${userReview.authors[0]}</span>
                        <br><br>
                        Rating: <span style="color: #B13100;">${userReview.amazonRating}</span>
                        <br><br>
                        Publication Year: <span style="color: #B13100;">${userReview.publicationYear}</span>
                        <br><br>
                        <a href = "<%=request.getContextPath()%>/browse/mybooks/delete?bid=${userReview.id}&type=<%=BookmarkType.BOOK%>" style = "font-size:18px; color: #0058A6;font-weight:bold;text-decoration: none">Delete</a>
                        <a href = "#" style = "font-size:18px; color: gray;font-weight:bold;text-decoration: none">Post a Review</a>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>

            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <br><br>
        <span style="font-size: 24px;color: #333333;margin:400px;">You haven't saved any items yet!</span>
    </c:otherwise>
</c:choose>
</body>
</html>
