package com.softisland.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.softisland.dao.DaoSupport;
import com.softisland.util.PageData;



@Service("rollTagService")
public class RollTagService {
	private Logger logger = Logger.getLogger(RollTagService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void work(){
		try {
			logger.info("roll_begin");
			PageData pd = new PageData();
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			String today=f.format(c.getTime()); //当天
			c.add(Calendar.DAY_OF_MONTH, -7);
			String lasttoday=f.format(c.getTime()); //获取前7天
			String day_anlyze_start = lasttoday + " 00:00:00.000";
			String day_anlyze_stop = today + " 00:00:00.000";
			pd.put("day_anlyze_start", day_anlyze_start);
			pd.put("day_anlyze_stop", day_anlyze_stop);
			//查询前3名房间的价值之和
			List<PageData> valueList=(List<PageData>) dao.findForList("RollMapper.valueList", pd);
			if(valueList!=null&&valueList.size()>0){
				logger.info("get roll room="+valueList);
			//清除所有的土豪标签
			dao.update("RollMapper.updateValue", null);
			//新增土豪标签
			for(PageData data:valueList){
			//查询该steam_uid在权限列表是否存在
				List<PageData> grantlist=(List<PageData>) dao.findForList("RollMapper.getGrantBySteam", data);
				if(grantlist!=null&&grantlist.size()>0){
					dao.update("RollMapper.addValue", data);
				}
				else{
					dao.save("RollMapper.addgrant", data);
				}		
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
