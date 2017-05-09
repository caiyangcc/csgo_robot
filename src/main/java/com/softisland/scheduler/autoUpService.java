/*package com.softisland.scheduler;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.util.PageData;
import com.softisland.util.TreasureProductEnum;





@Component
@EnableScheduling
public class autoUpService {
	private Logger logger = Logger.getLogger(autoUpService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	private ExecutorService executeService = null;
	
	@PostConstruct
    public void initThreadPool()
    {
        executeService = Executors.newFixedThreadPool(4);
    }
	
	*//**
	 * 摧毁线程池
	 *//*
    @PreDestroy
    public void shutdownThreadPool()
    {
        executeService.shutdown();
    }
	
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 1 * 60 * 1000)
	public void start() throws Exception{
    	LinkedBlockingQueue<String> batchQueue = new LinkedBlockingQueue<String>();
    	//查询所有的批次
    	List<PageData> batchList =(List<PageData>) dao.findForList("OneBuyObjectMapper.getAllBatch", null);
    	if(batchList==null||batchList.size()==0){
    		return;
    	}
    	else{
    		
    		for(PageData p:batchList){
    			String batch_code=(String) p.get("batch_code");
    			batchQueue.put(batch_code);
    		}
    		
    		for(int i=0;i<batchList.size();i++){
    			Task tasks = new Task(batchQueue);
 				executeService.execute(tasks);
    		}
    	}
    	
    }
	
    private class Task  implements Runnable{
    	private LinkedBlockingQueue<String> BatchQueue;
    	public Task(LinkedBlockingQueue<String> bQueue){
    		this.BatchQueue=bQueue;
    	}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				PageData pd = new PageData();
				String batch_code=BatchQueue.take();
				pd.put("batch_code", batch_code);
				//查询该批次号是否有在售商品
				List<PageData> onSaleList =(List<PageData>) dao.findForList("OneBuyObjectMapper.getOnSaleProduct", pd);
				if(onSaleList!=null&&onSaleList.size()>0){
					return;
				}
				else{
				//查询该批次是否有下架的商品
					List<PageData> downList =(List<PageData>) dao.findForList("OneBuyObjectMapper.downList", pd);
					if(downList!=null&&downList.size()>0){
					 //随即抽取一条上架
						Random rand = new Random();
						int code=0;
						do{
						int num=rand.nextInt(downList.size());
						PageData p=downList.get(num);
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
						code=(int) dao.update("OneBuyObjectMapper.autoUpProductchangeStatus", pd);
						if(code>0){
							dao.update("OneBuyObjectMapper.updateBatchForm", pd);
						}
						}while(code==0);		
					}
					else{
					//删除该批次
						dao.delete("OneBuyObjectMapper.deleteBatchAuto", pd);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    	
    }
}*/