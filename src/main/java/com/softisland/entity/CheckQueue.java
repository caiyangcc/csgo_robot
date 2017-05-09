package com.softisland.entity;

public class CheckQueue {

	private String pt_id;
	
	private String bot_id;
	
	private String p_id;
	
	private String lock_time;
	
	private String remark;
	
	private String app_id;
	
	private String status;
	
	public CheckQueue(String pt_id, String bot_id, String p_id, String lock_time) {
		super();
		this.pt_id = pt_id;
		this.bot_id = bot_id;
		this.p_id = p_id;
		this.lock_time = lock_time;
	}

	public String getPt_id() {
		return pt_id;
	}

	public void setPt_id(String pt_id) {
		this.pt_id = pt_id;
	}

	public String getBot_id() {
		return bot_id;
	}

	public void setBot_id(String bot_id) {
		this.bot_id = bot_id;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getLock_time() {
		return lock_time;
	}

	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
