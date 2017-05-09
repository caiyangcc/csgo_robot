package com.softisland.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softisland.dao.DaoSupport;
import com.softisland.dao.ScheduleDao;
import com.softisland.service.ScheduleService;
import com.softisland.util.PageData;
import com.softisland.util.TreasureProductEnum;
@Service
public class ScheduleServiceImpl implements ScheduleService{

	private static Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private DaoSupport dao; 
	
	
	
	
	
	@Override
	public List<Map<String, Object>> getAllUser() throws Exception {
		// TODO Auto-generated method stub
		List list= (List<Map<String, Object>>) dao.findForList("WalletMapper.getAllWalletDetaillistPage", null);
		return list;
	}

	@Override
	public List<Map<String, Object>> getAllBatch() throws Exception {
		// TODO Auto-generated method stub
		return scheduleDao.getAllBatch();
	}

	@Override
	public List<Map<String, Object>> getOnSalePro(String batchCode) throws Exception {
		// TODO Auto-generated method stub
		return scheduleDao.getOnSalePro(batchCode);
	}

	@Override
	public List<Map<String, Object>> getUpPro(String batchCode) throws Exception {
		// TODO Auto-generated method stub
		return scheduleDao.getUpPro(batchCode);
	}

	@Override
	@Transactional
	public void autoUp(String batch_code) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> pd = new HashMap<String,Object>();
		//查询该批次号是否有在售商品
		List<Map<String,Object>> onSaleList =scheduleDao.getOnSalePro(batch_code);
		if(onSaleList!=null&&onSaleList.size()>0){
			return;
		}
		else{
			//查询该批次是否有下架的商品
			List<Map<String,Object>> downList =scheduleDao.getUpPro(batch_code);
			if(downList!=null&&downList.size()>0){
			 //随即抽取一条上架
				Random rand = new Random();
				int code=0;
				do{
				int num=rand.nextInt(downList.size());
				Map<String,Object> p=downList.get(num);
				int id=(Integer) p.get("id");
				pd.put("id", id);
				SimpleDateFormat f1 = new SimpleDateFormat("yyyyMMdd");
				Calendar c1 = Calendar.getInstance();
				String issue=f1.format(c1.getTime());
				pd.put("issue",issue);
				long currentmillis=System.currentTimeMillis();
				Object current= new Timestamp(currentmillis);
				pd.put("putaway_date", current);
				pd.put("maturity_date", new Timestamp(currentmillis+TreasureProductEnum.Day_90.getTime()));
				pd.put("date_updated",current);
				code=(int) scheduleDao.autoUp(pd);
				if(code>0){
					scheduleDao.updateBatch(id);
				}
				}while(code==0);		
			}
			else{
			//删除该批次
				scheduleDao.deleteBatch(batch_code);
			}
		}
		
		
	}

	@Override
	public int updateBatch(int id) throws Exception {
		// TODO Auto-generated method stub
		return scheduleDao.updateBatch(id);
	}

	@Override
	public int deleteBatch(String batch_code) throws Exception {
		// TODO Auto-generated method stub
		return scheduleDao.deleteBatch(batch_code);
	}

	@Override
	public void updatePrice() throws Exception {
		// TODO Auto-generated method stub
		//查询最低价格，在售数量
		List<Map<String,Object>> dataList=scheduleDao.getDota2Data();
		if(dataList!=null&&dataList.size()>0){
			logger.info(dataList.size()+"---"+dataList.toString());
			int[] code=scheduleDao.updatePrice(dataList);
			logger.info("成功返回"+code.length);
		}
	}

	@Override
	public void addPayMent() throws Exception {
		// TODO Auto-generated method stub
		List<String> dataList=new ArrayList<String>();
		dataList.add("2016-12-01 11:58:00;2016120123574;353");
		dataList.add("2016-12-01 12:02:00;2016120137812;354");
		dataList.add("2016-12-01 12:05:00;2016120124875;355");
		dataList.add("2016-12-01 12:07:00;2016120135487;356");
		dataList.add("2016-12-01 12:10:00;2016120113245;357");
		dataList.add("2016-12-01 12:13:00;2016120144578;358");
		dataList.add("2016-12-01 12:15:00;2016120153789;359");
		dataList.add("2016-12-01 12:20:00;2016120123568;360");
		dataList.add("2016-12-01 12:22:00;2016120169453;361");
		dataList.add("2016-12-01 12:30:00;2016120156893;362");
		dataList.add("2016-12-01 12:25:00;2016120154368;363");
		dataList.add("2016-12-01 12:26:00;2016120165278;364");
		dataList.add("2016-12-01 12:28:00;2016120154357;366");
		dataList.add("2016-12-01 12:11:00;2016120145278;367");
		dataList.add("2016-12-01 12:40:00;2016120164283;368");
		dataList.add("2016-12-01 12:50:00;2016120126902;369");


		dataList.add("2016-12-02 10:10:00;2016120215638;370");
		dataList.add("2016-12-02 10:15:00;2016120223546;371");
		dataList.add("2016-12-02 10:18:00;2016120223154;372");
		dataList.add("2016-12-02 10:20:00;2016120222153;373");
		dataList.add("2016-12-02 10:23:00;2016120236471;374");
		dataList.add("2016-12-02 10:25:00;2016120231298;375");
		dataList.add("2016-12-02 10:26:00;2016120234879;376");
		dataList.add("2016-12-02 10:27:00;2016120269214;377");
		dataList.add("2016-12-02 10:29:00;2016120247584;378");
		dataList.add("2016-12-02 10:30:00;2016120234578;379");
		dataList.add("2016-12-02 10:31:00;2016120236458;380");
		dataList.add("2016-12-02 10:34:00;2016120231234;381");
		dataList.add("2016-12-02 10:40:00;2016120216172;382");
		
		
		
		List<PageData> addDataList=new ArrayList<PageData>();
		for(int i=0;i<dataList.size();i++){
			String initData=dataList.get(i);
			String[] initDataArry=initData.split(";");
			String time=initDataArry[0];
			String num=initDataArry[1];
			String poid=initDataArry[2];
			List<PageData> prodataList=(List<PageData>) dao.findForList("OneBuyObjectMapper.getAllPro", time);
			for(int j=0;j<prodataList.size();j++){
				PageData proData=prodataList.get(j);
				int id=(int) proData.get("id");
				PageData payMent=new PageData();
				payMent.put("ptid", id);
				payMent.put("poid", poid);
				payMent.put("pay_order_num", num);
				payMent.put("operator_id", "9b3803417f47461e94f97a0d82661a89");
				payMent.put("operator_modify_id", "9b3803417f47461e94f97a0d82661a89");
				payMent.put("create_time", time);
				payMent.put("update_time", time);
				addDataList.add(payMent);
			}
			//批量插入
			dao.save("OneBuyObjectMapper.savePayMent", addDataList);
			addDataList.clear();
		}	
	}

	@Override
	public void addCoupon() throws Exception {
		// TODO Auto-generated method stub
		List<String> dataList=new ArrayList<String>();
		dataList.add("2016-12-01 11:58:00;2016120123574;353;1201");
		dataList.add("2016-12-01 12:02:00;2016120137812;354;1201");
		dataList.add("2016-12-01 12:05:00;2016120124875;355;1201");
		dataList.add("2016-12-01 12:07:00;2016120135487;356;1201");
		dataList.add("2016-12-01 12:10:00;2016120113245;357;1201");
		dataList.add("2016-12-01 12:13:00;2016120144578;358;1201");
		dataList.add("2016-12-01 12:15:00;2016120153789;359;1201");
		dataList.add("2016-12-01 12:20:00;2016120123568;360;1201");
		dataList.add("2016-12-01 12:22:00;2016120169453;361;1201");
		dataList.add("2016-12-01 12:30:00;2016120156893;362;1201");
		dataList.add("2016-12-01 12:25:00;2016120154368;363;1201");
		dataList.add("2016-12-01 12:26:00;2016120165278;364;1201");
		dataList.add("2016-12-01 12:28:00;2016120154357;366;1201");
		dataList.add("2016-12-01 12:11:00;2016120145278;367;1201");
		dataList.add("2016-12-01 12:40:00;2016120164283;368;1201");
		dataList.add("2016-12-01 12:50:00;2016120126902;369;1201");
		
		
		dataList.add("2016-12-02 10:10:00;2016120215638;370;1202");
		dataList.add("2016-12-02 10:15:00;2016120223546;371;1202");
		dataList.add("2016-12-02 10:18:00;2016120223154;372;1202");
		dataList.add("2016-12-02 10:20:00;2016120222153;373;1202");
		dataList.add("2016-12-02 10:23:00;2016120236471;374;1202");
		dataList.add("2016-12-02 10:25:00;2016120231298;375;1202");
		dataList.add("2016-12-02 10:26:00;2016120234879;376;1202");
		dataList.add("2016-12-02 10:27:00;2016120269214;377;1202");
		dataList.add("2016-12-02 10:29:00;2016120247584;378;1202");
		dataList.add("2016-12-02 10:30:00;2016120234578;379;1202");
		dataList.add("2016-12-02 10:31:00;2016120236458;380;1202");
		dataList.add("2016-12-02 10:34:00;2016120231234;381;1202");
		dataList.add("2016-12-02 10:40:00;2016120216172;382;1202");
		
		String[] arr3=new String[]{"76561198271324266","76561198257937415","76561198326848418","76561198263235855","76561198329075624","76561198225175020","76561198134774303","76561198328060176","76561198176464203","76561198318531011","76561198273848498","76561198166493629","76561198093332287","76561198139862338","76561198205765443","76561198205765443","76561198075898762","76561198115153599","76561198276766444","76561198291424172","76561198199545410","76561198104971664","76561198291424172","76561198074262743","76561198315498587","76561198118846305","76561198307151275","76561198273848498","76561198118846305"};  
		List<PageData> addDataList=new ArrayList<PageData>();
		for(int i=0;i<dataList.size();i++){
			String initData=dataList.get(i);
			String[] initDataArry=initData.split(";");
			String time=initDataArry[0];
			String num=initDataArry[1];
			String poid=initDataArry[2];
			String initcode=initDataArry[3];
			List<PageData> prodataList=(List<PageData>) dao.findForList("OneBuyObjectMapper.getAllPro", time);
			for(int j=0;j<prodataList.size();j++){
				PageData proData=prodataList.get(j);
				int id=(int) proData.get("id");
				PageData payMent=new PageData();
				int index = (int) (Math.random() * arr3.length);
				String code=getRandomString(6);
				payMent.put("steam_uid", arr3[index]);
				payMent.put("code",initcode+code);
				payMent.put("status", 1);
				payMent.put("applied", proData.get("last_updated"));
				payMent.put("is_num", id);
				payMent.put("is_active", 1);
				payMent.put("date_created", time);
				payMent.put("description", num);
				addDataList.add(payMent);
			}
			//批量插入
			dao.save("OneBuyObjectMapper.saveCoupon", addDataList);
			addDataList.clear();
		}
	}

	
	public String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
}
