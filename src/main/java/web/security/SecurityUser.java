package web.security;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import exceptions.InvalidArgumentException;
import model.User;

public class SecurityUser extends User implements UserDetails
{

	private static final long serialVersionUID = 1L;
	public SecurityUser(User user) throws InvalidArgumentException {
		if(user != null)
		{
			this.setId(user.getId());
			this.setUsername(user.getUsername());
			this.setEmail(user.getEmail());
			this.setPassword(user.getPassword());
			this.setCurrency(user.getCurrency());
			this.setAdmin(user.isAdmin());
		}		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
		if (this.isAdmin()) {
			authorities.add(()->"ADMIN");
		}
		
		authorities.add(()->"USER");
		return authorities;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}	
}