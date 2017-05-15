package org.web.cape.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.web.cape.entity.MgrFunction;
import org.web.cape.entity.MgrRoleUser;
import org.web.cape.entity.MgrTreeVO;
import org.web.cape.entity.MgrUser;

@Component
public class MgrFunctionJdbcDao {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public List<MgrFunction> findAllFuncsByUserId(long userId) {
		String sql = "select * from mgr_function where isactive='Y' and id in (select func_id from mgr_role_func where role_id in (select role_id from mgr_role_user where user_id = ?))";
		List<MgrFunction> result = (List<MgrFunction>) jdbcTemplate.query(sql, new Object[] { userId }, BeanPropertyRowMapper.newInstance(MgrFunction.class));
		return result;
	}


}
