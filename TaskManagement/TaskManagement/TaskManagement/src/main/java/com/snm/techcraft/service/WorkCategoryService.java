package com.snm.techcraft.service;

import com.snm.techcraft.model.WorkCategory;
import com.snm.techcraft.util.SNMResponse;

public interface WorkCategoryService {

	SNMResponse getAllWorkCategories();

	SNMResponse addWorkCategory(WorkCategory workCategory);

	SNMResponse updateWorkCategory(int workCategoryId, WorkCategory workCategory);

	SNMResponse deleteWorkCategory(int workCategoryId);

	SNMResponse getById(Integer workCategoryId);

}
