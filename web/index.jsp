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
    <div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
        <b>
            <a href="<%=request.getContextPath()%>/guestLogin" style="font-size:16px;color:white;margin-left:1170px;text-decoration:none;">Browse</a>
            <a href="<%=request.getContextPath()%>/user&register" style="font-size:16px;color:white;margin-left:15px;text-decoration:none;">Register</a>
        </b>
    </div>
</div>
<br><br>
<form method="POST" action="<%=request.getContextPath()%>/auth">
    <fieldset style="border: 1px solid rgb(204, 204, 204); width: 400px; margin: auto;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
        <legend>Log In</legend>
        <table cellpadding="10px" align="center">
            <tr>
                <td><label>Email:</label></td>
                <td>
                    <input type="text" name="email"><br>
                </td>
            </tr>
            <tr>
                <td><label>Password:</label></td>
                <td>
                    <input type="password" name="password"><br>
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
                <td align="center" colspan="2"><input type="submit" name="submitLoginForm" value="LogIn"></td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>