package com.softisland.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * OneProductTypeEnum 一元购实物虚物
 * 
 * @author xiezong xiezhongjiang@softisland.com 2016年1月5日 下午3:17:14
 * 
 * @version 1.0.0
 *
 */
public enum OneProductTypeEnum {

	VERTUAL(1, "虚物"),REAL(2, "实物");
	private int code;
	private String name;

	private OneProductTypeEnum(int code, String name) {
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


	
	//只有自营和普通的枚举
	public static List<OneProductTypeEnum> getOneProductTypeList() {
		List<OneProductTypeEnum> oneTypeEnums = new ArrayList<OneProductTypeEnum>();
		oneTypeEnums.add(OneProductTypeEnum.VERTUAL);
		oneTypeEnums.add(OneProductTypeEnum.REAL);;
		return oneTypeEnums;
	}
	
	
}
