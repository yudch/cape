package org.web.cape.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.web.cape.entity.MgrRoleUser;
import org.web.cape.entity.MgrTreeVO;
import org.web.cape.entity.MgrUser;

@Component
public class MgrRoleJdbcDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public List<MgrTreeVO> findAllRolesByUserId(long userId) {
		
		if(!StringUtils.isEmpty(userId) && userId !=0 ){// 0 fasle 为选择   1true 选中
			String sql = " SELECT mgr_role.id,mgr_role.role_name as name,'' pid, 1 checked, 0 open"
					+ "  FROM  mgr_role left join mgr_role_user on mgr_role.id=mgr_role_user.role_id where mgr_role_user.user_id='"+userId+"'"
					+ "  UNION select  mrole.id,mrole.role_name as name,'' pid, 0 checked, 0 open from mgr_role mrole"
					+ "  WHERE  not exists(select mgr_role.id from mgr_role "
					+ "	 		left join mgr_role_user on mgr_role.id=mgr_role_user.role_id where mgr_role_user.user_id='"+userId+"'"
					+ "  		and mrole.id=mgr_role.id)";
					List<MgrTreeVO> result = (List<MgrTreeVO>) jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MgrTreeVO.class));
			
			return result;
					
		}else{
			String sql = " select  mgr_role.id,mgr_role.role_name as name,'' pid, 0 checked, 0 open from mgr_role ";
			List<MgrTreeVO> result = (List<MgrTreeVO>) jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(MgrTreeVO.class));
			
			return result;
		}
	}
	
	//待完善
	public void addRoleUser(MgrUser mgrUser){
		
		if(mgrUser!=null){
			 String ids[] = mgrUser.getRoleList().toArray(new String[0]);
			 
			 delUserRolesByUid(mgrUser.getId());
			 
			 addBatch(mgrUser,ids);
		}
	}
	
	private void addBatch(MgrUser mgrUser ,final String ids[]) {

		String sql = " insert into mgr_role_user(role_id, user_id) values( ?, '"+ mgrUser.getId() +"') ";
//		final long id = getNextId();
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
//				ps.setLong(1,  id);
				ps.setString(1,  ids[i]);
			}
			@Override
			public int getBatchSize() {
				return ids.length;
			}
		};

		jdbcTemplate.batchUpdate(sql, setter); 
	}
	/**
	 * 删除用户权限关系
	 * @param uid
	 */
	public void delUserRolesByUid(Long uid) {
		String sql = " delete from  mgr_role_user where user_id = '"+uid+"'";
		jdbcTemplate.execute(sql);
	}
	public List<MgrRoleUser>  getRoleUser(String uid){
		String sql = "select * from mgr_role_user where user_id='"+uid+"'";
		List<MgrRoleUser> result = (List<MgrRoleUser>) jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MgrRoleUser.class));
		return result;
	}
	
	public long getNextId(){
		String sql = "select max(id) + 1 from mgr_role_user";
		return jdbcTemplate.queryForObject(sql, Long.class);
	}
}
