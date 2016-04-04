package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import dao.DAOException;
import dao.IUserDAO;
import exceptions.InvalidArgumentException;
import model.User;
import web.security.SecurityUser;

@Component
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private IUserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = null;
		try {
			user = userDAO.getUserByUsername(username);
		} catch (DAOException e1) {		
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		
		if(user == null){
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		
		SecurityUser securityUser = null;
		
		try {
			securityUser = new SecurityUser(user);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		return securityUser;
	}
}
