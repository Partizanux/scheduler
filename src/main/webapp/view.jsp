<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
<head>
<title>view</title>
</head>
<body>
<center>
<table cellpadding=4 cellspacing=1 border=0>
<th bgcolor="#c0c0c0" colspan=6>
<font size=5>Your task list, <%=session.getAttribute("login")%></font>
</th>

<tr bgcolor="#c0c0c0">
<td align = "center"> <b> Date </b></td>
<td align = "center"> <b> Time </b></td>
<td align = "center"> <b> Task </b></td>
<td align = "center"> <b> Status </b></td>
<td align = "center"> <b> Notify on email </b></td>
<td align = "center"> <b> Delete </b></td>
</tr>

<jsp:useBean id="vbean" class="logic.Schedule" scope="session"/>
<jsp:setProperty property="login" name="vbean">
	<jsp:attribute name="value"><%=session.getAttribute("login")%></jsp:attribute>
</jsp:setProperty>

<c:forEach var="tsks" items="${vbean.listTasks}">
    <tr>
        <td><c:out value="${tsks.date}"/></td>
        <td><c:out value="${tsks.time}"/></td>
        <td><c:out value="${tsks.taskmsg}"/></td>
        <td><form id = "doneFrm${tsks.idt}" action="Manager" method="post">
			<input type="hidden" name="command" value="doneTask"/>
			<input type="hidden" name="iddone" value="${tsks.idt}"/>
			<c:if test="${tsks.status eq 'not_done'}">
				<button type="submit" form="doneFrm${tsks.idt}"> done </button></form>
			</c:if>
			<c:if test="${tsks.status eq 'done'}">
				<button type="submit" form="doneFrm${tsks.idt}" disabled> done </button></form>
			</c:if>
        </td>
        <td>
        	<form id = "notifyFrm${tsks.idt}" action="Manager" method="post">
			<input type="hidden" name="command" value="MailNotificationOn"/>
			<input type="hidden" name="idnotify" value="${tsks.idt}"/>
			<input type="hidden" name="notify" value="${tsks.notification}"/>
			<c:if test="${tsks.notification eq 'no'}">
				<button type="submit" form="notifyFrm${tsks.idt}">yes</button></form></td>
			</c:if>
			<c:if test="${tsks.notification eq 'yes'}">
				<button type="submit" form="notifyFrm${tsks.idt}">no</button></form></td>
			</c:if>
			<c:if test="${tsks.notification eq 'was sent'}">
				<button type="submit" form="notifyFrm${tsks.idt}" disabled>was sent</button></form></td>
			</c:if>
		<td>
			<form id = "delFrm${tsks.idt}" action="Manager" method="post">
			<input type="hidden" name="command" value="deleteTask"/>
			<input type="hidden" name="iddel" value="${tsks.idt}"/>
			<button type="submit" form="delFrm${tsks.idt}"> delete </button></form>
		</td>
			
    </tr>
</c:forEach>

<tr bgcolor="#c0c0c0">
<td  align=center colspan=6>
<form id = "exitFrm" action="Manager" method="post">
<input type="hidden" name="command" value="exit"/>
<div align=right><button type="submit" form="exitFrm">Exit</button></div>
</form>
</td>
</tr>
</table>
</center>
</body>

</html>