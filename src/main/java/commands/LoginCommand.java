package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.ActionLogic;

public class LoginCommand implements Command {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		String log = request.getParameter("login");
		String pass = request.getParameter("pass");

		if (log == null || log.equals("") || pass == null || pass.equals(""))
			return page = "/index.jsp";

		if (ActionLogic.checkLogin(log, pass)) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login", log);
			page = "/main.jsp";
		} else {
			page = "/index.jsp";
		}

		return page;
	}

}
