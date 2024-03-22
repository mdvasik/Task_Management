package com.snm.techcraft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snm.techcraft.dto.LoginRequest;
import com.snm.techcraft.model.UserInfo;
import com.snm.techcraft.service.UserService;
import com.snm.techcraft.util.SNMResponse;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/addUser")
	@CrossOrigin
	public SNMResponse addUser(@RequestBody UserInfo user) {

		return userService.addUser(user);

	}

	@CrossOrigin
	@GetMapping("/allusers")
	public SNMResponse getAllUser() {
		return userService.getAllUsers();

	}

	@CrossOrigin
	@PutMapping("/edituser/{id}")
	public SNMResponse editUser(@PathVariable int id ,@RequestBody UserInfo updatedUserInfo) {
		return userService.editUser(id,updatedUserInfo);
		
	}
	@CrossOrigin
	@DeleteMapping("/deleteuser/{id}")
	public SNMResponse deleteUser(@PathVariable int id ) {
		return userService.deleteUser(id);
	}
	@CrossOrigin
	@PostMapping("/login")
	public SNMResponse login(@RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest);
		
	}
	
	 @GetMapping("/getuserbyid/{id}")
	    public SNMResponse getUserById(@PathVariable int id) {
	        return userService.getUserById(id);
	    }
	
}
