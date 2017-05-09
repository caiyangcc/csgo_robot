package com.softisland.scheduler;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.util.PageData;



@Service("cleanLockService")
public class CleanLockService {
	private Logger logger = Logger.getLogger(CleanLockService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public synchronized void work()  {
		 		
	          
	 //查询上锁的的商品
	PageData pd=new PageData();
	try {
		List<PageData> lockProcuctList=(List<PageData>) dao.findForList("ProductMapper.lockProductList", pd);
	    if(lockProcuctList!=null&&lockProcuctList.size()>0){
	    	for(int i=0;i<lockProcuctList.size();i++){
	    		PageData p=lockProcuctList.get(i);
	    		int lock_time=(int) p.get("lock_time");
	    		int tradeId=(int) p.get("ptid");
	    		int id=(int) p.get("id");
	    		long intervaltime=(long)p.get("intervaltime");
	    		if(intervaltime>=lock_time){
	    			pd.put("oldstatus", 18);
	    			pd.put("status", 1);
		    		pd.put("tradeId", tradeId);
		    		pd.put("id", id);
		    		pd.put("lock_status", 0);
		    		pd.put("lock_last_updated", new Timestamp(System.currentTimeMillis()));
		    		pd.put("createTime", new Timestamp(System.currentTimeMillis()));
	    			dao.update("ProductMapper.cleanLockProduct", pd);
	    			dao.update("ProductMapper.updateproductstatus", pd);
	    		}
	    	}
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error(e.toString());
	}
	}
	
}