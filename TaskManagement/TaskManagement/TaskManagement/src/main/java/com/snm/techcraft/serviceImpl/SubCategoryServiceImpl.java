package com.snm.techcraft.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snm.techcraft.model.SubCategory;
import com.snm.techcraft.model.WorkCategory;
import com.snm.techcraft.service.SubCategoryService;
import com.snm.techcraft.util.SNMResponse;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
	private SubCategoryRepository subCategoryRepo;

	@Override
	public SNMResponse getSubCategoryList() {
		Iterable<SubCategory> subCategories = subCategoryRepo.findAll();
		return new SNMResponse("Subcategory list retrieved successfully", 200, subCategories);
	}

	@Override
	public SNMResponse addSubCategory(SubCategory subCategory) {
		SubCategory savedSubCategory = subCategoryRepo.save(subCategory);
		return new SNMResponse("Subcategory added successfully", 201, savedSubCategory);
	}

	@Override
	public SNMResponse deleteSubCategory(int subCategoryId) {
		if (subCategoryRepo.existsById(subCategoryId)) {
			subCategoryRepo.deleteById(subCategoryId);
			return new SNMResponse("Subcategory deleted successfully", 200, null);
		} else {
			return new SNMResponse("Subcategory with ID " + subCategoryId + " not found", 404, null);
		}
	}

	@Override
	public SNMResponse getSubCategoryById(int subCategoryId) {
		return subCategoryRepo.findById(subCategoryId)
				.map(subCategory -> new SNMResponse("Subcategory found", 200, subCategory))
				.orElse(new SNMResponse("Subcategory with ID " + subCategoryId + " not found", 404, null));
	}

	@Override
	public SubCategory findByName(String name) {
		return subCategoryRepo.findByName(name);
	}

	@Override
	public SNMResponse saveSubCategories(List<SubCategory> subCategories) {
		try {
			for (SubCategory subCategory : subCategories) {
				if (subCategory.getName() == null || subCategory.getName().isEmpty()) {
					throw new Exception("Subcategory name is required");
				}

				// Get the WorkCategory for the SubCategory
				WorkCategory workCategory = subCategory.getWorkCategory();
				if (workCategory == null) {
					throw new Exception("WorkCategory is required for SubCategory");
				}

				// Set the association between SubCategory and WorkCategory
				subCategory.setWorkCategory(workCategory);

				// Save the SubCategory
				subCategoryRepo.save(subCategory);
			}
			return new SNMResponse("Subcategories saved successfully", 200, null);
		} catch (Exception e) {
			return new SNMResponse("Error", 500, e.getMessage());
		}
	}

	@Override
	public SNMResponse updateSubCategory(Integer subCategoryId, SubCategory updatedSubCategory) {
	    try {
	        Optional<SubCategory> optionalSubCategory = subCategoryRepo.findById(subCategoryId);
	        if (optionalSubCategory.isPresent()) {
	            SubCategory existingSubCategory = optionalSubCategory.get();

	            existingSubCategory.setName(updatedSubCategory.getName());
	            existingSubCategory.setWorkCategory(updatedSubCategory.getWorkCategory());

	            subCategoryRepo.save(existingSubCategory);

	            return new SNMResponse("SubCategory updated successfully", 200, null);
	        } else {
	            return new SNMResponse("SubCategory not found", 404, null);
	        }
	    } catch (Exception e) {
	        return new SNMResponse("Error updating SubCategory", 500, e.getMessage());
	    }
	}

	@Override
	public List<SNMResponse> getSubCategoriesByWorkCategoryId(int workCategoryId) {
		 return subCategoryRepo.findByWorkCategoryWorkCategoryId(workCategoryId);
	}

	@Override
	 public SNMResponse getSubCategoriesByWorkId(int work_category_id) {
	        List<SubCategory> subCategories = subCategoryRepo.findByWorkCategory_WorkCategoryId(work_category_id);
	        if (subCategories != null && !subCategories.isEmpty()) {
	            return new SNMResponse("Subcategories fetched successfully", 200, subCategories);
	        } else {
	            return new SNMResponse("No subcategories found for the provided work category ID", 404, null);
	        }
	    }
}
