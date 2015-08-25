package logic;

import java.util.List;

public class Schedule {

	String login = "";

	public void setLogin(String str) {
		login = str;
	}

	public String getLogin() {
		return login;
	}

	public List<UTasks> getListTasks() {
		return ActionLogic.getTasks(login);
	}
}
