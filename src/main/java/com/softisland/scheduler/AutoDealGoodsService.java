package com.softisland.scheduler;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.softisland.dao.DaoSupport;
import com.softisland.dao.DaoSupport2;
import com.softisland.entity.CheckQueue;
import com.softisland.util.PageData;


@Service("autoDealGoodsService")
public class AutoDealGoodsService {
	private Logger logger = Logger.getLogger(AutoDealGoodsService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao2;
	
	 @Autowired  
	 RedisTemplate<String,String> redisTemplate;
	 
	private ExecutorService executeService = null;
	
	@PostConstruct
    public void initThreadPool()
    {
        executeService = Executors.newFixedThreadPool(2);
    }
	
	/**
	 * 摧毁线程池
	 */
    @PreDestroy
    public void shutdownThreadPool()
    {
        executeService.shutdown();
    }
	

    
    
	public void work() throws Exception{
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		String current=f.format(c.getTime()); 
		c.add(Calendar.MINUTE, -180);
		String lastMIn=f.format(c.getTime()); 
		PageData p=new PageData();
		p.put("current", current);
		p.put("lastMIn", lastMIn);
		
		LinkedBlockingQueue<List<PageData>> batchQueue = new LinkedBlockingQueue<List<PageData>>();
		
    	List<PageData> goodsList =(List<PageData>) dao2.findForList("QueueMapper.getAllDealGoods", p);
    	//分组
    	Map<String, List<PageData>> tagMap=goodsList.stream().collect(Collectors.groupingBy(PageData -> String.valueOf((int)PageData.get("pt_id"))));

  
    	if(goodsList==null||goodsList.size()==0){
    		return;
    	}
    	else{
    		for(Entry<String, List<PageData>> entry : tagMap.entrySet()){
    			//修改取出来的值为系统正在处理中
    			String pt_id=entry.getKey();
    			dao.update("QueueMapper.updateJobStatusToIng", pt_id);
    			List<PageData> goods = entry.getValue();
    			batchQueue.put(goods);
    		}
    		
    		for(Entry<String, List<PageData>> entry : tagMap.entrySet()){	
    			Task tasks = new Task(batchQueue);
 				executeService.execute(tasks);
    		}
    	}
    	
		//downBox uy=new downBox(p);
		//executeService.execute(uy);
	}
	
	 
	 
	 /**
	  * 
	  * @author Administrator
	  *
	  */
	 private class Task implements Runnable{

		 private LinkedBlockingQueue<List<PageData>> BatchQueue;
		 
		 public Task(LinkedBlockingQueue<List<PageData>> bQueue){
	    		this.BatchQueue=bQueue;
	    	}
			
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				List<PageData> datalist=BatchQueue.take();
				for(int i=0;i<datalist.size();i++){
				PageData data=datalist.get(i);
				String app_id=String.valueOf((int) data.get("app_id"));
				String id=String.valueOf((int) data.get("pt_id"));
				String steamPid=(String) data.get("steamPid");
				String robotSteamUid=(String) data.get("robotSteamUid");
				Timestamp date_store=(Timestamp) data.get("date_store");
				SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String lock_time=null;
				if(date_store!=null){
					lock_time=f1.format(date_store);
				} 
				//h1z1验证
				if(app_id.equals("433850")||app_id.equals("295110")){
				logger.info(app_id+"&"+id+"正在执行");
					//验证pid是否相等
				int code=(int) dao2.findForObject("QueueMapper.checkPid", data);
				if(code>0){//说明pid相等，处理吞货放入吞货表里
					CheckQueue queue;
					if(app_id.equals("433850")){
						queue=new CheckQueue(id,robotSteamUid,steamPid,lock_time);
					}
					else{
						//295110需要把锁定时间在date_store的基础上往后延续7天
						String lock_time1=null;
						if(date_store!=null){
						Calendar ca = Calendar.getInstance();
						ca.setTime(date_store);
						ca.add(Calendar.DATE, 7);
						Date current=ca.getTime();
						lock_time1=f1.format(current);
						}
						queue=new CheckQueue(id,robotSteamUid,steamPid,lock_time1);
					}
					saveCheckQueue(queue);
					logger.info(id+"处理了吞货");
					break;
				}

				}
				//验证TF2
				else if(app_id.equals("440")||app_id.equals("570")){
					logger.info(app_id+"&"+id+"正在执行");
					//验证pid对否存在于product_trade表里
					int is_exist=(int) dao2.findForObject("QueueMapper.checkPidIsExist", data);
					if(is_exist==0){
						//验证oid是否相等
						int code=(int) dao2.findForObject("QueueMapper.checkOid", data);
						if(code>0){//说明oid相等，处理吞货
							CheckQueue queue;
							queue=new CheckQueue(id,robotSteamUid,steamPid,lock_time);
							saveCheckQueue(queue);
							logger.info(data.get("pt_id")+"处理了吞货");
							break;
						}
					}
				}
				
				//验证csgo
				else if(app_id.equals("730")){
					logger.info(app_id+"&"+id+"正在执行");
					//验证pid对否存在于product_trade表里
					int is_exist=(int) dao2.findForObject("QueueMapper.checkPidIsExist", data);
					if(is_exist==0){
						String product_category_id=String.valueOf((int) data.get("product_category_id"));
							//标品
							if(product_category_id.equals("6")||product_category_id.equals("7")||product_category_id.equals("8")||product_category_id.equals("9")
							||product_category_id.equals("10")||product_category_id.equals("11")||product_category_id.equals("12")||product_category_id.equals("13"))
							{
							
							CheckQueue queue;
							queue=new CheckQueue(id,robotSteamUid,steamPid,lock_time);
							saveCheckQueue(queue);
							logger.info(id+"处理了吞货");
							break;
							}
							//武器
							else{

							//判断描述信息是否为空
							String descriptions=(String) data.get("descriptions");
							if(!StringUtils.isEmpty(descriptions)){
								//判断链接是否为空
								 JSONObject jsonObject = JSONObject.parseObject(descriptions);
								 String actions_link=jsonObject.getString("actions_link");
								 if(!StringUtils.isEmpty(actions_link)){
										//按关键字D分隔字符，并取最后一个D后面的内容
										String[] actions_link_array=actions_link.split("D");
										if(actions_link_array!=null&&actions_link_array.length>0){
											String actions=actions_link_array[actions_link_array.length-1];
											String r_actions=(String) data.get("actions");
											//当两边的actions一致的时候，处理吞货
											if(!StringUtils.isEmpty(r_actions)&&!StringUtils.isEmpty(actions)&&r_actions.equals(actions)){
												CheckQueue queue;
												queue=new CheckQueue(id,robotSteamUid,steamPid,lock_time);
												saveCheckQueue(queue);
												logger.info(id+"处理了吞货");
												break;
											}	
										}
											 
								 }
						
							}
							
							}
					}
				}	
				//验证卡牌
				else if(app_id.equals("753")){
					logger.info(app_id+"&"+id+"正在执行");
					//验证pid对否存在于product_trade表里
					int is_exist=(int) dao2.findForObject("QueueMapper.checkPidIsExist", data);
					if(is_exist==0){
						CheckQueue queue;
						queue=new CheckQueue(id,robotSteamUid,steamPid,lock_time);
						saveCheckQueue(queue);
						logger.info(id+"处理了吞货");
						break;
					}
				}
				}
				
					PageData data1=datalist.get(0);
					//验证是否已经有吞货处理了
					int num=(int) dao.findForObject("QueueMapper.findIsGoodsDeal", data1);
					if(num>0){//说明没有处理到吞货，直接改成运维处理
						data1.put("remark", "该pid没有处理到吞货，改为运维处理");
						updateJobStatus(data1);	
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
	 }
	 
	 
		public void saveCheckQueue(CheckQueue queue) throws Exception{
			//验证商品状态
			PageData data=(PageData) dao2.findForObject("QueueMapper.checkStatus", queue);
			if(data!=null){
			//验证是否有相同的商品id(product_trade_handle_queue表pt_id)
			int num=(int) dao.findForObject("QueueMapper.checkqueuept_id", queue);
			//验证是否有相同的pid(product_trade_handle_queue表new_steam_pid)
			int flag=(int) dao.findForObject("QueueMapper.checkqueuenew_steam_pid", queue);
			if(num>0&&flag==0){//验证有相同的pt_id但没有相同的pid
				//关闭掉以前的
				queue.setRemark("有新的处理任务，本任务结束");
				int code1=(int) dao.update("QueueMapper.updateOldStauts", queue);
				if(code1>0){
					//新增队列
					queue.setApp_id(String.valueOf((int)data.get("app_id")));
					queue.setStatus(String.valueOf((int)data.get("status")));
					int code=(int) dao.update("QueueMapper.updateJobStatusForAlready", queue);;
					if(code>0){
						dao.save("QueueMapper.saveCheckQueue", queue);
					}
				}
			}
			else if(num==0&&flag==0){
				//新增队列
				queue.setApp_id(String.valueOf((int)data.get("app_id")));
				queue.setStatus(String.valueOf((int)data.get("status")));
				int code=(int) dao.update("QueueMapper.updateJobStatusForAlready", queue);;
				if(code>0){
					dao.save("QueueMapper.saveCheckQueue", queue);
				}
			}
			else{
				logger.error(queue.getPt_id()+"商品有相同的pid");
			
			}
			}
			else{
				logger.error(queue.getPt_id()+"商品状态不是0,6,11");
			}	
		}
		
		
		public void updateJobStatus(PageData pd) throws Exception{
			dao.update("QueueMapper.updateJobStatus", pd);
		}
}