<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>login</title>
</head>

<body>
<center>
<table cellpadding=10 cellspacing=2 border=0>

<th bgcolor="#c0c0c0"  align="left">
<font size=10>WELCOME</font>
<div align="right"><font size=2><a href="register.jsp">register now!</a></font></div>
</th>

<form name = "loginForm" action="Manager" method="post">
<input type="hidden" name="command" value="authentication"/>

<tr bgcolor="#c0c0c0"><td valign=top>
Login
<br>
<input type="text" name="login" value="" size=15 maxlength=20></td></tr>

<tr bgcolor="#c0c0c0"><td valign=top>
Password
<br>
<input type="password" name="pass" value="" size=15 maxlength=20></td></tr>

<tr bgcolor="#c0c0c0">
<td  align=center>
<input type="submit" value="Log in">
</td>
</tr>

</table>
</center>
</form>
</body>
</html>