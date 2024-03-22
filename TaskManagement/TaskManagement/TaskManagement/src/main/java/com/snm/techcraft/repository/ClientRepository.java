package com.snm.techcraft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.snm.techcraft.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	@Query(value = "SELECT * FROM Client c " + "WHERE (CASE WHEN :searchValue IS NULL THEN true ELSE "
			+ "(c.name LIKE %:searchValue% OR CAST(c.mobile AS CHAR) LIKE %:searchValue% "
			+ "OR c.reference LIKE %:searchValue%) END) " + "AND c.status = true", nativeQuery = true)
	List<Client> findBySearchValue(@Param("searchValue") String searchValue);

	List<Client> findByStatus(boolean b);

}
