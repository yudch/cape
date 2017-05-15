package org.web.cape.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.web.cape.entity.DataTableDemo;


public interface DataTableDemoDao extends PagingAndSortingRepository<DataTableDemo, String> , JpaSpecificationExecutor<DataTableDemo>{

	DataTableDemo findByCode(String code);
	
	@Query("select d from DataTableDemo d where code in (:codes)")
	List<DataTableDemo> findByConditions(String[] codes);
	
	@Query(value = "select * from example_demo where id = ?1", nativeQuery=true)
	DataTableDemo getDemoByNativeSql(String id);
	
	@Modifying
	@Query(value = "delete from example_demo where id = ?1", nativeQuery=true)
	void deleteDemoByIdUseSql(String id);
}
