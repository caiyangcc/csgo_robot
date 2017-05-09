package com.softisland.scheduler;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.dao.DaoSupport2;
import com.softisland.entity.OneProductTypeEnum;
import com.softisland.util.PageData;


@Service("reportJobService")
public class ReportJobService {
	private Logger logger = Logger.getLogger(ReportJobService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao2;
	@Value("${numOfDay}") 
    private int numOfDay;
	 @Autowired  
	 RedisTemplate<String,String> redisTemplate;
	 
	/**
	 * 
	 * 报表定时分析任务
	 * 
	 * @exception @since 1.0.0
	 */
	public synchronized void work()  {
			long start = System.currentTimeMillis();
			long tmp3 = System.currentTimeMillis();
		for (int i = 1; i <= numOfDay; i++) {
				long end = 3600 * 1000 * 24;
				Date anylizeDate = new Date(start - end);
				start = start - end;
				PageData pd = new PageData();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = sdf.format(anylizeDate);
				long tmp1 = System.currentTimeMillis();
				logger.info("开始分析2层"+dateString+"的数据报表。开始时间为："+tmp1);
				pd.put("data_anliyze_time1", dateString);
					try {
						insertReportFormOne1(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						insertReportFormOne2(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						insertReportFormTwo(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						insertReportFormThree(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						insertReportFormFour(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						insertReportFormSix(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	try {
						insertReportFormSixteen(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	try {
						insertReportFormBoxSteamUid(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	try {
						insertReportFormBoxName(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						insertReportFormTwentyFour(pd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				long tmp2 = System.currentTimeMillis();
				logger.info("分析"+dateString+"的数据完成。总共耗时为："+(tmp2-tmp1)+"毫秒。");
				try {
					 Thread.sleep(5000);
				} catch (Exception e) {
					logger.error(e.toString());
				}
	           
			}
			start = System.currentTimeMillis();
			for (int i = 1; i <= numOfDay; i++) {
				long end = 3600 * 1000 * 24;
				Date anylizeDate = new Date(start - end);
				start = start - end;
				PageData pd = new PageData();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = sdf.format(anylizeDate);
				long tmp1 = System.currentTimeMillis();
				logger.info("开始分析3层"+dateString+"的数据报表。开始时间为："+tmp1);
				pd.put("data_anliyze_time1", dateString);
                try {
					insertReportFrom4All(pd);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					logger.info("分析3层"+dateString+"报表完成.........................................");
				}
				long tmp2 = System.currentTimeMillis();
				logger.info("分析3层"+dateString+"的数据完成。总共耗时为："+(tmp2-tmp1)+"毫秒。");
				try {
					 Thread.sleep(5000);
				} catch (Exception e) {
					logger.error(e.toString());
				}
	           
			}
			
			//统计卖家统计报表（统计前一天）
			try {
				insertReportFormSeven();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//买家统计报表（统计前一天的）
			try {
				insertReportFormEight();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long tmp2 = System.currentTimeMillis();
			logger.info("分析"+numOfDay+"天的数据完成。总共耗时为："+(tmp2-tmp3)+"毫秒。");
	}

	public void insertReportFormOne1(PageData pd) throws Exception {

		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormOne1", pd);
		List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormOne1", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormOne1", list);
		}
	}

	public void insertReportFormOne2(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		pd.put("dateString", dateString);
		dao.delete("RobotMapper.deleteReportFormOne2", pd);
		List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormOne2", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormOne2", list);
		}
	}

	
	public void insertReportFormTwo(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = null;
		String day_anlyze_stop = null;
		for (int i = 0; i < 24; i++) {
			day_anlyze_start = dateString + " " + i + ":00:00.000";
			day_anlyze_stop = dateString + " " + i + ":59:59.999";
			// 执行存储过程
			pd.put("day_anlyze_start", day_anlyze_start);
			pd.put("day_anlyze_stop", day_anlyze_stop);
			dao.delete("RobotMapper.deleteReportFormTwo", pd);
			List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormTwo", pd);
			if(list!=null&&list.size()>0){
				dao.save("RobotMapper.insertReportFormTwo", list);
			}
		}
	}
	

	public void insertReportFormThree(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormThree", pd);
		List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormThree", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormThree", list);
		}
	}
	
	public void insertReportFormFour(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormFour", pd);
		List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormFour", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormFour", list);
		}
	}

	public void insertReportFormFive(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormFive", pd);
		List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormFive", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormFive", list);
		}
	}

	public void insertReportFormSix(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormSix", pd);
		List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormSix", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormSix", list);
		}
	}

	public void insertReportFormSeven() throws Exception {
		PageData pd = new PageData();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		String dateString=f.format(c.getTime()); 
		logger.error("--开始分析"+dateString+"号的卖家统计报表");
		String day_anlyze_start = "2016-01-25"+ " 00:00:00.000";
		String day_anlyze_start1 = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		pd.put("day_anlyze_start1", day_anlyze_start1);
		pd.put("dateString", dateString);
		int offset=0;
		int limit=20000;
		pd.put("offset", offset);
		pd.put("limit", limit);
		
		List<PageData> list=this.insertReportFormSevenData(pd);
		//查询卖家总数，存入缓存
		int count=(int) dao.findForObject("RobotMapper.getALLSellerReportCount", null);
		String newcountOut=String.valueOf(count);
		redisTemplate.opsForValue().set("sellerreportformseven:seller_count", newcountOut);	
		
	}

	
	public List<PageData> insertReportFormSevenData(PageData pd) throws Exception{
		int offset=(int) pd.get("offset");
		int limit=(int) pd.get("limit");
		List<PageData> list = (List<PageData>) dao2.findForList("RobotMapper.queryReportFormSeven", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormSeven", list);		
		}
		while(list!=null&&list.size()==limit){
			offset=offset+list.size();
			pd.put("offset", offset);
			pd.put("limit", limit);
		list=this.insertReportFormSevenData(pd);
		}
		return list; 
	}
	
	public void insertReportFormEight() throws Exception {
		PageData pd = new PageData();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		String dateString=f.format(c.getTime());
		
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(cal.getTime()) + " 00:00:00.000";
		String first = df.format(cal.getTime()) + " 00:00:00.000";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		pd.put("week_start", preMonday);
		pd.put("month_start", first);
		dao.delete("RobotMapper.deleteReportFormEight", pd);
		int offset=0;
		int limit=20000;
		pd.put("offset", offset);
		pd.put("limit", limit);
		List<PageData> list=this.insertReportFormEightData(pd);
		//List<PageData> list = (List<PageData>) dao2.findForList("RobotMapper.queryReportFormEight", pd);
		/*if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormEight", list);
		}*/
	}
	
	
	public List<PageData> insertReportFormEightData(PageData pd) throws Exception{
		int offset=(int) pd.get("offset");
		int limit=(int) pd.get("limit");
		List<PageData> list = (List<PageData>) dao2.findForList("RobotMapper.queryReportFormEight", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormEight", list);
		}
		while(list!=null&&list.size()==limit){
			offset=offset+list.size();
			pd.put("offset", offset);
			pd.put("limit", limit);
		list=this.insertReportFormEightData(pd);
		}
		return list; 
	}
	
	
	public void insertReportFrom4All(PageData pd) throws Exception{
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = null;
		String day_anlyze_stop = null;
		String day_anlyze_start1 = null;
		String eleven=null;
		day_anlyze_start = "2016-01-25 00:00:00.000";
		day_anlyze_start1=dateString +" "+"00:00:00.000";
			day_anlyze_stop =dateString +" "+"23:59:59.999";
			// 执行存储过程
			pd.put("day_anlyze_start", day_anlyze_start);
			pd.put("day_anlyze_start1", day_anlyze_start1);
			pd.put("day_anlyze_stop", day_anlyze_stop);
			dao.delete("RobotMapper.deleteReportForm4all", pd);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportForm4all", pd);
			List<PageData> categoryList=(List<PageData>) dao.findForList("RobotMapper.findCategory",pd);
		for(int k=1;k<=4;k=k+3){
			for(PageData page:categoryList){
				String title =(String) page.get("title");
				String typeName=(String) page.get("name");
				int categoryid= (int) page.get("categoryid");
				int typeid= (int) page.get("typeid");
				int gameType=(int) page.get("gameType");
				String gameName=(String) page.get("gameName");
				boolean judge=false;
				if(list==null||list.size()<=0){
					eleven=sdf.format(new Timestamp(System.currentTimeMillis()));
				}
			for(PageData pagedate:list){
				String category=(String) pagedate.get("typeName");
				String userType=(String) pagedate.get("userType");
				String daytime =pagedate.get("time")+"";
				if(eleven==null&&list!=null){
				eleven=pagedate.get("eleven")+"";
				}
				if(!dateString.equals(daytime.substring(0,10))){
					pagedate.put("time",dateString);
					pagedate.put("upOnTotalNum","0");
					pagedate.put("saleTotalNum","0");
					pagedate.put("status","0");
				}
				if(category.equals(title)&&userType.equals(String.valueOf(k))){
					judge=true;
					pagedate.put("time", day_anlyze_start1);
					break;
					}
				}
				if(!judge){
					PageData newpage=new PageData();
					newpage.put("userType",String.valueOf(k));
					newpage.put("gameType",gameType);
					newpage.put("gameName",gameName);
					newpage.put("categoryName",typeName);
					newpage.put("eleven",eleven);
					newpage.put("categoryid",categoryid);
					newpage.put("typeid",typeid);
					newpage.put("typeName",title);
					newpage.put("time",day_anlyze_start1);
					newpage.put("upInTotalNum","0");
					newpage.put("saleTotalNum","0");
					newpage.put("upOnTotalNum","0");
					newpage.put("status","0");
					list.add(newpage);
					
				}
				
			}
		}
	if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportForm4", list);
			}
	}

	public void insertReportFormNineTen() throws Exception {
		/*String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start ="2016-03-01" + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		String day_anlyze_start1 = null;
		day_anlyze_start1=dateString +" "+"00:00:00.000";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		pd.put("day_anlyze_start1", day_anlyze_start1);
		dao.delete("RobotMapper.deleteReportFormNineTen", pd);
		PageData list=(PageData) dao.findForObject("RobotMapper.selectReportFormNineTen", pd);
		if(list!=null&&list.size()>0){
			if(list.get("time")==null||!dateString.equals((String) list.get("time"))){
				list.put("incom","0");
				list.put("success","0");
				list.put("rate","0");
			}
			DecimalFormat    df   = new DecimalFormat("######0.00");  
			BigDecimal success=(BigDecimal) list.get("success");
			BigDecimal free=((BigDecimal) list.get("free")).add(new BigDecimal(18091.61));
			if(success.compareTo(BigDecimal.ZERO)==0){
				list.put("rate", df.format(BigDecimal.ZERO));
			}else{
				
				BigDecimal rate=(success.divide((free.add(success)),4)).multiply(new BigDecimal(100));
				list.put("rate", df.format(rate));
			}
			list.put("free",df.format(free));
			list.put("time",day_anlyze_start1);
			dao.save("RobotMapper.insertReportFormNineTen", list);
			}*/
		PageData pd = new PageData();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		String dateString=f.format(c.getTime()); 
		String day_anlyze_start =dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		c.add(Calendar.DAY_OF_MONTH, -1);
		String dateString1=f.format(c.getTime());  
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		pd.put("day_anlyze_start1", dateString1);
		//dao.delete("RobotMapper.deleteReportFormNineTen", pd);
		PageData list=(PageData) dao.findForObject("RobotMapper.selectReportFormNineTen", pd);
		
		PageData page=(PageData) dao.findForObject("RobotMapper.selectReportFormNineTenLast", pd);
		
		if(list!=null&&list.size()>0){
			if(list.get("time")==null||!dateString.equals((String) list.get("time"))){
				list.put("incom",new BigDecimal(0));
				list.put("success",new BigDecimal(0));
				list.put("rate",new BigDecimal(0));
			}
			
			DecimalFormat    df   = new DecimalFormat("######0.00");  
			BigDecimal success=(BigDecimal) list.get("success");
			BigDecimal income=(BigDecimal) list.get("incom");
			BigDecimal noWithdrawCash;
			if(page!=null){
				 noWithdrawCash=(BigDecimal) page.get("not_withdraw_amount");
			}
			else{
				 noWithdrawCash=new BigDecimal(0);
			}
		
			
			BigDecimal free=noWithdrawCash.add(income).subtract(success);
			
			if(success.compareTo(BigDecimal.ZERO)==0){
				list.put("rate", df.format(BigDecimal.ZERO));
			}else{
				BigDecimal rate=(success.divide((free.add(success)),4)).multiply(new BigDecimal(100));
				list.put("rate", df.format(rate));
			}
			list.put("free",df.format(free));
			list.put("time",day_anlyze_start);
			dao.save("RobotMapper.insertReportFormNineTen", list);
			}
	}

	
	
	/**
	 * 渠道对账报表
	 * 
	 * @return
	 */
	public void insertReportFormSixteen(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormSixteen", pd);
		List<PageData> list = (List<PageData>) dao2.findForList("RobotMapper.queryReportForChannel", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormSixteen", list);
		}
	}
	
	/**
	 * 开箱用户统计
	 * 
	 * @return
	 */
	public void insertReportFormBoxSteamUid(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormBoxSteamUid", pd);
		List<PageData> list = (List<PageData>) dao2.findForList("RobotMapper.queryReportForBoxSteamUid", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormBoxSteamUid", list);
		}
	}
	
	/**
	 * 开箱名称统计
	 * 
	 * @return
	 */
	public void insertReportFormBoxName(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormBoxName", pd);
		List<PageData> list = (List<PageData>) dao2.findForList("RobotMapper.queryReportForBoxName", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormBoxName", list);
		}
	}
/*	public void insertReportFormSeven() throws Exception {
		PageData pd = new PageData();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		String dateString=f.format(c.getTime()); 
		String day_anlyze_start = "2016-01-25"+ " 00:00:00.000";
		String day_anlyze_start1 = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		c.add(Calendar.DAY_OF_MONTH, -1);
		String dateString2=f.format(c.getTime());
		String day_anlyze_start2 = dateString2 + " 00:00:00.000";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		pd.put("day_anlyze_start1", day_anlyze_start1);
		pd.put("day_anlyze_start2", day_anlyze_start2);
		//dao.delete("RobotMapper.deleteReportFormSeven", pd);
		List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormSeven", pd);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				PageData p=list.get(i);
				long currentSale=((BigDecimal)p.get("nine")).longValue();
				double currentSaleMoney=((BigDecimal)p.get("ten")).doubleValue();
				double currentWithdraw=((BigDecimal)p.get("fourteen")).doubleValue();
				p.put("nine",currentSale+(long)p.get("salenum"));
				p.put("ten",currentSaleMoney+(double)p.get("saleamount"));
				p.put("eleven",(double)p.get("withdraw_cash_total")+currentSaleMoney-currentWithdraw);
			}	
		}
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormSeven", list);
		}
	}*/
	
	
	/*
	 * new_report我的begin
	 */
	//自营进销存报表
	public void insertReportFormSeventeen(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormSelfInvoicing", pd);
		//查询当天入库数量、金额
		List<PageData> listEight = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingEight", pd);
		//查询当天出库数量、金额
		List<PageData> listNine = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingNine", pd);
		//查询当天销售入库数量、金额
		List<PageData> listOne = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingOne", pd);
		//查询当天物料入库数量、金额
		List<PageData> listTwo = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingTwo", pd);
		//查询当天批发出库数量、金额
		List<PageData> listThree = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingThree", pd);
		//查询当天零售出库数量、金额
		List<PageData> listFour = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingFour", pd);
		//查询当天物料出库数量、金额
		List<PageData> listFive = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingFive", pd);
		//查询当天营销出库数量、金额
		List<PageData> listSix = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingSix", pd);
		//查询当天剩余库存
		List<PageData> listSeven = (List<PageData>) dao.findForList("RobotMapper.querySelfInvoicingSeven", pd);
		
		List<PageData> newList = new ArrayList<PageData>();
		Date date = new Date();
		for(int i=-1;i<11;i++){
			PageData pa = new PageData();
			if(listEight!=null&&listEight.size()>0){
				for(PageData p8:listEight){
					int categoryId = Integer.parseInt(String.valueOf(p8.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p8.get("name"));
						pa.put("storage_quantity", p8.get("num"));
						pa.put("storage_amount", p8.get("totalMoney"));
					}
				}
			}
			if(listNine!=null&&listNine.size()>0){
				for(PageData p9:listNine){
					int categoryId = Integer.parseInt(String.valueOf(p9.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p9.get("name"));
						pa.put("outgoing_quantity", p9.get("num"));
						pa.put("outgoing_amount", p9.get("totalMoney"));
					}
				}
			}
			if(listOne!=null&&listOne.size()>0){
				for(PageData p1:listOne){
					int categoryId = Integer.parseInt(String.valueOf(p1.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p1.get("name"));
						pa.put("sales_storage_quantity", p1.get("num"));
						pa.put("sales_storage_amount", p1.get("totalMoney"));
					}
				}
			}
			if(listTwo!=null&&listTwo.size()>0){
				for(PageData p2:listTwo){
					int categoryId = Integer.parseInt(String.valueOf(p2.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p2.get("name"));
						pa.put("material_storage_quantity", p2.get("num"));
						pa.put("material_storage_amount", p2.get("totalMoney"));
					}
				}
			}
			if(listThree!=null&&listThree.size()>0){
				for(PageData p3:listThree){
					int categoryId = Integer.parseInt(String.valueOf(p3.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p3.get("name"));
						pa.put("wholesale_outgoing_quantity", p3.get("num"));
						pa.put("wholesale_outgoing_amount", p3.get("totalMoney"));
					}
				}
			}
			if(listFour!=null&&listFour.size()>0){
				for(PageData p4:listFour){
					int categoryId = Integer.parseInt(String.valueOf(p4.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p4.get("name"));
						pa.put("retail_outgoing_quantity", p4.get("num"));
						pa.put("retail_outgoing_amount", p4.get("totalMoney"));
					}
				}
			}
			if(listFive!=null&&listFive.size()>0){
				for(PageData p5:listFive){
					int categoryId = Integer.parseInt(String.valueOf(p5.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p5.get("name"));
						pa.put("material_outgoing_quantity", p5.get("num"));
						pa.put("material_outgoing_amount", p5.get("totalMoney"));
					}
				}
			}
			if(listSix!=null&&listSix.size()>0){
				for(PageData p6:listSix){
					int categoryId = Integer.parseInt(String.valueOf(p6.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p6.get("name"));
						pa.put("marketing_outgoing_quantity", p6.get("num"));
						pa.put("marketing_outgoing_amount", p6.get("totalMoney"));
					}
				}
			}
			if(listSeven!=null&&listSeven.size()>0){
				for(PageData p7:listSeven){
					int categoryId = Integer.parseInt(String.valueOf(p7.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p7.get("name"));
						pa.put("stock_quantity", p7.get("num"));
						pa.put("stock_amount", p7.get("totalMoney"));
					}
				}
			}
			if(pa!=null&&pa.size()>0){
				newList.add(pa);
			}
		}
		if(newList!=null&&newList.size()>0){
			//查询出来的数据组装成新的集合插入report17_selfinvoicing_form表
			dao.batchSave("RobotMapper.saveSelfInvoicingDatas", newList);
		}

	}
	
	//自营营销报表
	public void insertReportFormEighteen(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		
		//根据日期删除report18_marketing_form中数据
		dao.delete("RobotMapper.deleteReportFormSelfMarketing", pd);
		//查询当天营销入库数量、金额
		List<PageData> listOne = (List<PageData>) dao.findForList("RobotMapper.querySelfMarketingOne", pd);
		//查询当天营销出库数量、金额
		List<PageData> listTwo = (List<PageData>) dao.findForList("RobotMapper.querySelfMarketingTwo", pd);
		//查询当天剩余库存
		List<PageData> listThree = (List<PageData>) dao.findForList("RobotMapper.querySelfMarketingThree", pd);
		
		List<PageData> newList = new ArrayList<PageData>();
		Date date = new Date();
		for(int i=-1;i<11;i++){
			PageData pa = new PageData();
			if(listOne!=null&&listOne.size()>0){
				for(PageData p1:listOne){
					int categoryId = Integer.parseInt(String.valueOf(p1.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p1.get("name"));
						pa.put("storage_quantity", p1.get("num"));
						pa.put("storage_amount", p1.get("totalMoney"));
					}
				}
			}
			if(listTwo!=null&&listTwo.size()>0){
				for(PageData p2:listTwo){
					int categoryId = Integer.parseInt(String.valueOf(p2.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p2.get("name"));
						pa.put("outgoing_quantity", p2.get("num"));
						pa.put("outgoing_amount", p2.get("totalMoney"));
					}
				}
			}
			if(listThree!=null&&listThree.size()>0){
				for(PageData p3:listThree){
					int categoryId = Integer.parseInt(String.valueOf(p3.get("categoryId")));
					if(categoryId==i){
						pa.put("data_anliyze_time", dateString);
						pa.put("create_time", date);
						pa.put("product_category_type", i);
						pa.put("product_category_name", p3.get("name"));
						pa.put("stock_quantity", p3.get("num"));
						pa.put("stock_amount", p3.get("totalMoney"));
					}
				}
			}
			if(pa!=null&&pa.size()>0){
				newList.add(pa);
			}
		}
		if(newList!=null&&newList.size()>0){
			//查询出来的数据组装成新的集合插入report17_selfinvoicing_form表
			dao.batchSave("RobotMapper.saveSelfMarketingDatas", newList);
		}
	}
	/*
	 * end
	 */

	/**
	 * 夺宝收入报表
	 * @param pd
	 * @throws Exception
	 */
	public void insertReportFormTwenty(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormTwenty", pd);
		List<PageData> list = (List<PageData>) dao.findForList("RobotMapper.queryReportFormTwenty", pd);
		if(list!=null&&list.size()>0){
			dao.save("RobotMapper.insertReportFormTwenty", list);
		}
	}
	
	
	/**
	 * 一元夺宝进销存报表
	 * @param pd
	 * @throws Exception
	 */
	public void insertReportFormTwentyOne(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormTwentyOne", pd);
		//查询当天夺宝商品总数，夺宝商品成本
		List<PageData> totalList = (List<PageData>) dao.findForList("RobotMapper.totalList", pd);
		
		//查询上架的商品总数商品成本
		List<PageData> upList = (List<PageData>) dao.findForList("RobotMapper.upProductList", pd);
		
		//查询已售出的商品总数商品成本
		List<PageData> saleList = (List<PageData>) dao.findForList("RobotMapper.saleProductList", pd);
		
		//查询已揭晓的商品总数商品成本
		List<PageData> openList = (List<PageData>) dao.findForList("RobotMapper.openProductList", pd);
		
		//查询已兑换的商品总数商品成本
		List<PageData> exchangeList = (List<PageData>) dao.findForList("RobotMapper.exchangeProductList", pd);
		
		//查询废弃的商品总数与商品成本
		List<PageData> cancleList = (List<PageData>) dao.findForList("RobotMapper.cancleProductList", pd);
		
		List<OneProductTypeEnum> oneTypeEnums = OneProductTypeEnum.getOneProductTypeList();
		
		List<PageData> list=new ArrayList();
		
		
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		String createTime=f1.format(c1.getTime());
		for(int i=0;i<oneTypeEnums.size();i++){
			String code=String.valueOf(oneTypeEnums.get(i).getCode());
			String name=oneTypeEnums.get(i).getName();
			long totalCount=0; //商品总数
			BigDecimal totalPrice=new BigDecimal("0");//商品总成本
			long upCount=0;//上架商品总数
			BigDecimal upPrice=new BigDecimal("0");//上架商品总成本
			long saleCount=0;//已售商品总数
			BigDecimal salePrice=new BigDecimal("0");//已售商品总成本
			long openCount=0;//已揭晓商品总数
			BigDecimal openPrice=new BigDecimal("0");//已揭晓商品总成本
			long exchangeCount=0;//已兑换商品总数
			BigDecimal exchangePrice=new BigDecimal("0");//已兑换商品总成本
			long cancleCount=0;//已兑换商品总数
			BigDecimal canclePrice=new BigDecimal("0");//已兑换商品总成本
			PageData newpd=new PageData();
			for(int j=0;j<totalList.size();j++){
				PageData p=totalList.get(j);
				if(p.get("treasure_type").equals(code)){
					totalCount= (long) p.get("totalCount");
					totalPrice= totalPrice.add((BigDecimal)p.get("totalPrice"));
				}
			}
			
			
			for(int j=0;j<upList.size();j++){
				PageData p=upList.get(j);
				if(p.get("treasure_type").equals(code)){
					upCount=(long) p.get("upCount");
					upPrice= upPrice.add((BigDecimal)p.get("upPrice"));
				}
			}
			

			for(int j=0;j<saleList.size();j++){
				PageData p=saleList.get(j);
				if(p.get("treasure_type").equals(code)){
					saleCount=(long) p.get("saleCount");
					salePrice= salePrice.add((BigDecimal)p.get("salePrice"));
				}
			}
			
			for(int j=0;j<openList.size();j++){
				PageData p=openList.get(j);
				if(p.get("treasure_type").equals(code)){
					openCount=(long) p.get("openCount");
					openPrice= openPrice.add((BigDecimal)p.get("openPrice"));
				}
			}
			
			for(int j=0;j<exchangeList.size();j++){
				PageData p=exchangeList.get(j);
				if(p.get("treasure_type").equals(code)){
					exchangeCount=(long) p.get("exchangeCount");
					exchangePrice= exchangePrice.add((BigDecimal)p.get("exchangePrice"));
				}
			}
			
			for(int j=0;j<cancleList.size();j++){
				PageData p=cancleList.get(j);
				if(p.get("treasure_type").equals(code)){
					cancleCount=(long) p.get("cancleCount");
					canclePrice= canclePrice.add((BigDecimal)p.get("canclePrice"));
				}
			}
			totalCount=totalCount-cancleCount;
			totalPrice=totalPrice.subtract(canclePrice);
			
			newpd.put("anlyze_data", dateString);
			newpd.put("code", code);
			newpd.put("name", name);
			newpd.put("total_count", totalCount);
			newpd.put("total_price", totalPrice);
			newpd.put("up_count", upCount);
			newpd.put("up_price", upPrice);
			newpd.put("sale_count", saleCount);
			newpd.put("sale_price", salePrice);
			newpd.put("open_count", openCount);
			newpd.put("open_price", openPrice);
			newpd.put("exchange_count", exchangeCount);
			newpd.put("exchange_price", exchangePrice);
			newpd.put("create_time",createTime);
			list.add(newpd);
		}
		
		
		if(list!=null&&list.size()>0){
		dao.save("RobotMapper.insertReportFormTwentyOne", list);
		}
	}
	
	
	/**
	 * 一元夺宝c2c报表
	 * @param pd
	 * @throws Exception
	 */
	public void insertReportFormTwentyTwo(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormTwentyTwo", pd);
		//查询c2c上架的商品总数与金额
		List<PageData> newupList = (List<PageData>) dao.findForList("RobotMapper.newUpProductc2cList", pd);
		
		//查询c2c用户下注产品数
		List<PageData> betList = (List<PageData>) dao.findForList("RobotMapper.betProductc2cList", pd);
		
		//查询c2c夺宝终止产品数
		List<PageData> cancleList = (List<PageData>) dao.findForList("RobotMapper.cancleProductc2cList", pd);
		
		//查询查c2c开奖产品数
		List<PageData> openList = (List<PageData>) dao.findForList("RobotMapper.openProductc2cList", pd);
		
		//查询查c2c在架产品数 
		List<PageData> upList = (List<PageData>) dao.findForList("RobotMapper.upProductc2cList", pd);
		
		//查询c2c下注产品用户数
		List<PageData> betUserList = (List<PageData>) dao.findForList("RobotMapper.betUserProductc2cList", pd);
		
		//查询查c2c订单总数与金额
		List<PageData> orderList = (List<PageData>) dao.findForList("RobotMapper.orderProductc2cList", pd);
		
		//查询查c2c退款总数与金额
		List<PageData> returnOrderList = (List<PageData>) dao.findForList("RobotMapper.returnOrderProductc2cList", pd);
		
		//查询c2c管理费收入
		List<PageData> managerList = (List<PageData>) dao.findForList("RobotMapper.managerProductc2cList", pd);
		
		
		List<PageData> list=new ArrayList();
		
		
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		String createTime=f1.format(c1.getTime());
			long new_up_count=0; //新上架产品数
			long bet_count=0;//下注产品数
			long open_count=0;//开奖数
			long cancle_count=0;//中止数
			long up_count=0;//在架数
			long up_user_count=0;//上架用户数
			long bet_user_count=0;//下注用户数
			long order_count=0;//订单数
			BigDecimal order_price=new BigDecimal("0");//订单总额
			long return_order_count=0;//退款订单数
			BigDecimal return_order_price=new BigDecimal("0");//退款订单金额数
			BigDecimal up_total_price=new BigDecimal("0");//上架产品金额
			BigDecimal manager_price=new BigDecimal("0");//管理费
			PageData newpd=new PageData();
			for(int j=0;j<newupList.size();j++){
				PageData p=newupList.get(j);
					new_up_count= (long) p.get("newUpCount");
					up_total_price= up_total_price.add((BigDecimal)p.get("upPrice"));
					up_user_count=(long) p.get("upUserCount");		
			}
			
			
			for(int j=0;j<betList.size();j++){
				PageData p=betList.get(j);
				bet_count=(long) p.get("betCount");
			}
			

			for(int j=0;j<openList.size();j++){
				PageData p=openList.get(j);
				open_count=(long) p.get("openCount");
				
			}
			
			for(int j=0;j<cancleList.size();j++){
				PageData p=cancleList.get(j);
				cancle_count=(long) p.get("cancleCount");
				
			}
			
			for(int j=0;j<upList.size();j++){
				PageData p=upList.get(j);
				up_count=(long) p.get("upCount");
				
			}
			
			for(int j=0;j<betUserList.size();j++){
				PageData p=betUserList.get(j);
				bet_user_count=(long) p.get("betUserCount");
			}
			
			for(int j=0;j<orderList.size();j++){
				PageData p=orderList.get(j);			
				order_count=(long) p.get("orderCount");
				order_price= order_price.add((BigDecimal)p.get("orderPrice"));
				
			}
			
			for(int j=0;j<returnOrderList.size();j++){
				PageData p=returnOrderList.get(j);		
				return_order_count=(long) p.get("returnCount");
				return_order_price= return_order_price.add((BigDecimal)p.get("returnPrice"));
				
			}
			
			for(int j=0;j<managerList.size();j++){
				PageData p=managerList.get(j);
				manager_price= manager_price.add((BigDecimal)p.get("managerPrice"));
				
			}
			
			newpd.put("anlyze_data", dateString);
			newpd.put("code", 1);
			newpd.put("name", "虚物");
			newpd.put("new_up_count", new_up_count);
			newpd.put("bet_count", bet_count);
			newpd.put("open_count", open_count);
			newpd.put("cancle_count", cancle_count);
			newpd.put("up_count", up_count);
			newpd.put("up_user_count", up_user_count);
			newpd.put("bet_user_count", bet_user_count);
			newpd.put("order_count", order_count);
			newpd.put("order_price", order_price);
			newpd.put("return_order_count", return_order_count);
			newpd.put("return_order_price", return_order_price);
			newpd.put("up_total_price", up_total_price);
			newpd.put("manager_price", manager_price);
			newpd.put("create_time",createTime);
			list.add(newpd);
		
				
		if(list!=null&&list.size()>0){
		dao.save("RobotMapper.insertReportFormTwentyTwo", list);
		}
	}
	
	
	public void insertReportFormTwentyFour(PageData pd) throws Exception {
		String dateString = String.valueOf(pd.get("data_anliyze_time1"));
		String day_anlyze_start = dateString + " 00:00:00.000";
		String day_anlyze_stop = dateString + " 23:59:59.999";
		pd.put("day_anlyze_start", day_anlyze_start);
		pd.put("day_anlyze_stop", day_anlyze_stop);
		dao.delete("RobotMapper.deleteReportFormTwentyFour", pd);
		List<PageData> list = (List<PageData>) dao2.findForList("RobotMapper.queryReportFormTwentyFour", pd);
		if(list!=null&&list.size()>0){
			logger.error("get data--"+list.toString());
			dao.save("RobotMapper.insertReportFormTwentyFour", list);
		}
	}
	
	
	public int getNumOfDay() {
		return numOfDay;
	}

	public void setNumOfDay(int numOfDay) {
		this.numOfDay = numOfDay;
	}
	public void steamidsort(String dateString,String dateStart){
		dateStart=dateStart+" 00:00:00.000";
		dateString=dateString+" 23:59:59.999";
	}
	
}