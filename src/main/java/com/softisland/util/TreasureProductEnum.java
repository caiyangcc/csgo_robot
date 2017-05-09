/**
 * 
 */
package com.softisland.util;

/**
 * @author Administrator
 *
 */
public enum TreasureProductEnum {

	Day_90(90,90*24*3600*1000l,"90天");
	
	private Integer status;
	private Long time;
	private String memo;
	
	private TreasureProductEnum(Integer status, Long time, String memo) {
		this.status = status;
		this.time = time;
		this.memo = memo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
}
