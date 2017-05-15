package org.web.cape.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.web.cape.entity.MgrUser;

public interface MgrUserDao extends PagingAndSortingRepository<MgrUser, Long>,JpaSpecificationExecutor<MgrUser> {
	MgrUser findByLoginName(String loginName);
	
	@Query("select u from MgrUser u where roles in (:con)")
	List<MgrUser> findByCondition(@Param("con")String[] con);
	
	MgrUser findById(long id);
	
	@Query("select max(func.id)+1 from MgrUser func")
	long getNextId();
	
 
}
