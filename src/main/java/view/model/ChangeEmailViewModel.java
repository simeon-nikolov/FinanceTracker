package view.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class ChangeEmailViewModel {
    @NotNull
    @NotEmpty
    @Email
    @Pattern(regexp = "(^\\S+@\\S+\\.\\S+$)", message = "Not valid e-mail address!")
	private String newEmail;
    @NotNull
    @NotEmpty
    @Email
	private String confirmNewEmail;
    @NotNull
    @NotEmpty
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
