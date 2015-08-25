package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.ActionLogic;

public class DeleteTaskCommand implements Command {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = "/view.jsp";
		String iddel = request.getParameter("iddel");
		
		HttpSession session = null;

		if ((session = request.getSession(false)) == null)
			return "/index.jsp";
		String login = (String) session.getAttribute("login");

		ActionLogic.delTask(iddel, login);

		return page;
	}

}
