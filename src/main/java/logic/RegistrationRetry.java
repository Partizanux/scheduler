package logic;

public class RegistrationRetry {
	private static String errFName = "";
	private static String errLName = "";
	private static String errLog = "";
	private static String errEmail = "";
	private static String errPass = "";
	private static String errPass2 = "";

	public void setErrFName(String value) {
		errFName = value;
	}

	public String getErrFName() {
		return errFName;
	}

	public void setErrLName(String value) {
		errLName = value;
	}

	public String getErrLName() {
		return errLName;
	}

	public void setErrLog(String value) {
		errLog = value;
	}

	public String getErrLog() {
		return errLog;
	}

	public void setErrEmail(String value) {
		errEmail = value;
	}

	public String getErrEmail() {
		return errEmail;
	}

	public void setErrPass(String value) {
		errPass = value;
	}

	public String getErrPass() {
		return errPass;
	}

	public void setErrPass2(String value) {
		errPass2 = value;
	}

	public String getErrPass2() {
		return errPass2;
	}

	public static boolean regCheck(String fname, String lname, String log,
			String pass, String pass2, String email) {
		boolean OkAll = true;

		clearMessages();

		if (fname == null || fname.equals("")) {
			errFName = "Please enter your first name";
			OkAll = false;
		}
		if (lname == null || lname.equals("")) {
			errLName = "Please enter your last name";
			OkAll = false;
		}
		if (log == null || log.equals("")) {
			errLog = "Please enter your login";
			OkAll = false;
		}
		if (email == null || email.equals("")) {
			errEmail = "Please enter your email";
			OkAll = false;
		}
		if (pass == null || pass.equals("")) {
			errPass = "Please enter your password";
			OkAll = false;
		}
		if (pass2 == null || !pass.equals(pass2)) {
			errPass2 = "Please confirm your password";
			OkAll = false;
		}
		return OkAll;
	}

	public static void clearMessages() {
		errFName = "";
		errLName = "";
		errLog = "";
		errEmail = "";
		errPass = "";
		errPass2 = "";
	}

	public static void userExistsMsg() {
		errLog = "User with such LOGIN already exists";
	}

	public static void userIncorrectLoginMsg() {
		errLog = "Incorrect LOGIN";
	}
}
