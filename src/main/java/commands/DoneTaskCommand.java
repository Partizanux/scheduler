package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.ActionLogic;

public class DoneTaskCommand implements Command {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = "/view.jsp";
		String iddone = request.getParameter("iddone");

		HttpSession session = null;

		if ((session = request.getSession(false)) == null)
			return "/index.jsp";
		String login = (String) session.getAttribute("login");

		ActionLogic.doneTask(iddone, login);

		return page;
	}

}
