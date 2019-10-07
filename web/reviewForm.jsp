<%@ page import="com.dey.sayantan.thrillio.entities.Bookmark" %>
<%@ page import="com.dey.sayantan.thrillio.managers.BookmarkManager" %><%--
  Created by IntelliJ IDEA.
  User: Sayantan
  Date: 11/28/2018
  Time: 12:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;">
    <br>
    <b><a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>
</div>
<br><br>
<form method="POST" action="<%=request.getContextPath()%>/browse/mybooks/review/submitReview?bid=<%=request.getParameter("bid")%>&type=<%=request.getParameter("type")%>">
    <fieldset
            style="border: 1px solid rgb(204, 204, 204); width: 600px; margin: auto;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
        <table cellpadding="10px" align="center">
            <%long bid = Long.valueOf(request.getParameter("bid"));
            String type = request.getParameter("type");
            Bookmark bookmark = BookmarkManager.getInstance().getBookmark((long)request.getSession().getAttribute("userId"),bid,type);%>
            <tr>
                <td><label>Title:</label></td>
                <td>
                    <%=bookmark.getTitle()%><br>
                </td>
            </tr>
            <tr>
                <td><label>Review:</label></td>
                <td>
                    <textarea rows="7" cols="50" name="review"></textarea>
                </td>
            </tr>
            <tr>
                <td align="center" colspan="2"><input type="submit" name="submitUserReview" value="Submit"></td>
                <td style="color: dimgrey" align="center" colspan="2"><input type="button" name="cancelBtn" value="Cancel"></td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>