package com.softisland.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softisland.dao.ScheduleDao;
@Repository
public class ScheduleDaoImpl implements ScheduleDao{
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Override
	public List<Map<String, Object>> getAllUser() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list=jdbcTemplate.queryForList("select * from a ");
		return list;
	}
	@Override
	public List<Map<String, Object>> getAllBatch() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> batchList=jdbcTemplate.queryForList("select DISTINCT batch_code from treasure_autoup");
		return batchList;
	}
	@Override
	public List<Map<String, Object>> getOnSalePro(String batchCode) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> onSaleList=jdbcTemplate.queryForList("select p.id from treasure_autoup a,treasure_product p where a.product_id=p.id and a.batch_code=? and p.status=1",batchCode);	
		return onSaleList;
	}
	@Override
	public List<Map<String, Object>> getUpPro(String batchCode) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> upList=jdbcTemplate.queryForList("select p.id from treasure_autoup a,treasure_product p where a.product_id=p.id and a.batch_code=? and p.status=2",batchCode);	

		return upList;
	}
	@Override
	public int autoUp(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="update treasure_product set status =1,issue=?,putaway_date=?,maturity_date=?,date_updated=? where id=? and status=2";
		int code=jdbcTemplate.update(sql, map.get("issue"),map.get("putaway_date"),map.get("maturity_date"),map.get("date_updated"),map.get("id"));
		return code;
	}
	@Override
	public int updateBatch(int id) throws Exception {
		// TODO Auto-generated method stub
		String sql="update treasure_autoup set is_up=1,update_time=NOW() where product_id=?";
		int code=jdbcTemplate.update(sql,id);
		return code;
	}
	@Override
	public int deleteBatch(String batch_code) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from treasure_autoup where batch_code=?";
		int code=jdbcTemplate.update(sql,batch_code);
		return code;
	}
	@Override
	public List<Map<String, Object>> getDota2Data() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer bu=new StringBuffer();
		bu.append(" select min(t.unit_price) as price ,count(t.id) as count,p.id");
		bu.append(" from product_trade t left join product p on t.product_id=p.id");
		bu.append(" where t.status=2 and t.app_id=570 group by p.id");
		bu.append(" union");
		bu.append(" select 0 as price,0 as count,p.id from"); 
		bu.append(" product p where p.id not in");
		bu.append(" (select p.id from product_trade t left join product p on t.product_id=p.id where t.status=2 and t.app_id=570 group by p.id)");
		bu.append(" and p.app_id=570");
		return jdbcTemplate.queryForList(bu.toString());
	}
	@Override
	public int[] updatePrice(List<Map<String, Object>> list) throws Exception {
		// TODO Auto-generated method stub
        String sql="update product  set min_price=?,sale_count=? where id = ?";
        	int[] code=jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter(){
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return list.size();
				}
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
				  ps.setBigDecimal(1,(BigDecimal) list.get(i).get("price"));
				  ps.setLong(2, (long) list.get(i).get("count"));
				  ps.setInt(3, (int) list.get(i).get("id"));
				}
        		
        	});
			return code;
        
        
	}

}
