<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id = "regRetry" class = "logic.RegistrationRetry" scope = "request"/>

<html>
<head>
<title>Retry</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>

<body>
<form name = "retryForm" action="Manager" method="post">
<input type="hidden" name="command" value="registration"/>
<center>
<table  class="inde" >

<th colspan=2>
USER   REGISTRATION
<p>* Required Fields</p>
</th>

<tr><td align=left>
First Name*:
</td>
<td>
<input type="text" name="firstName" value="<%=request.getParameter("firstName")%>" maxlength=20><br>
<span class="advise"><%=regRetry.getErrFName()%></span>
</td>
</tr>

<tr><td align=left>
Last Name*:
</td>
<td>
<input type="text" name="lastName" value="<%=request.getParameter("lastName")%>" maxlength=20><br>
<span class="advise"><%=regRetry.getErrLName()%></span>
</td>
</tr>

<tr><td align=left>
Login*:
</td>
<td>
<input type="text" name="login" value="<%=request.getAttribute("loginAtt")%>" maxlength=20><br>
<span class="advise"><%=regRetry.getErrLog()%></span>
</td>
</tr>

<tr><td align=left>
E-mail*:
</td>
<td>
<input type="text" name="email" value="<%=request.getParameter("email")%>" maxlength=20><br>
<span class="advise"><%=regRetry.getErrEmail()%></span>
</td>
</tr>

<tr><td align=left>
password*:
</td>
<td>
<input type="password" name="pass" value="<%=request.getParameter("pass")%>" maxlength=20><br>
<span class="advise"><%=regRetry.getErrPass()%></span>
</td>
</tr>

<tr><td align=left>
confirm password*:
</td>
<td>
<input type="password" name="pass2" value="" maxlength=20><br>
<span class="advise"><%=regRetry.getErrPass2()%></span>
</td>
</tr>

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