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


@Service("reportJobForWalletDetailService")
public class ReportJobForWalletDetailService {
	private Logger logger = Logger.getLogger(ReportJobForWalletDetailService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	public synchronized void work()  {
				logger.info("开始钱包流水报表");
				long start = System.currentTimeMillis();
		        try{
		        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				String today=f.format(c.getTime()); //当天
				c.add(Calendar.DAY_OF_MONTH, -1);
				String lasttoday=f.format(c.getTime()); //获取前一天
				PageData pd = new PageData();
				String day_anlyze_start = lasttoday+" 00:00:00.000";
				String day_anlyze_stop = today+" 00:00:00.000";
				pd.put("day_anlyze_start", day_anlyze_start);
				pd.put("day_anlyze_stop", day_anlyze_stop);
				//查询当天用户的余额
				List<PageData> balanceList = (List<PageData>) dao.findForList("RobotMapper.queryBalance", pd);
				String pulldate = null;
				if(balanceList!=null&&balanceList.size()>0){
					pulldate=String.valueOf((Timestamp)balanceList.get(0).get("pulltime"));
				}
					
				//查询充值金额，充值手续费
				List<PageData> rechargeList = (List<PageData>) dao.findForList("RobotMapper.queryRecharge", pd);
				//查询提现金额，提现手续费
				List<PageData> withdrawList = (List<PageData>) dao.findForList("RobotMapper.queryWithdraw", pd);
				//查询当天采购
				List<PageData> buyList = (List<PageData>) dao.findForList("RobotMapper.queryBuy", pd);
				//查询当天收入
				List<PageData> incomeList = (List<PageData>) dao.findForList("RobotMapper.queryIncome", pd);
				//查询期初余额
				List<PageData> lastList = (List<PageData>) dao.findForList("RobotMapper.queryLast", pd);
				//查询退款
				List<PageData> returnList = (List<PageData>) dao.findForList("RobotMapper.queryReturn", pd);
				//查询追款
				List<PageData> askList = (List<PageData>) dao.findForList("RobotMapper.queryAsk", pd);
				//查询优惠券
				List<PageData> couponList = (List<PageData>) dao.findForList("RobotMapper.queryCoupon", pd);
				
				List<PageData> list=new ArrayList();
				
				SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar c1 = Calendar.getInstance();
				String createTime=f1.format(c1.getTime());
				for(int i=0;i<balanceList.size();i++){
					int code=(int) balanceList.get(i).get("user_type");
					String name=(String) balanceList.get(i).get("steam_uid");
					double currentCash=((BigDecimal)balanceList.get(i).get("total")).doubleValue();//钱包余额
					double income=0; //收入
					double withdrawCash=0;//提现金额
					double withdrawFee=0;//提现手续费
					double buyCash=0;//购买
					double rechargeCash=0;//充值金额
					double rechargeFee=0;//充值手续费
					double lastNodrawCash=0;//期初余额
					double returnCash=0;//退款金额
					double askCash=0;//追款金额
					double countCurrentCash=0; //计算出的钱包余额
					double queryCoupon=0;
					PageData newpd=new PageData();
					
					for(int j=0;j<rechargeList.size();j++){
						PageData p=rechargeList.get(j);
						if(p.get("steamid").equals(name)){
							rechargeCash=((BigDecimal)p.get("rechargemoney")).doubleValue();
							rechargeFee=((BigDecimal)p.get("rechargefee")).doubleValue();
							rechargeList.remove(j);
							break;
						}
					}
					
					for(int j=0;j<incomeList.size();j++){
						PageData p=incomeList.get(j);
						if(p.get("steamid").equals(name)){
							income=((BigDecimal)p.get("incomemoney")).doubleValue();
							incomeList.remove(j);
							break;
						}
					}
					
					for(int j=0;j<withdrawList.size();j++){
						PageData p=withdrawList.get(j);
						if(p.get("steamid").equals(name)){
							withdrawCash=((BigDecimal)p.get("withdrawmoney")).doubleValue();
							withdrawFee=((BigDecimal)p.get("withdrawfee")).doubleValue();
							withdrawList.remove(j);
							break;
							
						}
					}
					
					
					for(int j=0;j<buyList.size();j++){
						PageData p=buyList.get(j);
						if(p.get("steamid").equals(name)){
							buyCash=((BigDecimal) p.get("buymoney")).doubleValue();
							buyList.remove(j);
							break;
							
						}
					}
					
					for(int j=0;j<lastList.size();j++){
						PageData p=lastList.get(j);
						if(p.get("steamid").equals(name)){
							lastNodrawCash=((BigDecimal)p.get("lastmoney")).doubleValue();
							lastList.remove(j);
							break;
							
						}
					}
					
					for(int j=0;j<returnList.size();j++){
						PageData p=returnList.get(j);
						if(p.get("steamid").equals(name)){
							returnCash=((BigDecimal)p.get("returnmoney")).doubleValue();
							returnList.remove(j);
							break;
							
						}
					}
					
					
					for(int j=0;j<askList.size();j++){
						PageData p=askList.get(j);
						if(p.get("steamid").equals(name)){
							askCash=((BigDecimal)p.get("askmoney")).doubleValue();
							askList.remove(j);
							break;
							
						}
					}
					
					
				
					for(int j=0;j<couponList.size();j++){
						PageData p=couponList.get(j);
						if(p.get("steamid").equals(name)){
							queryCoupon=((BigDecimal)p.get("couponmoney")).doubleValue();
							couponList.remove(j);
							break;
							
						}
					}
				
					
					buyCash=buyCash-returnCash;
					income=income-askCash+queryCoupon;
					countCurrentCash=lastNodrawCash+income+rechargeCash-rechargeFee-withdrawCash-withdrawFee-buyCash;
				if(currentCash!=0||income!=0||withdrawCash!=0||withdrawFee!=0||buyCash!=0||rechargeCash!=0||rechargeFee!=0||lastNodrawCash!=0){
						newpd.put("currentCash",currentCash);
						newpd.put("income",income);
						newpd.put("withdrawCash",withdrawCash);
						newpd.put("withdrawFee",withdrawFee);
						newpd.put("buyCash",buyCash);
						newpd.put("rechargeCash",rechargeCash);
						newpd.put("rechargeFee",rechargeFee);
						newpd.put("lastNodrawCash",lastNodrawCash);
						newpd.put("anlyze_time",lasttoday);
						newpd.put("user_type_code",code);
						String user_type_name;
						if(code==1){
							user_type_name="普通用户";
						}
						else if(code==3){
							user_type_name="vip用户";
						}
						else if(code==4){
							user_type_name="自营账户";
						}else{
							user_type_name="大卖家";
						}
						newpd.put("user_type_name",user_type_name);
						newpd.put("steam_uid",name);
						newpd.put("create_time",createTime);
						newpd.put("pull_time",pulldate);
						newpd.put("countCurrentCash", countCurrentCash);
						list.add(newpd);
						if(list!=null&&list.size()>0&&list.size()==20000){
							//logger.error("最终用户为:"+list.size()+"---"+list.toString());
							dao.save("RobotMapper.insertWalletReportForm", list);
							list.clear();
						}
						else if(list!=null&&list.size()>0&&i==balanceList.size()-1){
							dao.save("RobotMapper.insertWalletReportForm", list);
						}
					}
				}

				/*if(list!=null&&list.size()>0){
					//logger.error("最终用户为:"+list.size()+"---"+list.toString());
					dao.save("RobotMapper.insertWalletReportForm", list);
				}*/
				
				
				long tmp2 = System.currentTimeMillis();
				logger.info("分析"+today+"的数据完成。总共耗时为："+(tmp2-start)+"毫秒。");
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
	           
				
	}
	
}