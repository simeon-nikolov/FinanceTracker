package view.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserViewModel {

	@NotNull
	@Size(min = 6, max = 15)
    private String username;
	
    @NotNull
    @Size(min = 6, max = 30)
//    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])")
    private String password;
    	
    @NotNull
    @Size(min = 6, max = 30)
//    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])")
    private String confirmPassword;
    
    @NotEmpty
    @NotNull
    @Email
    @Pattern(regexp = "(^\\S+@\\S+\\.\\S+$)", message = "Not valid e-mail address!")
    private String email;
    
  	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
    
}
