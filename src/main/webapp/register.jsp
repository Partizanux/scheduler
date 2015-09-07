<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Registration</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>

<body>
<form name = "registerForm" action="Manager" method="post">
<input type="hidden" name="command" value="registration"/>
<center>
<table class="inde" >

<th colspan=2>
USER   REGISTRATION
<p>* Required Fields</p>
</th>

<tr><td align=left>
First Name*:
</td><td>
<input type="text" name="firstName" value="" maxlength=20></td></tr>

<tr><td align=left>
Last Name*:
</td><td>
<input type="text" name="lastName" value="" maxlength=20></td></tr>

<tr><td align=left>
Login*:
</td><td>
<input type="text" name="login" value="" maxlength=15></td></tr>

<tr><td align=left>
E-mail*:
</td><td>
<input type="text" name="email" value="" maxlength=30></td></tr>

<tr><td align=left>
password*:
</td><td>
<input type="password" name="pass" value="" maxlength=15></td></tr>

<tr><td align=left>
confirm password*:
</td><td>
<input type="password" name="pass2" value="" maxlength=15></td></tr>

<tr>
<td align=center colspan=2>
<button type="submit">Submit</button> <button type="reset">Reset</button>
</td>
</tr>

</table>
</center>
</form>
</body>
</html>