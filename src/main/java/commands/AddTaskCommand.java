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
		
		byte[] bytes = task.getBytes(StandardCharsets.ISO_8859_1);
		for(int i = 0; i < bytes.length; i++){
			System.out.print(bytes[i]);
		}
		task = new String(bytes, StandardCharsets.UTF_8);
		
		HttpSession session = request.getSession(false);
		String l = (String) session.getAttribute("login");
		try {
			ActionLogic.addTask(l, day, month, year, hour, minute, task, 0);
			// 0 - NOT DONE status, default value
			
			page = "/view.jsp";
		} catch (FmtDataException fde) {
			request.setAttribute("errorPageMsg",
					"FmtDataException: input data is not correct");
			page = "/error.jsp";//main.jsp
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorPageMsg", "Exception e");
			page = "/error.jsp";
		}

		return page;
	}

}
