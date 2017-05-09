package com.softisland.dao;

import java.util.List;
import java.util.Map;

public interface ScheduleDao {
	
	public List<Map<String,Object>> getAllUser() throws Exception;
	
	
		//查询所有的批号	
		public List<Map<String,Object>> getAllBatch() throws Exception;
		//查询该批次号是否有在售商品
		public List<Map<String,Object>> getOnSalePro(String batchCode)throws Exception;
		
		//查询该批次是否有下架的商品
		
		public List<Map<String,Object>> getUpPro(String batchCode)throws Exception;
		
		//自动上架
		public int autoUp(Map<String,Object> map) throws Exception;
		
		//改变批次状态
		
		public int updateBatch(int id) throws Exception;
		
		//删除批次
		public int deleteBatch(String batch_code) throws Exception;
		
		//查询所有dota2产品
		public List<Map<String,Object>> getDota2Data() throws Exception;
		
		//自动更新价格
		public int[] updatePrice(List<Map<String, Object>> list)throws Exception;
}
