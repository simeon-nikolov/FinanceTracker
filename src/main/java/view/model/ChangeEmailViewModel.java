package view.model;

public class ChangeEmailViewModel {
	private String newEmail;
	private String confirmNewEmail;
	private String enterPassword;
	
	public ChangeEmailViewModel() {}

	public ChangeEmailViewModel(String newEmail, String confirmNewEmail, String enterPassword) {
		this.newEmail = newEmail;
		this.confirmNewEmail = confirmNewEmail;
		this.enterPassword = enterPassword;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getConfirmNewEmail() {
		return confirmNewEmail;
	}

	public void setConfirmNewEmail(String confirmNewEmail) {
		this.confirmNewEmail = confirmNewEmail;
	}

	public String getEnterPassword() {
		return enterPassword;
	}

	public void setEnterPassword(String enterPassword) {
		this.enterPassword = enterPassword;
	}
}
