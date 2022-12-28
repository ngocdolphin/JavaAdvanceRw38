package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vti.entity.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department>{
	//boolean existsByName(String name);
	Department findById(int id);
	@Query("SELECT count(d) > 0 FROM Department d WHERE d.name =:nameParameter")
	boolean existsByName(@Param("nameParameter") String name);
}
