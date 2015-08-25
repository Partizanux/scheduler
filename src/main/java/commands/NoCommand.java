package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCommand implements Command {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("errorPageMsg", "NoCommand");
		String page = "/error.jsp";
		return page;
	}

}
