package com.snm.techcraft.service;

import java.util.List;

import com.snm.techcraft.model.SubCategory;
import com.snm.techcraft.util.SNMResponse;

public interface SubCategoryService {

	SNMResponse getSubCategoryList();

	SNMResponse addSubCategory(SubCategory subCategory);

	SNMResponse deleteSubCategory(int subCategoryId);

	SNMResponse getSubCategoryById(int subCategoryId);

	SubCategory findByName(String name);

	SNMResponse saveSubCategories(List<SubCategory> subCategories);

	SNMResponse updateSubCategory(Integer subCategoryId, SubCategory updatedSubCategory);

	List<SNMResponse> getSubCategoriesByWorkCategoryId(int workCategoryId);

	SNMResponse getSubCategoriesByWorkId(int work_category_id);
}
