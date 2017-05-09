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
import com.softisland.entity.RobotTypeEnum;
import com.softisland.util.PageData;


@Service("reportJobForCustomerDetailService")
public class ReportJobForCustomerService {
	private Logger logger = Logger.getLogger(ReportJobForCustomerService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	
	public synchronized void work()  {
				logger.info("开始客户对账报表");
		        try{
		        PageData pd = new PageData();
				//查询当天未提现的金额
				List<PageData> balanceList = (List<PageData>) dao.findForList("RobotMapper.queryBalanceForCus", pd);
				String pulldate = null;
				if(balanceList!=null&&balanceList.size()>0){
					pulldate=String.valueOf((Timestamp)balanceList.get(0).get("pulltime"));
				}
				long start = System.currentTimeMillis();
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				String today=f.format(c.getTime()); //当天
				c.add(Calendar.DAY_OF_MONTH, -1);
				String lasttoday=f.format(c.getTime()); //获取前一天
				String day_anlyze_start = lasttoday + " 00:00:00.000";
				String day_anlyze_stop = today + " 00:00:00.000";
				pd.put("day_anlyze_start", day_anlyze_start);
				pd.put("day_anlyze_stop", day_anlyze_stop);
				c.add(Calendar.DAY_OF_MONTH, -1);
				String lasttwotoday=f.format(c.getTime()); //获取前两天
				pd.put("last_day_anlyze_start", lasttwotoday);
				
				//查询充值金额，充值手续费
				List<PageData> rechargeList = (List<PageData>) dao.findForList("RobotMapper.queryRechargeForCus", pd);
				//查询提现金额，提现手续费
				List<PageData> withdrawList = (List<PageData>) dao.findForList("RobotMapper.queryWithdrawForCus", pd);
				//查询当天采购
				List<PageData> buyList = (List<PageData>) dao.findForList("RobotMapper.queryCurrentBuyForCus", pd);
				
				//查询当天收入
				List<PageData> incomeList = (List<PageData>) dao.findForList("RobotMapper.queryIncomeForCus", pd);
				
				//查询期初余额
				List<PageData> lastList = (List<PageData>) dao.findForList("RobotMapper.queryLastForCus", pd);
				
				//查询一元购收入
				List<PageData> oneBuySellerList = (List<PageData>) dao.findForList("RobotMapper.queryOneBuySellerForCus", pd);
				
				//查询一元购购买
				List<PageData> oneBuyBuyerList = (List<PageData>) dao.findForList("RobotMapper.queryOneBuyBuyerForCus", pd);
				
				//查询平台退款			
				List<PageData> platformList=(List<PageData>) dao.findForList("RobotMapper.queryPlatformListForCus", pd);
				
				//查询c2c结算收入
				List<PageData> c2cIncomeList=(List<PageData>) dao.findForList("RobotMapper.c2cIncome", pd);
				
				//查询c2c结算支出
				List<PageData> c2cPayList=(List<PageData>) dao.findForList("RobotMapper.c2cPay", pd);
				
				//查询c2c管理费支出
				List<PageData> c2cFeeList=(List<PageData>) dao.findForList("RobotMapper.c2cFee", pd);
				
				//查询营销费用
				List<PageData> voucherList=(List<PageData>) dao.findForList("RobotMapper.voucherForCus", pd);
				
				//查询cdk收入
				List<PageData> cdkIncomeList=(List<PageData>) dao.findForList("RobotMapper.cdkIncome", pd);
				//查询cdk支出
				List<PageData> cdkPayList=(List<PageData>) dao.findForList("RobotMapper.cdkPay", pd);
				//查询cdk退款收入
				List<PageData> cdkReturnList=(List<PageData>) dao.findForList("RobotMapper.cdkReturn", pd);
				
				//查询cdk退款支出
				List<PageData> cdkReturnListPay=(List<PageData>) dao.findForList("RobotMapper.cdkReturnPay", pd);
				//查询cdk保障金收入
				List<PageData> cdkSecurityIncomeList=(List<PageData>) dao.findForList("RobotMapper.cdkSecurityIncome", pd);
				//查询cdk保障金支出
				List<PageData> cdkSecurityPayList=(List<PageData>) dao.findForList("RobotMapper.cdkSecurityPay", pd);
				
				//查询平台收入
				List<PageData> platformIncomeList=(List<PageData>) dao.findForList("RobotMapper.platformIncome", pd);
						
				//查询平台支出
						
				List<PageData> platformPayList=(List<PageData>) dao.findForList("RobotMapper.platformPay", pd);
				
				//查询开箱收入
				List<PageData> boxIncomeList=(List<PageData>) dao.findForList("RobotMapper.boxIncome", pd);
				
				//查询开箱支出
				List<PageData> boxPayList=(List<PageData>) dao.findForList("RobotMapper.boxPay", pd);
				
				//查询开箱回购收入
				List<PageData> boxReturnIncomeList=(List<PageData>) dao.findForList("RobotMapper.boxReturnIncome", pd);
				
				//查询开箱回购支出
				List<PageData> boxReturnPayList=(List<PageData>) dao.findForList("RobotMapper.boxReturnPay", pd);
				
				
				//查询抢座位收入
				List<PageData> seatIncomeList=(List<PageData>) dao.findForList("RobotMapper.seatIncome", pd);
				
				//查询抢座位支出
				List<PageData> seatPayList=(List<PageData>) dao.findForList("RobotMapper.seatPay", pd);
				
				
				List<RobotTypeEnum> robotTypeEnums = RobotTypeEnum.getTwoSaleTypeList();
				
				List<PageData> list=new ArrayList();
				
				SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar c1 = Calendar.getInstance();
				String createTime=f1.format(c1.getTime());
				for(int i=0;i<robotTypeEnums.size();i++){
					String code=String.valueOf(robotTypeEnums.get(i).getCode());
					String name=robotTypeEnums.get(i).getName();
					double currentCash=0;//钱包余额
					double income=0; //收入
					double withdrawCash=0;//提现金额
					double withdrawFee=0;//提现手续费
					double buyCash=0;//购买
					double rechargeCash=0;//充值金额
					double rechargeFee=0;//充值手续费
					double lastNodrawCash=0;//期初余额
					double countCurrentCash=0;//计算的钱包余额
					double onebuyIncome=0; //一元购收入
					double onebuyBuyCash=0;//一元购购买
					double platformReturnMoney=0;//平台退款金额
					double c2cIncome=0;//c2c结算收入
					double c2cPay=0;//c2c结算支出
					double c2cFee=0;//c2c管理费
					double voucher_money=0;//营销费收入
					double buyer_fee_money=0;//营销费支出
					double cdkIncome=0;//cdk收入
					double cdkPay=0;//cdk支出
					double cdkReturn=0;//cdk退款
					double cdkReturnPay=0;//cdk退款支出
					double platformIncome=0;//平台收入
					double platformPay=0;//平台支出
					double boxIncome=0;//箱子收入
					double boxPay=0;//箱子支出
					double boxReturnIncome=0;//回购箱子收入
					double boxReturnPay=0;//回购箱子支出
					double cdkSecurityIncome=0;//cdk保障金收入
					double cdkSecurityPay=0;//cdk保障金支出
					double seatIncome=0;//抢座位收入
					double seatPay=0;//抢座位支出
					PageData newpd=new PageData();
					
					for(int j=0;j<balanceList.size();j++){
						PageData p=balanceList.get(j);
						if(p.get("styletype").equals(code)){
							currentCash= currentCash+((BigDecimal)p.get("total")).doubleValue();
						}
					}
					
					for(int j=0;j<incomeList.size();j++){
						PageData p=incomeList.get(j);
						if(p.get("styletype").equals(code)){
							income=income+((BigDecimal)p.get("incomemoney")).doubleValue();
						}
					}
					
					for(int j=0;j<withdrawList.size();j++){
						PageData p=withdrawList.get(j);
						if(p.get("styletype").equals(code)){
							withdrawCash=withdrawCash+((BigDecimal)p.get("withdrawmoney")).doubleValue();
							withdrawFee=withdrawFee+((BigDecimal)p.get("withdrawfee")).doubleValue();
							
						}
					}
					
					
					for(int j=0;j<rechargeList.size();j++){
						PageData p=rechargeList.get(j);
						if(p.get("styletype").equals(code)){
							rechargeCash=((BigDecimal)p.get("rechargemoney")).doubleValue();
							rechargeFee=((BigDecimal)p.get("rechargefee")).doubleValue();
						}
					}
					
					for(int j=0;j<buyList.size();j++){
						PageData p=buyList.get(j);
						if(p.get("styletype").equals(code)){
							buyCash=buyCash+((BigDecimal) p.get("buymoney")).doubleValue();
							buyer_fee_money=buyer_fee_money+((BigDecimal) p.get("fee_money")).doubleValue();
							
						}
					}
					
					for(int j=0;j<lastList.size();j++){
						PageData p=lastList.get(j);
						if(String.valueOf(p.get("styletype")).equals(code)){
							lastNodrawCash=lastNodrawCash+(double) p.get("lastmoney");
							
						}
					}
					
					for(int j=0;j<oneBuySellerList.size();j++){
						PageData p=oneBuySellerList.get(j);
						if(p.get("styletype").equals(code)){
							onebuyIncome=onebuyIncome+((BigDecimal) p.get("incomemoney")).doubleValue();						
						}			
					}
					
					for(int j=0;j<oneBuyBuyerList.size();j++){
						PageData p=oneBuyBuyerList.get(j);
						if(p.get("styletype").equals(code)){
							onebuyBuyCash=onebuyBuyCash+((BigDecimal) p.get("buymoney")).doubleValue();						
						}			
					}
					
					for(int j=0;j<platformList.size();j++){
						PageData p=platformList.get(j);
						if(p.get("styletype").equals(code)){
							platformReturnMoney=platformReturnMoney+((BigDecimal) p.get("platformreturnmoney")).doubleValue();						
						}			
					}
					
					for(int j=0;j<c2cIncomeList.size();j++){
						PageData p=c2cIncomeList.get(j);
						if(p.get("styletype").equals(code)){
							c2cIncome=c2cIncome+((BigDecimal) p.get("incomemoney")).doubleValue();						
						}			
					}
					
					for(int j=0;j<c2cPayList.size();j++){
						PageData p=c2cPayList.get(j);
						if(p.get("styletype").equals(code)){
							c2cPay=c2cPay+((BigDecimal) p.get("paymoney")).doubleValue();						
						}			
					}
					
					for(int j=0;j<c2cFeeList.size();j++){
						PageData p=c2cFeeList.get(j);
						if(p.get("styletype").equals(code)){
							c2cFee=c2cFee+((BigDecimal) p.get("feemoney")).doubleValue();						
						}			
					}
					
					
					for(int j=0;j<voucherList.size();j++){
						PageData p=voucherList.get(j);
						if(p.get("styletype").equals(code)){
							voucher_money=voucher_money+((BigDecimal) p.get("vouchermoney")).doubleValue();						
						}			
					}
					
					for(int j=0;j<cdkIncomeList.size();j++){
						PageData p=cdkIncomeList.get(j);
						if(p.get("styletype").equals(code)){
							cdkIncome=cdkIncome+((BigDecimal) p.get("cdkincome")).doubleValue();						
						}			
					}
					
					for(int j=0;j<cdkPayList.size();j++){
						PageData p=cdkPayList.get(j);
						if(p.get("styletype").equals(code)){
							cdkPay=cdkPay+((BigDecimal) p.get("cdkpay")).doubleValue();						
						}			
					}
					
					for(int j=0;j<cdkReturnList.size();j++){
						PageData p=cdkReturnList.get(j);
						if(p.get("styletype").equals(code)){
							cdkReturn=cdkReturn+((BigDecimal) p.get("cdkreturn")).doubleValue();						
						}			
					}
					for(int j=0;j<cdkReturnListPay.size();j++){
						PageData p=cdkReturnListPay.get(j);
						if(p.get("styletype").equals(code)){
							cdkReturnPay=cdkReturnPay+((BigDecimal) p.get("cdkreturn")).doubleValue();						
						}			
					}
					
					
					for(int j=0;j<platformIncomeList.size();j++){
						PageData p=platformIncomeList.get(j);
						if(p.get("styletype").equals(code)){
							platformIncome=platformIncome+((BigDecimal) p.get("incomemoney")).doubleValue();						
						}
					}
					
					for(int j=0;j<platformPayList.size();j++){
						PageData p=platformPayList.get(j);
						if(p.get("styletype").equals(code)){
							platformPay=platformPay+((BigDecimal) p.get("buymoney")).doubleValue();						
						}
					}
					
					for(int j=0;j<boxIncomeList.size();j++){
						PageData p=boxIncomeList.get(j);
						if(p.get("styletype").equals(code)){
							boxIncome=boxIncome+((BigDecimal) p.get("incomemoney")).doubleValue();						
						}
					}
					
					for(int j=0;j<boxPayList.size();j++){
						PageData p=boxPayList.get(j);
						if(p.get("styletype").equals(code)){
							boxPay=boxPay+((BigDecimal) p.get("buymoney")).doubleValue();						
						}
					}
					
					for(int j=0;j<boxReturnIncomeList.size();j++){
						PageData p=boxReturnIncomeList.get(j);
						if(p.get("styletype").equals(code)){
							boxReturnIncome=boxReturnIncome+((BigDecimal) p.get("incomemoney")).doubleValue();						
						}
					}
					
					for(int j=0;j<boxReturnPayList.size();j++){
						PageData p=boxReturnPayList.get(j);
						if(p.get("styletype").equals(code)){
							boxReturnPay=boxReturnPay+((BigDecimal) p.get("paymoney")).doubleValue();						
						}
					}
					
					for(int j=0;j<cdkSecurityIncomeList.size();j++){
						PageData p=cdkSecurityIncomeList.get(j);
						if(p.get("styletype").equals(code)){
							cdkSecurityIncome=cdkSecurityIncome+((BigDecimal) p.get("cdkincome")).doubleValue();						
						}
					}
					
					
					for(int j=0;j<cdkSecurityPayList.size();j++){
						PageData p=cdkSecurityPayList.get(j);
						if(p.get("styletype").equals(code)){
							cdkSecurityPay=cdkSecurityPay+((BigDecimal) p.get("cdkpay")).doubleValue();						
						}
					}
					
					for(int j=0;j<seatIncomeList.size();j++){
						PageData p=seatIncomeList.get(j);
						if(p.get("styletype").equals(code)){
							seatIncome=seatIncome+((BigDecimal) p.get("seatincome")).doubleValue();						
						}
					}
					
					for(int j=0;j<seatPayList.size();j++){
						PageData p=seatPayList.get(j);
						if(p.get("styletype").equals(code)){
							seatPay=seatPay+((BigDecimal) p.get("seatpay")).doubleValue();						
						}
					}
					
					countCurrentCash=lastNodrawCash+income+onebuyIncome+c2cIncome+cdkIncome+rechargeCash+voucher_money+platformIncome+boxIncome+boxReturnIncome+cdkSecurityIncome+seatIncome-rechargeFee-withdrawCash-withdrawFee-buyCash-onebuyBuyCash+platformReturnMoney+cdkReturn-c2cPay-cdkPay-c2cFee-buyer_fee_money-platformPay-boxPay-boxReturnPay-cdkSecurityPay-cdkReturnPay-seatPay;
					if(currentCash!=0||income!=0||withdrawCash!=0||withdrawFee!=0||buyCash!=0||rechargeCash!=0||rechargeFee!=0||lastNodrawCash!=0||onebuyIncome!=0||onebuyBuyCash!=0||platformReturnMoney!=0||c2cIncome!=0||c2cPay!=0||c2cFee!=0||voucher_money!=0||buyer_fee_money!=0||cdkIncome!=0||cdkPay!=0||cdkReturn!=0||platformIncome!=0||platformPay!=0||boxIncome!=0||boxPay!=0||boxReturnIncome!=0||boxReturnPay!=0||cdkSecurityIncome!=0||cdkSecurityPay!=0||cdkReturnPay!=0||seatIncome!=0||seatPay!=0){
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
						newpd.put("user_type_name",name);
						newpd.put("create_time",createTime);
						newpd.put("pull_time",pulldate);
						newpd.put("countCurrentCash", countCurrentCash);
						newpd.put("onebuyIncome", onebuyIncome);
						newpd.put("onebuyBuyCash", onebuyBuyCash);
						newpd.put("platformReturnMoney", platformReturnMoney);
						newpd.put("c2cIncome", c2cIncome);
						newpd.put("c2cPay", c2cPay);
						newpd.put("c2cFee", c2cFee);
						newpd.put("voucher_money", voucher_money);
						newpd.put("buyer_fee_money", buyer_fee_money);
						newpd.put("cdkIncome", cdkIncome);
						newpd.put("cdkPay", cdkPay);
						newpd.put("cdkReturn", cdkReturn);
						newpd.put("cdkReturnPay", cdkReturnPay);
						newpd.put("platformIncome", platformIncome);
						newpd.put("platformPay", platformPay);
						newpd.put("boxIncome", boxIncome);
						newpd.put("boxPay", boxPay);
						newpd.put("box_return_income", boxReturnIncome);
						newpd.put("box_return_pay", boxReturnPay);
						newpd.put("cdk_security_income", cdkSecurityIncome);
						newpd.put("cdk_security_pay", cdkSecurityPay);
						newpd.put("seat_income", seatIncome);
						newpd.put("seat_pay", seatPay);
						list.add(newpd);
					}
				}

				if(list!=null&&list.size()>0){
					dao.save("RobotMapper.insertCustomerReportForm", list);
				}
				
				
				long tmp2 = System.currentTimeMillis();
				logger.info("分析"+lasttoday+"的数据完成。总共耗时为："+(tmp2-start)+"毫秒。");
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
	           
				
	}
	
}