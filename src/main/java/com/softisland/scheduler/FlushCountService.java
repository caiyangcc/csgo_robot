package com.softisland.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.dao.DaoSupport2;
import com.softisland.util.PageData;



@Service("flushCountService")
public class FlushCountService {
	private Logger logger = Logger.getLogger(FlushCountService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao2;
	
	 @Autowired  
	 RedisTemplate<String,String> redisTemplate;
	
	public void work(){
		try {
			//查询商品销售订单数量
			int count=(int) dao2.findForObject("RollMapper.getSaleCount", null);
			String newcount=String.valueOf(count);
			redisTemplate.opsForValue().set("order:order_count", newcount);
			//查询商品入库数量
			int countIn=(int) dao2.findForObject("RollMapper.getStockInCount", null);
			String newcountIn=String.valueOf(countIn);
			redisTemplate.opsForValue().set("stock:stock_in", newcountIn);
			//查询商品出库数量
			int countOut=(int) dao2.findForObject("RollMapper.getStockOutCount", null);
			String newcountOut=String.valueOf(countOut);
			redisTemplate.opsForValue().set("stock:stock_out", newcountOut);
			//查询钱包明细数量
			int walletCount=(int) dao2.findForObject("RollMapper.getWalletCount", null);
			String newWalletCount=String.valueOf(walletCount);
			redisTemplate.opsForValue().set("wallet:walletCount", newWalletCount);
			//查询product_trade的数量
			int tradeCount=(int) dao2.findForObject("RollMapper.getProductTrade", null);
			String newtradeCount=String.valueOf(tradeCount);
			redisTemplate.opsForValue().set("product_trade:trade_total", newtradeCount);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
