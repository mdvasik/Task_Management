package com.snm.techcraft.serviceImpl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snm.techcraft.model.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

	Role findByName(String string);

}
