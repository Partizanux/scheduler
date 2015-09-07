<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>view</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>
<body>
<center>
<table class="view" cellpadding=4 cellspacing=1 border=0>
<th colspan=6>
Your task list, <%=session.getAttribute("login")%>
</th>
   <col class="col12" span="2">
   <col class="col3">
   <col class="col456" span="3">
<tr class="header">
<td align = "center"> Date </td>
<td align = "center"> Time </td>
<td align = "center"> Task </td>
<td align = "center"> Status </td>
<td align = "center"> Notify on email </td>
<td align = "center"> Delete </td>
</tr>

<jsp:useBean id="vbean" class="logic.Schedule" scope="session"/>
<jsp:setProperty property="login" name="vbean">
	<jsp:attribute name="value"><%=session.getAttribute("login")%></jsp:attribute>
</jsp:setProperty>

<c:forEach var="tsks" items="${vbean.listTasks}">
    <tr>
        <td><c:out value="${tsks.date}"/></td>
        <td><c:out value="${tsks.time}"/></td>
        <td class="task"><c:out value="${tsks.taskmsg}"/></td>
        <td><form id = "doneFrm${tsks.idt}" action="Manager" method="post">
			<input type="hidden" name="command" value="doneTask"/>
			<input type="hidden" name="iddone" value="${tsks.idt}"/>
			<c:if test="${tsks.status eq 'not_done'}">
				<button type="submit" form="doneFrm${tsks.idt}"> done </button></form>
			</c:if>
			<c:if test="${tsks.status eq 'done'}">
				<button class="bdisabled" type="submit" form="doneFrm${tsks.idt}" disabled> done </button></form>
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
				<button class="bdisabled" type="submit" form="notifyFrm${tsks.idt}" disabled>was sent</button></form></td>
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
		<td align=center colspan=3>
			<c:set var="contextPath" value="${pageContext.request.contextPath}" />
			<div align=left>
					<a href="${contextPath}/main.jsp"><button type="button">add
							task</button></a>
			</div>
		</td>
		<td align=center colspan=3>
			<form id="exitFrm" action="Manager" method="post">
				<input type="hidden" name="command" value="exit" />
				<div align=right>
					<button type="submit" form="exitFrm">Exit</button>
				</div>
			</form>
		</td>
	</tr>
</table>
</center>
</body>

</html>