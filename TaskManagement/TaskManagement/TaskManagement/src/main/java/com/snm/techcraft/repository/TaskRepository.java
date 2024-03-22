package com.snm.techcraft.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snm.techcraft.model.Task;
import com.snm.techcraft.model.UserInfo;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	Optional<Task> findByName(String name);
	
	@Query("SELECT t FROM Task t WHERE t.name LIKE %:name%")
	List<Task> findByNameContaining(String name);

	@Query("SELECT t FROM Task t WHERE (:taskName is null or t.name = :taskName) "
	        + "AND (:workCategoryId is null or t.workCategory.id = :workCategoryId) "
	        + "AND (:subCategoryId is null or t.subCategory.id = :subCategoryId) "
	        + "AND (:client is null or t.client.id = :client) "
	        + "AND (:assignNameId is null or t.assignName.id = :assignNameId) "
	        + "AND (:taskStatus is null or t.status = :status) "
	        + "AND (:paymentStatus is null or t.paymentStatus = :paymentStatus)")
	List<Task> filterTasks(@Param("taskName") String name, @Param("workCategoryId") Integer workCategoryId,
	        @Param("subCategoryId") Integer subCategoryId, @Param("client") Integer client,
	        @Param("assignNameId") Integer assignNameId, @Param("taskStatus") String status,
	        @Param("paymentStatus") String paymentStatus);

	@Query("SELECT t FROM Task t WHERE (:status is null or t.status = :status) "
			+ "AND (:assignName is null or t.assignName = :assignName)")
	Page<Task> findByTaskStatusAndAssignName(@Param("taskStatus") String status,
			@Param("assignName") UserInfo assignName, Pageable pageable);

	@Query("SELECT t FROM Task t WHERE t.status = :status")
	Page<Task> findByTaskStatus(@Param("taskStatus") String status, Pageable pageable);

	@Query("SELECT t FROM Task t WHERE t.assignName = :assignName")
	Page<Task> findByAssignName(@Param("assignName") UserInfo assignName, Pageable pageable);

	@Query("SELECT " 
	               + "COUNT(CASE WHEN t.status = 'Pending' THEN 1 END), "
			       + "COUNT(CASE WHEN t.status = 'Complete' THEN 1 END) " 
	               + "FROM Task t WHERE t.assignName.id = :userId")
	List<Object[]> countPendingAndCompletedTasks(@Param("userId") int userId);


}
