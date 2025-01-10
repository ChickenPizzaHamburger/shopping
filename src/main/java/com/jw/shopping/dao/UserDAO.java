package com.jw.shopping.dao;

import com.jw.shopping.dto.User;

public interface UserDAO {

	public boolean signup(final User user);
	public boolean isUserIdDuplicate(final String userId);
	public boolean isEmailDuplicate(final String email);
	public String getPasswordByUserId(final String userId);
	public User getUserById(final String id);
	
}
