<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.dey.sayantan.thrillio.entities.Book" %>
<%@ page import="java.util.Collection" %><%--
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
<body style="font-family:Arial;font-size:20px;" onload="myFunction()">
<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;">
    <br><b>
    <a href=""
       style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">Thrill.<i>IO</i></a></b>
    <div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
        <b>
            <a href="<%=request.getContextPath()%>/browse/mybooks"
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
    <%
        Collection<Book> myBooks = (Collection<Book>) request.getAttribute("booksList");
        for (Book book : myBooks) {
    %>
    <tr>
        <td>
            <img src="<%=book.getImageUrl()%>" width="150" height="200">
        </td>

        <td style="color:gray;">
            Title: <span style="color: #B13100;"><%=book.getTitle()%></span>
            <br><br>
            By: <span style="color: #B13100;"><%=book.getAuthors()[0]%></span>
            <br><br>
            Rating: <span style="color: #B13100;"><%=book.getAmazonRating()%></span>
            <br><br>
            Publication Year: <span style="color: #B13100;"><%=book.getPublicationYear()%></span>
            <br><br>
            <a href="#" style="font-size:18px; color:gray;font-weight:bold;text-decoration: none">View</a>
            <a href="<%=request.getContextPath()%>/browse/save?bid=<%=book.getId()%>"
               style="font-size:18px; color: #0058A6;font-weight:bold;text-decoration: none">Save</a>
            <a href="#" style="font-size:18px; color: gray;font-weight:bold;text-decoration: none">Mark as
                Kid-Friendly</a>
            <a href="#" style="font-size:18px; color: gray;font-weight:bold;text-decoration: none">Share</a>

        </td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <%
        }
    %>
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
