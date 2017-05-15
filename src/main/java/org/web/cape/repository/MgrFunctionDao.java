package org.web.cape.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.web.cape.entity.MgrFunction;

public interface MgrFunctionDao extends PagingAndSortingRepository<MgrFunction, Long> , JpaSpecificationExecutor<MgrFunction>{
	@Query("select max(func.id)+1 from MgrFunction func")
	long getNextId();
}
