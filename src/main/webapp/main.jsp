<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>main</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>

<body>
	<form name="taskForm" action="Manager" method="post">
		<input type="hidden" name="command" value="addTask" />
		<center>
			<table class="addTable">



				<th colspan=2>Add your tasks <%=session.getAttribute("login")%>
				</th>

				<tr>
					<td align="left"> Date: </td>
					<td align="left"><input type="text"
						name="dd" placeholder="DD" value="" size=2 maxlength=2 required> - <input
						type="text" placeholder="MM" name="mm" value="" size=2 maxlength=2 required>
						- <input type="text" placeholder="YYYY" name="yy" value="" size=4 maxlength=4
						required>
						</td>
				</tr>
				<tr>
					<td align="left"> Time: </td>
					<td align="left"><input type="text"
						name="hours" placeholder="hh" value="" size=2 maxlength=2 required> : <input
						type="text" placeholder="mm" name="minutes" value="" size=2 maxlength=2 required>
					</td>
				</tr>

				<tr>
				<td align="left" id="eltask"> Task: </td>
					<td align="left"><textarea placeholder="please, no more 300 sym" 
							name="task" value="" cols="65"
							rows="10" maxlength=300 required></textarea></td>
				</tr>

				<tr>
					<td align=center colspan=2>
					<button type="submit">Add</button>
					<button type="reset">Reset</button>
					</form>
						<form id="viewFrm" action="Manager" method="post">
							<input type="hidden" name="command" value="viewTask" />
							<button id="buttonView" type="submit" form="viewFrm">View tasks</button>
						</form></td>
				</tr>

			</table>
		</center>
</body>

</html>