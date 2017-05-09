package com.softisland.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.util.PageData;



@Service("returnVoucherService")
public class ReturnVoucherService {
	private Logger logger = Logger.getLogger(ReturnVoucherService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
		public void work(){ 
    	try{
    		PageData pd=new PageData();
    		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Calendar c = Calendar.getInstance();
    		String current=f.format(c.getTime()); 
    		c.add(Calendar.MINUTE, -60);
    		String lastMIn=f.format(c.getTime());
    		pd.put("current", current);
    		pd.put("lastMIn", lastMIn);
    	//查询提现失败但优惠券使用成功的数量
    	List<PageData> dataList=(List<PageData>) dao.findForList("RobotMapper.getFailWithDrawCash", pd);
    	if(dataList!=null&&dataList.size()>0){
    	//退回优惠券
    	dao.batchUpdate("RobotMapper.returnVoucher", dataList);
    	}
    	}catch(Exception e){
    		logger.error(e.toString());
    	}
    	
    	
    	
    }
	
}