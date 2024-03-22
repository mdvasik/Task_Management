package com.snm.techcraft.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.snm.techcraft.model.Role;
import com.snm.techcraft.service.RoleService;
import com.snm.techcraft.util.SNMResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public SNMResponse addRole(Role role) {

		log.info("Inside Role ADD Service Method");
		roleRepository.save(role);
		return new SNMResponse("Role added Successfully", HttpStatus.OK.value(), null);
	}

	@Override
	public SNMResponse getAllRoles() {

		log.info("Inside Get All Roles Service Method");
		List<Role> listofRoles = roleRepository.findAll();
		return new SNMResponse("List of Roles", HttpStatus.OK.value(), listofRoles);
	}

}
