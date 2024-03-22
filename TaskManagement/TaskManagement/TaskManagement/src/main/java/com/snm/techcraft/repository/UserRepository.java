package com.snm.techcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snm.techcraft.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo,Integer>{

	UserInfo findByEmail(String email);

}
