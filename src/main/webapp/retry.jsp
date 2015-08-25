<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id = "regRetry" class = "logic.RegistrationRetry" scope = "request"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Retry</title>
</head>

<body>
<form name = "retryForm" action="Manager" method="post">
<input type="hidden" name="command" value="registration"/>
<center>
<table cellpadding=10 cellspacing=2 border=0>

<th bgcolor="#c0c0c0">
<font size=5>USER   REGISTRATION</font>
<br>
<font size=1>* Required Fields</font>
</th>

<tr bgcolor="#c0c0c0"><td valign=top>
First Name*
<br>
<input type="text" name="firstName" value="<%=request.getParameter("firstName")%>" size=15 maxlength=20><br>
<font size=2 color=red><%=regRetry.getErrFName()%></font>
</td></tr>

<tr bgcolor="#c0c0c0"><td valign=top>
Last Name*
<br>
<input type="text" name="lastName" value="<%=request.getParameter("lastName")%>" size=15 maxlength=20><br>
<font size=2 color=red><%=regRetry.getErrLName()%></font>
</td></tr>

<tr bgcolor="#c0c0c0"><td valign=top>
Login*
<br>
<input type="text" name="login" value="<%=request.getAttribute("loginAtt")%>" size=10 maxlength=15><br>
<font size=2 color=red><%=regRetry.getErrLog()%></font>
</td></tr>

<tr bgcolor="#c0c0c0"><td valign=top>
E-mail*
<br>
<input type="text" name="email" value="<%=request.getParameter("email")%>" size=25 maxlength=30><br>
<font size=2 color=red><%=regRetry.getErrEmail()%></font>
</td></tr>

<tr bgcolor="#c0c0c0"><td valign=top>
password*
<br>
<input type="password" name="pass" value="<%=request.getParameter("pass")%>" size=10 maxlength=15><br>
<font size=2 color=red><%=regRetry.getErrPass()%></font>
</td></tr>

<tr bgcolor="#c0c0c0"><td valign=top>
confirm password*
<br>
<input type="password" name="pass2" value="<%=request.getAttribute("pass2Att")%>" size=10 maxlength=15><br>
<font size=2 color=red><%=regRetry.getErrPass2()%></font>
</td></tr>

<tr bgcolor="#c0c0c0">
<td  align=center>
<input type="submit" value="Submit"> <input type="reset" value="Reset">
</td>
</tr>

</table>
</center>
</form>
</body>
</html>