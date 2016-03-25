package dao;

import model.User;

public class Demo {
	
	public static void main(String[] args) {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getUserById(2);
		System.out.println(user.getUsername());
	}

}
