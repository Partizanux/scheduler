package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExitCommand implements Command {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = "/index.jsp";
		HttpSession session = request.getSession(false);
		try {
			session.invalidate();
		} catch (NullPointerException e) {
			// session automatically disappear when server reloading
			// after this pushing "exit" button gives us NullPointerException
		}

		return page;
	}

}
