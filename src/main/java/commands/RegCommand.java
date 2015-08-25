package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.ActionLogic;
import logic.RegistrationRetry;
import my_exceptions.IncorrectLoginException;
import my_exceptions.UserExistsException;


public class RegCommand implements Command {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String page = null;
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String log = request.getParameter("login");
		String pass = request.getParameter("pass");
		String pass2 = request.getParameter("pass2");
		String email = request.getParameter("email");

		if (!RegistrationRetry.regCheck(fname, lname, log, pass, pass2, email)) {

			page = "/retry.jsp";

			String l = request.getParameter("login");
			request.setAttribute("loginAtt", l);// save login when retry

			if (pass.equals(pass2)) {
				String p = request.getParameter("pass2");
				request.setAttribute("pass2Att", p);// save pass2 when retry
			} else {
				request.setAttribute("pass2Att", "");
			}

		} else {
			try {
				ActionLogic.registration(fname, lname, log, pass, email);
				HttpSession session = request.getSession(true);
				session.setAttribute("firstName", fname);
				session.setAttribute("lastName", lname);
				session.setAttribute("login", log);
				session.setAttribute("email", email);
				session.setAttribute("pass", pass);

				page = "/main.jsp";

			} catch (IncorrectLoginException el) {

				RegistrationRetry.userIncorrectLoginMsg();
				request.setAttribute("loginAtt", "");// erase login if user
														// exists
				page = "/retry.jsp";
			} catch (UserExistsException e) {
				RegistrationRetry.userExistsMsg();
				request.setAttribute("loginAtt", "");// erase login if user
														// exists
				page = "/retry.jsp";
			}
		}// else

		return page;
	}

}
