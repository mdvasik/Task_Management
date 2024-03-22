package com.snm.techcraft.serviceImpl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snm.techcraft.model.SubCategory;
import com.snm.techcraft.util.SNMResponse;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

	SubCategory findByName(String name);

	List<SNMResponse> findByWorkCategoryWorkCategoryId(int workCategoryId);

	List<SubCategory> findByWorkCategory_WorkCategoryId(int work_category_id);

}
