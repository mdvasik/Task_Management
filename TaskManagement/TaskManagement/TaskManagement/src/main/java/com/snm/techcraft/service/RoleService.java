package com.snm.techcraft.service;

import com.snm.techcraft.model.Role;
import com.snm.techcraft.util.SNMResponse;

public interface RoleService {

	SNMResponse addRole(Role role);

	SNMResponse getAllRoles();

}
