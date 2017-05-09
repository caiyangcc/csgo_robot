package com.softisland.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.util.PageData;



@Service("cancleRoomService")
public class cancleRoom {
	private Logger logger = Logger.getLogger(cancleRoom.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void work(){
		
		//查询进行中的房间
		try {
			List<PageData> roomList=(List<PageData>) dao.findForList("RollMapper.getRoomListForTimer", null);
			if(roomList!=null&&roomList.size()>0){
				for(int j=0;j<roomList.size();j++){
					PageData pd=roomList.get(j);
					int code=(int) dao.update("RollMapper.cancleRoom", pd);
					if(code>0){
						//取消兑换券
						 //1.查询该房间的所有产品
						List<PageData> productList=(List) dao.findForList("RollMapper.getProduct", pd);
						if(productList!=null&&productList.size()>0){
						
							for(int i=0;i<productList.size();i++){
								PageData data=productList.get(i);
						 //2.更改为已作废
								int id=(int) data.get("id");
								int product_trade_id=(int) data.get("product_trade_id");
								int num=(int) dao.update("RollMapper.updateProductStatus", id);
								if(num>0){
						//3.取消兑换券
									dao.update("RollMapper.updateCoupon", product_trade_id);
						//4.更改7变1
									dao.update("RollMapper.updateProductTrade", product_trade_id);
								}
							}
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
