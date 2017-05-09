package com.softisland.scheduler;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.util.PageData;



@Service("updateRobotAmountService")
public class updateRobotAmountService {
	private Logger logger = Logger.getLogger(updateRobotAmountService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void work()  {
	try {
		//查询所有的机器人
		List<PageData> list=(List<PageData>) dao.findForList("AccountMapper.getRobot", null);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				PageData p1=list.get(i);
		//查询该机器人的数量
		PageData p=(PageData) dao.findForObject("AccountMapper.getRobotAmount", p1);
		p.put("bots_steam_uid", p1.get("steam_uid"));
		//更新库存数量
		dao.update("AccountMapper.updateAmount", p);
			}
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error(e.toString());
	}
	}
	
}