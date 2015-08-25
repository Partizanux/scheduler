package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ActionLogic;

public class MailNotifyCommand implements Command {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = "/view.jsp";
		String idnotify = request.getParameter("idnotify");
		String notify = request.getParameter("notify");
		
		if (request.getSession(false) == null)
			return "/index.jsp";

		ActionLogic.mailNotification(idnotify, notify);
		
		return page;
	}

}
