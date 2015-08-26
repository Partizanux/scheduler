<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<title>main</title>
</head>

<body>
<form name = "taskForm" action="Manager" method="post">
<input type="hidden" name="command" value="addTask"/>
<center>
<table cellpadding=4 cellspacing=1 border=0>



<th bgcolor="#c0c0c0" colspan=2>
<font size=5>Hello <%=session.getAttribute("login")%></font>
<br><font size=5>Now you can add your tasks</font>
</th>

<tr bgcolor="#c0c0c0">
<td align = "center"> <b> Time </b></td>
<td align = "center"> <b> Task </b></td>
</tr>

<tr bgcolor="#c0c0c0">
<td align = "center">
<div align="center">day/month/year</div>
<input type="text" name="dd" value="" size=2 maxlength=2 required> / <input type="text" name="mm" value="" size=2 maxlength=2 required> / <input type="text" name="yy" value="" size=4 maxlength=4 required>
<div align="center"> hours : minutes</div>
<input type="text" name="hours" value="" size=2 maxlength=2 required> : <input type="text" name="minutes" value="" size=2 maxlength=2 required>
</td>

<td align = "center">
<textarea name="task" value="" cols="50" rows="4" maxlength=300 accept-charset="UTF-8" required></textarea></td>
</tr>

<tr bgcolor="#c0c0c0">
<td  align=center colspan=2>
<input type="submit" value="Add"><font> </font><input type="reset" value="Reset"></form><font> </font>
<form id = "viewFrm" action="Manager" method="post">
<input type="hidden" name="command" value="viewTask"/>
<button type="submit" form="viewFrm">View tasks</button>
</form>

</td>
</tr>

</table>
</center>

</body>

</html>