package com.snm.techcraft.service;

import com.snm.techcraft.dto.LoginRequest;
import com.snm.techcraft.model.UserInfo;
import com.snm.techcraft.util.SNMResponse;

public interface UserService {
	
	
	SNMResponse addUser( UserInfo user);

	SNMResponse getAllUsers();

	SNMResponse editUser(int id, UserInfo updatedUserInfo);

	SNMResponse deleteUser(int id);

	SNMResponse login(LoginRequest loginRequest);

	SNMResponse getUserById(int id);
	
	
	

}
