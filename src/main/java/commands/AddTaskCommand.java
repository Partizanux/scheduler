package commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.ActionLogic;
import my_exceptions.FmtDataException;

public class AddTaskCommand implements Command {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String page = null;
		String day = request.getParameter("dd");
		String month = request.getParameter("mm");
		String year = request.getParameter("yy");
		String hour = request.getParameter("hours");
		String minute = request.getParameter("minutes");
		
		String task = request.getParameter("task");
		
		System.out.println("before encoding: " + task);
		System.out.println();
		
		byte[] bytes = task.getBytes(StandardCharsets.ISO_8859_1);
		task = new String(bytes, StandardCharsets.UTF_8);
		
		byte[] bytes16 = task.getBytes(StandardCharsets.UTF_16);
		String task16 = new String(bytes, StandardCharsets.UTF_8);
		
		byte[] bytes8 = task.getBytes(StandardCharsets.UTF_8);
		String task8 = new String(bytes, StandardCharsets.UTF_8);
		
		byte[] bytesASCII = task.getBytes(StandardCharsets.US_ASCII);
		String taskASCII = new String(bytes, StandardCharsets.US_ASCII);
		
		System.out.println("after encoding ISO_8859_1 : " + task);
		System.out.println("after encoding UTF_8: " + task8);
		System.out.println("after encoding UTF_16: " + task16);
		System.out.println("after encoding US_ASCII: " + taskASCII);
		
		
		
		HttpSession session = request.getSession(false);
		String l = (String) session.getAttribute("login");
		try {
			ActionLogic.addTask(l, day, month, year, hour, minute, task, 0);
			// 0 - NOT DONE status, default value
			
			page = "/view.jsp";
		} catch (FmtDataException fde) {
			request.setAttribute("errorPageMsg",
					"FmtDataException: input data is not correct");
			page = "/error.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorPageMsg", "Exception e");
			page = "/error.jsp";
		}

		return page;
	}

}
