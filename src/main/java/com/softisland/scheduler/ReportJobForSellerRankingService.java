package com.softisland.scheduler;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.util.PageData;


@Service("reportJobForSellerRankingService")
public class ReportJobForSellerRankingService {
	private Logger logger = Logger.getLogger(ReportJobForSellerRankingService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public synchronized void work()  {
		 		System.out.println("开始卖家排行榜报表");
				long start = System.currentTimeMillis();
		        try{
		        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				String today=f.format(c.getTime()); //当天
				c.add(Calendar.DAY_OF_MONTH, -7);
				String lasttoday=f.format(c.getTime()); //获取前七天
				PageData pd = new PageData();
				String day_anlyze_start = lasttoday+" 00:00:00.000";
				String day_anlyze_stop = today+" 00:00:00.000";
				pd.put("day_anlyze_start", day_anlyze_start);
				pd.put("day_anlyze_stop", day_anlyze_stop);
				//查询上一周的所有卖家用户
				List<PageData> sellerList = (List<PageData>) dao.findForList("RobotMapper.queryAllSeller", pd);	
				//查询所有的卖家上架数量
				List<PageData> putOnList = (List<PageData>) dao.findForList("RobotMapper.queryAllSellerOnCount", pd);
				//查询所有卖家的买家数量
				List<PageData> buyerList = (List<PageData>) dao.findForList("RobotMapper.queryAllSellerToBuyer", pd);
				//查询所有卖家的销售数量，销售金额
				List<PageData> salerList = (List<PageData>) dao.findForList("RobotMapper.queryAllSellerToSale", pd);		
				//查询上周的排行榜
				List<PageData> lastRankingList=(List<PageData>) dao.findForList("RobotMapper.queryAllSellerLastWeekRanking", pd);
				//查询统计报表里的所有卖家
				Integer count= (Integer) dao.findForObject("RobotMapper.queryAllSellerCountFromReport", pd);
				//查询所有的steam用户
				//Integer steamcount= (Integer) dao.findForObject("RobotMapper.queryAllSellerCountFromSteam", pd);
				List<PageData> list=new ArrayList();				
				SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar c1 = Calendar.getInstance();
				String createTime=f1.format(c1.getTime());
				for(int i=0;i<sellerList.size();i++){
					String steam_uid=(String) sellerList.get(i).get("seller_steam_uid");
					String name=(String) sellerList.get(i).get("personaname");
					int putOnNumber=0; //上架数量
					int buyerNumber=0;//买家数量
					int saleNumber=0;//销售数量
					double salePrice=0;//销售金额
					double sortNumber=0;//排序值
					int lastRanking=0;//上周排行榜
					PageData newpd=new PageData();
					
					for(int j=0;j<putOnList.size();j++){
						PageData p=putOnList.get(j);
						if(p.get("seller_steam_uid").equals(steam_uid)){
							putOnNumber=((Long) p.get("put_on_number")).intValue();
							putOnList.remove(j);
							break;
						}
					}
					
					for(int j=0;j<buyerList.size();j++){
						PageData p=buyerList.get(j);
						if(p.get("seller_steam_uid").equals(steam_uid)){
							buyerNumber=((Long) p.get("buyerCount")).intValue();
							buyerList.remove(j);
							break;
						}
					}
					
					for(int j=0;j<salerList.size();j++){
						PageData p=salerList.get(j);
						if(p.get("seller_steam_uid").equals(steam_uid)){
							saleNumber=((Long) p.get("sale_num")).intValue();
							salePrice=((BigDecimal)p.get("sale_price")).doubleValue();
							salerList.remove(j);
							break;			
						}
					}
						
				
					int m=0;
					for(int j=0;j<lastRankingList.size();j++){
						PageData p=lastRankingList.get(j);
						if(p.get("seller_steam_uid").equals(steam_uid)){
							lastRanking=((Long) p.get("num")).intValue();
							m=1;
							lastRankingList.remove(j);
							break;			
						}
					}
					//当统计表里上周没有用户时，上周排行直接设为统计报表里的用户总数，也就是最后一名
					/*if(m==0){
						if(lastRanking==0){
						lastRanking=-2;
						}
						else{
						lastRanking=count;
						}
					}*/
					
					if(m==0){
					lastRanking=0;
					}
					//排序值
				sortNumber=putOnNumber*0.3+buyerNumber*0.4+saleNumber*0.15+salePrice*0.15;
				
				if(putOnNumber!=0||buyerNumber!=0||saleNumber!=0||salePrice!=0||sortNumber!=0){
						newpd.put("steam_uid",steam_uid);
						newpd.put("name",name);
						newpd.put("putOnNumber",putOnNumber);
						newpd.put("buyerNumber",buyerNumber);
						newpd.put("saleNumber",saleNumber);
						newpd.put("salePrice",salePrice);
						newpd.put("sortNumber",sortNumber);
						newpd.put("day_anlyze_start",lasttoday);
						newpd.put("day_anlyze_stop",today);
						newpd.put("create_time",createTime);
						newpd.put("lastRanking",lastRanking);
						list.add(newpd);
					}
				}

				if(list!=null&&list.size()>0){
					dao.save("RobotMapper.insertSellerRankingReportForm19", list);
				}
				
				
				long tmp2 = System.currentTimeMillis();
				System.out.println("分析完毕");
				logger.info("分析"+today+"的数据完成。总共耗时为："+(tmp2-start)+"毫秒。");
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
	           
				
	}
	
}