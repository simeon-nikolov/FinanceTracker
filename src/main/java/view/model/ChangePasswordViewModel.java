package view.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordViewModel {
	@NotNull
	@NotEmpty
	private String oldPassword;
	@NotNull
	@NotEmpty
	@Size(min = 6, max = 30)
	private String newPassword;
	@NotNull
	@NotEmpty
	@Size(min = 6, max = 30)
	private String confirmNewPassword;
	
	public ChangePasswordViewModel() {}
	
	public ChangePasswordViewModel(String oldPassword, String newPassword, String confirmNewPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
}
