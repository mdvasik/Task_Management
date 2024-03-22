package com.snm.techcraft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snm.techcraft.model.WorkCategory;

public interface WorkCategoryRepository extends JpaRepository<WorkCategory, Integer> {

	boolean existsByName(String name);
	
	
	Optional<WorkCategory> findById(Integer workCategoryId);

}
