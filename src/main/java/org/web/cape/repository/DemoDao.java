package org.web.cape.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.web.cape.entity.Demo;

public interface DemoDao extends PagingAndSortingRepository<Demo, String> , JpaSpecificationExecutor<Demo>{

	Demo findByCode(String code);
	
	@Query("select d from Demo d where code in (:codes)")
	List<Demo> findByConditions(String[] codes);
	
	@Query(value = "select * from example_demo where id = ?1", nativeQuery=true)
	Demo getDemoByNativeSql(String id);
	
	@Modifying
	@Query(value = "delete from example_demo where id = ?1", nativeQuery=true)
	void deleteDemoByIdUseSql(String id);
	
	@Modifying
	@Query(value = "delete from example_demo where id in (:ids)", nativeQuery=true)	
	void delBatch(@Param("ids") String[] ids) ;
}
