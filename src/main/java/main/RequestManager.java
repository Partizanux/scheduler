package main;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import commands.AddTaskCommand;
import commands.Command;
import commands.DeleteTaskCommand;
import commands.DoneTaskCommand;
import commands.ExitCommand;
import commands.LoginCommand;
import commands.MailNotifyCommand;
import commands.NoCommand;
import commands.RegCommand;
import commands.ViewTaskCommand;

public class RequestManager {
	private static HashMap<String, Command> commands = new HashMap<String, Command>();

	static {
		commands.put("registration", new RegCommand());
		commands.put("authentication", new LoginCommand());
		commands.put("addTask", new AddTaskCommand());
		commands.put("viewTask", new ViewTaskCommand());
		commands.put("doneTask", new DoneTaskCommand());
		commands.put("deleteTask", new DeleteTaskCommand());
		commands.put("exit", new ExitCommand());
		commands.put("MailNotificationOn", new MailNotifyCommand());
	}

	public static Command getCommand(HttpServletRequest request) {
		String cmd = request.getParameter("command");
		Command command = commands.get(cmd);

		if (command == null) {
			command = new NoCommand();
		}

		return command;
	}

}
