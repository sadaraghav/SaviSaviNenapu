package com.cisco.cmad.data;

import com.cisco.cmad.api.User;

public interface UserDAO {
	void createUser(User user);
	User readUser(String userId);
	void updateUser(User user);
	void deleteUser(String userId);
}
