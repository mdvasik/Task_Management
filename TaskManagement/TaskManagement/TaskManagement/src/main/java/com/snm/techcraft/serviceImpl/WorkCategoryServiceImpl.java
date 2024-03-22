package com.snm.techcraft.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.snm.techcraft.model.WorkCategory;
import com.snm.techcraft.repository.WorkCategoryRepository;
import com.snm.techcraft.service.WorkCategoryService;
import com.snm.techcraft.util.SNMResponse;

@Service
public class WorkCategoryServiceImpl implements WorkCategoryService {

	@Autowired
	private WorkCategoryRepository workCategoryRepo;

	@Override
	public SNMResponse getAllWorkCategories() {
		List<WorkCategory> workCategory = workCategoryRepo.findAll();
		return new SNMResponse("All Work List", 200, workCategory);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUBADMIN')")
	public SNMResponse addWorkCategory(WorkCategory workCategory) {
		if (workCategoryRepo.existsByName(workCategory.getName())) {
			return new SNMResponse("Work category with the same name already exists", HttpStatus.BAD_REQUEST.value(),
					null);
		}
		WorkCategory savedWorkCategory = workCategoryRepo.save(workCategory);
		return new SNMResponse("Work category added successfully",200, savedWorkCategory);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUBADMIN')")
	public SNMResponse updateWorkCategory(int workCategoryId, WorkCategory workCategory) {
		Optional<WorkCategory> optionalWorkCategory = workCategoryRepo.findById(workCategoryId);
		if (optionalWorkCategory.isEmpty()) {
			return new SNMResponse("Work category with ID " + workCategoryId + " not found", HttpStatus.NOT_FOUND.value(), null);
		}

		WorkCategory existingWorkCategory = optionalWorkCategory.get();

		existingWorkCategory.setName(workCategory.getName());

		WorkCategory updatedWorkCategory = workCategoryRepo.save(existingWorkCategory);

		return new SNMResponse("Work category with ID " + workCategoryId + " updated successfully", HttpStatus.OK.value(),
				updatedWorkCategory);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUBADMIN')")
	public SNMResponse deleteWorkCategory(int workCategoryId) {
		Optional<WorkCategory> optionalWorkCategory = workCategoryRepo.findById(workCategoryId);
		if (optionalWorkCategory.isEmpty()) {
			return new SNMResponse("Work category with ID " + workCategoryId + " not found", HttpStatus.NOT_FOUND.value(), null);
		}

		workCategoryRepo.deleteById(workCategoryId);

		return new SNMResponse("Work category with ID " + workCategoryId + " deleted successfully", HttpStatus.OK.value(), null);
	}

	@Override
	public SNMResponse getById(Integer workCategoryId) {
	Optional<WorkCategory> optionalWorkCategory =	workCategoryRepo.findById(workCategoryId);
	
	
	if(optionalWorkCategory.isEmpty()) {
		return new SNMResponse("Work category with ID " + workCategoryId + " not found", HttpStatus.NOT_FOUND.value(), null);
	}
	
Optional<WorkCategory>  workCategory =	workCategoryRepo.findById(workCategoryId);
	
		return new SNMResponse("Success",200,workCategory);
	}

}
