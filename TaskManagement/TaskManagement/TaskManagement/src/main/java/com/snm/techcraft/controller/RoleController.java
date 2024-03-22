package com.snm.techcraft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snm.techcraft.model.Role;
import com.snm.techcraft.service.RoleService;
import com.snm.techcraft.util.SNMResponse;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/addrole")
	@CrossOrigin
	public SNMResponse addRoles(@RequestBody Role role) {
		return roleService.addRole(role);
		
	}
	
	@GetMapping("/allrole")
	@CrossOrigin
	public SNMResponse getAllRoles() {
		return roleService.getAllRoles();
		
	}
}
