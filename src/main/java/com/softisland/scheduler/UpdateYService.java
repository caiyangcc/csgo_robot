package com.softisland.scheduler;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.util.PageData;


@Service("updateYService")
public class UpdateYService {
	private Logger logger = Logger.getLogger(UpdateYService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
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
	

    
    
	public void work(){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		String current=f.format(c.getTime()); 
		c.add(Calendar.MINUTE, -11);
		String lastMIn=f.format(c.getTime()); 
		c.add(Calendar.MINUTE, -50);
		String lastHou=f.format(c.getTime());
		PageData p=new PageData();
		p.put("current", current);
		p.put("lastMIn", lastMIn);
		p.put("lastHou", lastHou);
		UpdateY uy=new UpdateY(p);
		downBox box=new downBox(p);
		executeService.execute(uy);
		executeService.execute(box);
	}
	
	/**
	 * 修改Y值
	 * @author Administrator
	 *
	 */
	 private class UpdateY implements Runnable{

		PageData pd;
		public UpdateY(PageData p){
			this.pd=p;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//查询Y值增长率
			/*try {
				logger.info("update Y begin");
				String y_rate=redisTemplate.opsForValue().get("box:y_rate");
				if(y_rate==null||y_rate.equals("")){
					return;
				}
				int rate=Integer.valueOf(y_rate);
				if(rate>0){
				//查询10分钟之前利润为负数的数据
				List<PageData> list=(List<PageData>) dao.findForList("BoxMapper.getMinusPrice", pd);
				if(list!=null&&list.size()>0){
					BigDecimal total=new BigDecimal(0);
					for(int i=0;i<list.size();i++){
						PageData data=list.get(i);
						BigDecimal purchase_price=(BigDecimal) data.get("purchase_price");
						total=total.add(purchase_price);
					}
					PageData o = (PageData) dao.findForObject("BoxMapper.queryLastUpdateYValue", new PageData());
					int new_Y = 0;
					if(o!=null){
						new_Y=Integer.valueOf(String.valueOf(o.get("new_value")));
					}
					//算最新的Y值
					double afterY=total.multiply(new BigDecimal(rate).divide(new BigDecimal(100))).doubleValue();
					afterY=Math.floor(afterY);
					afterY=afterY+new_Y;
					//插入数据库
					PageData board = new PageData();
					board.put("old_value", new_Y);
					board.put("created", new Timestamp(System.currentTimeMillis()));
					board.put("operator_id", "1");
					board.put("operator_name", "系统自增");
					board.put("new_value", afterY);
					board.put("purchase_rate", o.get("purchase_rate"));
					dao.save("BoxMapper.saveYValue", board);
				}
				else{
					return;
				}
				}
				else{
					return;
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			try {
			logger.info("update Y begin");
			//查询10分钟之前数据
			List<PageData> list=(List<PageData>) dao.findForList("BoxMapper.getBoxMinusPrice", pd);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					PageData data=list.get(i);
					BigDecimal total=(BigDecimal) data.get("purchase_price");
				
				PageData o = (PageData) dao.findForObject("BoxMapper.queryBoxLastUpdateYValue", data);
				int new_Y = 0;
				if(o!=null){
					new_Y=Integer.valueOf(String.valueOf(o.get("new_value")));
					int rate=(int) o.get("y_add_rate");
				if(rate>0){
				//算最新的Y值
				double afterY=total.multiply(new BigDecimal(rate).divide(new BigDecimal(100))).doubleValue();
				afterY=Math.floor(afterY);
				afterY=afterY+new_Y;
				//插入数据库
				PageData board = new PageData();
				board.put("old_value", new_Y);
				board.put("created", new Timestamp(System.currentTimeMillis()));
				board.put("operator_id", "1");
				board.put("operator_name", "系统自增");
				board.put("new_value", afterY);
				board.put("box_id", data.get("box_id"));
				board.put("purchase_rate", o.get("purchase_rate"));
				board.put("y_add_rate", o.get("y_add_rate"));
				dao.save("BoxMapper.saveBoxYValue", board);
				dao.update("BoxMapper.updateBoxYValue", board);
				}
				}
			}
			}
			else{
				return;
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}
		 
	 }
	 
	 
	 /**
	  * 箱子下架
	  * @author Administrator
	  *
	  */
	 private class downBox implements Runnable{

		 	PageData pd;
			public downBox(PageData p){
				this.pd=p;
			}
			
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//查询出box_item_id为空的箱子
			try {
				List<PageData> boxList =(List<PageData>) dao.findForList("BoxMapper.getNullBox", pd);
				logger.error("查询出box_item_id为空的箱子:"+boxList);
				if(boxList!=null&&boxList.size()>0){
					for(int i=0;i<boxList.size();i++){
						PageData p=boxList.get(i);
						//下架该箱子
						dao.update("BoxMapper.dowmBox", p);
					}	
				}
				else{
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
	 }
}