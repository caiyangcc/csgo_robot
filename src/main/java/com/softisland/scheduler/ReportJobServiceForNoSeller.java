package com.softisland.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;

import com.softisland.dao.DaoSupport;
import com.softisland.dao.DaoSupport2;
import com.softisland.entity.GameTypeEnum;
import com.softisland.util.PageData;


@Service("reportJobServiceForNoSeller")
public class ReportJobServiceForNoSeller {
	private Logger logger = Logger.getLogger(ReportJobServiceForNoSeller.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao2;



			public  void work()  {
				logger.info("--begin reportJobServiceForNoSeller form");
				PageData pd = new PageData();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				Calendar ca = Calendar.getInstance();
				String createTime=format.format(ca.getTime());
				String currentDay=format1.format(ca.getTime());
				ca.add(Calendar.MINUTE, -30);
				Date lastTime = ca.getTime();
				String endTime = format.format(lastTime);
				pd.put("day_anlyze_start", endTime);
				pd.put("day_anlyze_stop", createTime);
				pd.put("currentDay", currentDay);
                try {                	
					insertReportFormOne1(pd);
				} catch (Exception e) {
					e.printStackTrace();
				}	
	}

	
			
	public void insertReportFormOne1(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao2.findForList("RobotMapper.queryReportFormOneNoSeller", pd);
		if(list!=null&&list.size()>0){
			dao.delete("RobotMapper.deletereport23_noseller_form", pd);
			dao.save("RobotMapper.insertReportFormOneNoSeller", list);
		}
	}

	
}