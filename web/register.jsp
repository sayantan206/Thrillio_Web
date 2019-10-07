<%--
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
    <title>Login</title>
</head>
<body>
<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;">
    <br>
    <b><a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>
    <%--<div style="height:25px;background: #DB5227;font-family: Arial;color: white;">--%>
    <%--<b>--%>
    <%--<a href="<%=request.getContextPath()%>/guestLogin" style="font-size:16px;color:white;margin-left:1170px;text-decoration:none;">Browse</a>--%>
    <%--<a href="<%=request.getContextPath()%>/register" style="font-size:16px;color:white;margin-left:15px;text-decoration:none;">Register</a>--%>
    <%--</b>--%>
    <%--</div>--%>
</div>
<br><br>
<form method="POST" action="<%=request.getContextPath()%>/new&user">
    <fieldset
            style="border: 1px solid rgb(204, 204, 204); width: 600px; margin: auto;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
        <legend>Register</legend>
        <table cellpadding="10px" align="center">
            <tr>
                <td><label>First Name:</label></td>
                <td>
                    <input type="text" name="firstName" value="<c:if test="${sessionScope.firstName != null}">${sessionScope.firstName}</c:if>"><br>
                </td>
            </tr>
            <tr>
                <td><label>Last Name:</label></td>
                <td>
                    <input type="text" name="lastName" value="<c:if test="${sessionScope.lastName != null}">${sessionScope.lastName}</c:if>"><br>
                </td>
            </tr>
            <tr>
                <td><label>Email:</label></td>
                <td>
                    <input type="text" name="email" value="<c:if test="${sessionScope.email != null}">${sessionScope.email}</c:if>">
                        <c:if test="${requestScope.duplicate_email == true}">
                            <span style="color: red;">&nbsp;&nbsp;*Email already taken</span>
                        </c:if>
                </td>

            </tr>
            <tr>
                <td><label>Password:</label></td>
                <td>
                    <input type="password" name="password"><br>
                </td>
            </tr>
            <tr>
                <td><label>Gender:</label></td>
                <td>
                    <%--<form>
                        <input type="radio" name="gender" value="0" checked> Male
                        <input type="radio" name="gender" value="1"> Female
                        <input type="radio" name="gender" value="2"> Other
                    </form>--%>
                    <select name="gender">
                        <option value="0">Male</option>
                        <option value="1">Female</option>
                        <option value="2">Other</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label>User Type:</label></td>
                <td>
                    <form>
                        <input type="radio" name="userType" value="0" checked> User
                        <input type="radio" name="userType" value="1"> Editor
                        <input type="radio" name="userType" value="2"> Chief-Editor
                        <input type="radio" name="userType" value="3"> Email Admin
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:if test="${requestScope.is_valid == false}">
                        <span style="font-size:12px; color:red;font-weight:bold;text-decoration: none">*Please enter correct Email and Password*</span>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td align="center" colspan="2"><input type="submit" name="submitRegistrationForm" value="Submit"></td>
            </tr>
            <tr>
                <td align="center" colspan="2" style="color: dimgrey;">Or</td>
            </tr>
            <tr>
                <td style="color: dimgrey" align="center" colspan="2">Already a User ? &nbsp;&nbsp;<a href="index.jsp"
                                                                                                      style="text-decoration: underline">Log
                    In</a>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
