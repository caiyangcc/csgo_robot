package com.softisland.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * RobotTypeEnum 机器人类型
 * 
 * @author xiezong xiezhongjiang@softisland.com 2016年1月5日 下午3:17:14
 * 
 * @version 1.0.0
 *
 */
public enum RobotTypeEnum {

	NORMAL(1, "普通用户"),PUBLIC(2, "公用机器人"), VIP(3, "VIP用户"),SELFUSER(4, "自营账号"), HONER(5, "奖品机器人"),BIGERSELLER(6, "大卖家");
	private int code;
	private String name;

	private RobotTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<RobotTypeEnum> getRobotTypeEnumList() {
		List<RobotTypeEnum> robotTypeEnums = new ArrayList<RobotTypeEnum>();
		robotTypeEnums.add(RobotTypeEnum.PUBLIC);
		robotTypeEnums.add(RobotTypeEnum.VIP);
		robotTypeEnums.add(RobotTypeEnum.HONER);
		return robotTypeEnums;
	}
	
	public static List<RobotTypeEnum> getRobotAccountTypeEnumList(){
		List<RobotTypeEnum> robotTypeEnums = new ArrayList<RobotTypeEnum>();
		robotTypeEnums.add(RobotTypeEnum.PUBLIC);
		robotTypeEnums.add(RobotTypeEnum.VIP);
		robotTypeEnums.add(RobotTypeEnum.SELFUSER);
		robotTypeEnums.add(RobotTypeEnum.HONER);
		return robotTypeEnums;
		
	}
	public static List<RobotTypeEnum> getAllTypeEnumList() {
		List<RobotTypeEnum> robotTypeEnums = new ArrayList<RobotTypeEnum>();
		robotTypeEnums.add(RobotTypeEnum.NORMAL);
		robotTypeEnums.add(RobotTypeEnum.PUBLIC);
		robotTypeEnums.add(RobotTypeEnum.VIP);
		robotTypeEnums.add(RobotTypeEnum.SELFUSER);
		robotTypeEnums.add(RobotTypeEnum.HONER);
		return robotTypeEnums;
	}
	
	public static List<RobotTypeEnum> getSaleTypeList() {
		List<RobotTypeEnum> robotTypeEnums = new ArrayList<RobotTypeEnum>();
		robotTypeEnums.add(RobotTypeEnum.NORMAL);
		robotTypeEnums.add(RobotTypeEnum.SELFUSER);
		robotTypeEnums.add(RobotTypeEnum.BIGERSELLER);
		robotTypeEnums.add(RobotTypeEnum.VIP);
		return robotTypeEnums;
	}
	
	//只有自营和普通的枚举
	public static List<RobotTypeEnum> getTwoSaleTypeList() {
		List<RobotTypeEnum> robotTypeEnums = new ArrayList<RobotTypeEnum>();
		robotTypeEnums.add(RobotTypeEnum.NORMAL);
		robotTypeEnums.add(RobotTypeEnum.SELFUSER);;
		return robotTypeEnums;
	}
	
	public static RobotTypeEnum getTypeByCode(int code) {
		RobotTypeEnum robotTypeEnum = RobotTypeEnum.NORMAL;
		if (code==1) {
			robotTypeEnum = RobotTypeEnum.NORMAL;
		}else if(code==2){
			robotTypeEnum = RobotTypeEnum.PUBLIC;
		}else if(code==3){
			robotTypeEnum = RobotTypeEnum.VIP;
		}else if(code==4){
			robotTypeEnum = RobotTypeEnum.SELFUSER;
		}else if(code==5){
			robotTypeEnum = RobotTypeEnum.HONER;
		}
		return robotTypeEnum;
	}
}
