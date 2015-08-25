package logic;

public class UTasks {
	private String date;
	private String time;
	private String taskmsg;
	private String status;
	private Integer idt;
	private String notification;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTaskmsg() {
		return taskmsg;
	}

	public void setTaskmsg(String taskmsg) {
		this.taskmsg = taskmsg;
	}

	public String getStatus() {// isStatus for boolean
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIdt() {
		return idt;
	}

	public void setIdt(Integer idt) {
		this.idt = idt;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

}
