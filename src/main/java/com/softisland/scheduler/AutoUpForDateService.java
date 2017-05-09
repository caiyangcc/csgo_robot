package com.softisland.scheduler;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;

import com.softisland.util.PageData;



@Service("autoUpForDateService")
public class AutoUpForDateService {
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
			"yyyyMMdd");
	private Logger logger = Logger.getLogger(AutoUpForDateService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void work()  {
	PageData pd=new PageData();
	try {
		
		// 获取当前定时任务的时间
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		String currentTime=f1.format(c1.getTime());
		pd.put("currentTime", currentTime);
		//查询可以执行定时任务的商品
		List<PageData> list=(List<PageData>) dao.findForList("OneBuyObjectMapper.getDateAutoList", pd);
		if(list!=null&&list.size()>0){
			for(PageData p:list){			
				Timestamp up_time=(Timestamp) p.get("up_time");
				if(up_time!=null){
					String newup_time = f1.format(up_time);
					boolean code=AutoUpForDateService.compareDate(currentTime, newup_time);
					if(code){
						pd.put("up_time", currentTime);	
					}
					else{
						pd.put("up_time", up_time);	
					}
					
				}
				else{
					pd.put("up_time", currentTime);	
				}
				int treasure_product_id=(int) p.get("treasure_product_id");
				//自动上架
				pd.put("treasure_product_id", treasure_product_id);
				pd.put("status", 1);//1 
				pd.put("issue", AutoUpForDateService.getDays());//期号
				pd.put("date_updated", new Timestamp(System.currentTimeMillis()));	
				dao.update("OneBuyObjectMapper.updateDateAuto", pd);
				//修改时间自动上架表
				pd.put("is_up", 1);
				dao.update("OneBuyObjectMapper.updateDateAutoForm", pd);
				
			}
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error(e.toString());
	}
	}
	
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}
	
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getDays() {
		return sdfDays.format(new Date());
	}
}