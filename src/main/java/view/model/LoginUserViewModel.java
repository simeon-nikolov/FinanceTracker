package view.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginUserViewModel {

	@NotNull
	@Size(min = 6, max = 15)
    private String username;
	
    @NotNull
    @Size(min = 6, max = 30)
//    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%])")
    private String password;
    
    public LoginUserViewModel() {}
    
	public LoginUserViewModel(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
