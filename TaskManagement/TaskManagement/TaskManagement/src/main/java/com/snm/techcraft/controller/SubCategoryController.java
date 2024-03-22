package com.snm.techcraft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snm.techcraft.model.SubCategory;
import com.snm.techcraft.service.SubCategoryService;
import com.snm.techcraft.util.SNMResponse;

@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {
	@Autowired
	private SubCategoryService subCategoryService;

	@CrossOrigin
	@GetMapping("/subCategoryList")
	public SNMResponse getSubCategoryList() {
		return subCategoryService.getSubCategoryList();
	}

	@CrossOrigin
	@PostMapping("/subCategory")
	public SNMResponse addSubCategory(@RequestBody SubCategory subCategory) throws Exception {
		return subCategoryService.addSubCategory(subCategory);
	}

	@CrossOrigin
	@DeleteMapping("/subCategorydelete/{subCategoryId}")
	public SNMResponse deleteSubCategory(@PathVariable int subCategoryId) {
		return subCategoryService.deleteSubCategory(subCategoryId);
	}

	@CrossOrigin
	@GetMapping("/subCategory/{subCategoryId}")
	public SNMResponse getSubCategoryById(@PathVariable int subCategoryId) {
		return subCategoryService.getSubCategoryById(subCategoryId);
	}

	@CrossOrigin
	@PostMapping("/saveSubCategories")
	public SNMResponse saveSubCategories(@RequestBody List<SubCategory> subCategories) {
		return subCategoryService.saveSubCategories(subCategories);
	}

	@GetMapping("/subcate/{subCategoryId}")
	public SNMResponse getSubCategoryById(@PathVariable Integer subCategoryId) {
		return subCategoryService.getSubCategoryById(subCategoryId);
	}

	@CrossOrigin
	@PutMapping("/editsubcategory/{subCategoryId}")
	public SNMResponse updateSubCategory(@PathVariable Integer subCategoryId,
			@RequestBody SubCategory updatedSubCategory) {
		return subCategoryService.updateSubCategory(subCategoryId, updatedSubCategory);
	}

	@GetMapping("/subcategoryList/{workCategoryId}")
	public List<SNMResponse> getSubCategoriesByWorkCategoryId(@PathVariable int workCategoryId) {
		return subCategoryService.getSubCategoriesByWorkCategoryId(workCategoryId);
	}
	
	@GetMapping("/subcategoryList")
	public SNMResponse getSubCategoriesByWorkId(@RequestParam int work_category_id) {
	    return subCategoryService.getSubCategoriesByWorkId(work_category_id);
	}

}
