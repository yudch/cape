package org.web.cape.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DemoJdbcDao {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public void delBatch( final String[] ids) {

		String sql = " DELETE FROM example_demo where id = ? ";
		
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1,  ids[i] );
			}

			@Override
			public int getBatchSize() {
				return ids.length;
			}
		};

		jdbcTemplate.batchUpdate(sql, setter); 
	}
	 
}
