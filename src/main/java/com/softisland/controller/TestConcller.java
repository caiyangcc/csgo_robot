package com.softisland.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.redis.core.RedisTemplate;

import com.softisland.scheduler.FlushCountService;
import com.softisland.service.ScheduleService;

@Controller
public class TestConcller {
	private Logger logger = Logger.getLogger(TestConcller.class);
	@Autowired
	private ScheduleService userService;
	@Autowired  
	RedisTemplate<String,String> redisTemplate;
	
	@RequestMapping("/addPayMent")
	public void addPayMent(){
		try {
			userService.addPayMent();
			System.out.println("addPayMent end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	@RequestMapping("/addCoupon")
	public void addCoupon(){
		try {
			 userService.addCoupon();
			 System.out.println("addCoupon end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@RequestMapping("/defaultPage")
	public ModelAndView defaultPage(){
		ModelAndView mv=new ModelAndView();
		System.out.println("11111111111");
		System.out.println("11111111111");
		System.out.println("11111111111");
        List<Map<String, Object>> list;
		/*try {
			list = userService.getAllUser();
			 for(Map p:list){
			        int id=(int) p.get("id");
			        System.out.println(id);
			        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
       
		mv.setViewName("admin/default");
		return mv;
		
	}
	
	
	@RequestMapping("/webSet")
	public ModelAndView webSet(){
		ModelAndView mv=new ModelAndView();
		System.out.println("11111111111");
		System.out.println("11111111111");
		System.out.println("11111111111");
        List<Map<String, Object>> list;
		/*try {
			list = userService.getAllUser();
			 for(Map p:list){
			        int id=(int) p.get("id");
			        System.out.println(id);
			        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
       
		mv.setViewName("view/webSet");
		return mv;
		
	}
	
	
	@RequestMapping("/message")
	public void message(){
		Set<String> set=redisTemplate.keys("*");
		for (String str : set) {  
			logger.info(str+"--"+redisTemplate.opsForValue().get(str));  
		} 	
	}
	
	@RequestMapping("/save")
	public void setKey(){
		List<String> list=new ArrayList<String>();
		list.add("box:y_rate--1");
		list.add("sellerreportformseven:seller_count--2637714");
		list.add("eb1a880b463c4ee4a0c38e8c70f68f62_mac--9C-5C-8E-70-2C-2F");
		  list.add("stock:stock_out--5779623");
		list.add("6296380039ca47818a6af46f0f200a7c_mac--30-5A-3A-0B-BD-8A");
		list.add("9a0019b54a3541259f96f88a3f4fbbf9_mac--50-E5-49-9A-B8-56");
		list.add("e6137486457d487c9c5441d649b6fd93_mac--2C-56-DC-99-3B-E5");
		list.add("9b3803417f47461e94f97a0d82661a89_mac--38-2C-4A-BB-DD-E5");
		list.add("e1616994ce534aa5863b20829a6bda71_mac--08-62-66-2F-D9-05");
		 list.add("09897758ccd745db86a1e77f8a8e57d6_mac--2C-56-DC-99-3B-E2");
		list.add("7535f4cb5f71403d98cf1e2f64a30e81_mac--54-A0-50-E6-C0-27");
		 list.add("wallet:walletCount--13302140");
		list.add("stock:stock_in--7196332");
		list.add("order:order_count--4299128");
		list.add("5964f8a05a514e4194e5e23f7cfdae2c_mac--34-97-F6-24-4D-1B");
		list.add("d9ea792344124eaf997198968e025f5d_mac--38-2C-4A-BB-DE-50");
		list.add("b63337ecd1294a77afeb5deddc26f3a2_mac--9C-5C-8E-70-35-26");
		list.add("b81223c38ea84cd2b4d3290786a220ef_mac--9C-5C-8E-70-35-26");
		list.add("a8435c30fe804ea897a6add16b525e24_mac--2C-56-DC-99-3B-E2");
		list.add("7b44d34328734aa9b5a367617d9a770a_mac--2C-56-DC-99-3B-E5");
		list.add("44a1a25624f24d068a6bc05b5cc55025_mac--30-5A-3A-0B-BD-8A");
		list.add("02d41cbb16e64f3abe016feabec72709_mac--50-E5-49-9A-B9-07");
		 list.add("4abe5c6cb3104d9eba0e1cd53be83921_mac--9C-5C-8E-70-35-26");
		list.add("e5183bc640404ddbb3096fa6ca43d28d_mac--2C-56-DC-99-3B-E2");
		list.add("8515d62e4a244d258d134269af0aab3a_mac--38-2C-4A-BB-DE-50");
		
		for(int i=0;i<list.size();i++){
			String str=list.get(i);
			String[] newstr=str.split("--");
			redisTemplate.opsForValue().set(newstr[0], newstr[1]);
		}
		
		
		
	} 
	
}
