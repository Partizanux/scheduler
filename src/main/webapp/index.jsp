<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>

<body>
<center>
<table class="inde">

<th id="welcome" colspan=2>
<span>WELCOME</span>
<div align="right"><a href="register.jsp">register now!</a></div>
</th>

<form name = "loginForm" action="Manager" method="post">
<input type="hidden" name="command" value="authentication"/>

<tr>
<td align="left">Login: </td>
<td align="left"><input type="text" name="login" value="" maxlength=20></td>
</tr>

<tr>
<td align="left">Password: </td>
<td align="left"><input type="password" name="pass" value="" maxlength=20></td>
</tr>

<tr>
<td  align="center" colspan="2">
<button type="submit">Log in</button>
</td>
</tr>

</table>
</center>
</form>
</body>
</html>