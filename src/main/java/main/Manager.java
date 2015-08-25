package main;

import java.io.IOException;
import java.util.Timer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import logic.MailSender;
import logic.SendMailTimer;
import commands.Command;

public class Manager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Manager.class);


	public Manager() {
		super();
		Timer timer = new Timer();
		timer.schedule(new SendMailTimer(), 0L, 600000L);//check every 10 min
	}


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Command c = RequestManager.getCommand(request);
		String page = c.execute(request, response);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}