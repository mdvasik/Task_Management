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

import com.snm.techcraft.model.WorkCategory;
import com.snm.techcraft.service.WorkCategoryService;
import com.snm.techcraft.util.SNMResponse;

@RestController
@RequestMapping("/api/category")
public class WorkCategoryController {

	@Autowired
	private WorkCategoryService workCategoryService;

	@CrossOrigin
	@GetMapping("/all")
	public SNMResponse getAllWorkCategories() {
		return workCategoryService.getAllWorkCategories();

	}

	@CrossOrigin
	@PostMapping("/add")
	public SNMResponse addWorkCategory(@RequestBody WorkCategory workCategory) {
		return workCategoryService.addWorkCategory(workCategory);
	}

	@CrossOrigin
	@PutMapping("/update/{workCategoryId}")
	public SNMResponse updateWorkCategory(@PathVariable int workCategoryId, @RequestBody WorkCategory workCategory) {
		return workCategoryService.updateWorkCategory(workCategoryId, workCategory);
	}

	@CrossOrigin
	@DeleteMapping("/delete/{workCategoryId}")
	public SNMResponse deleteWorkCategory(@PathVariable int workCategoryId) {
		return workCategoryService.deleteWorkCategory(workCategoryId);
	}
	@CrossOrigin
	@GetMapping("getworkCate/{workCategoryId}")
	public SNMResponse getById(@PathVariable Integer workCategoryId) {
		return workCategoryService.getById(workCategoryId);
	}
}
