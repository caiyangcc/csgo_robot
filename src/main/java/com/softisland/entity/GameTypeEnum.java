/**
 * 
 */
package com.softisland.entity;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Administrator
 *
 */
public enum GameTypeEnum {
	CSGO(730,"csgo"),DOTA2(570,"dota2"),KOTK(433850,"h1z1:KotK"),JS(295110,"h1z1:js"),RUN(578080,"绝地大逃杀"),TF(440,"TF2");
	
	private int code;
	private String name;
	
	private GameTypeEnum(int code, String name) {
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
	
	public static List<GameTypeEnum> getGameTypeList(){
		List<GameTypeEnum> GameTypeList = new ArrayList<GameTypeEnum>();
		GameTypeList.add(GameTypeEnum.CSGO);
		GameTypeList.add(GameTypeEnum.DOTA2);
		GameTypeList.add(GameTypeEnum.KOTK);
		GameTypeList.add(GameTypeEnum.JS);
		GameTypeList.add(GameTypeEnum.RUN);
		GameTypeList.add(GameTypeEnum.TF);
		return GameTypeList;
	}
	
	public static List<GameTypeEnum> getGameTypeListForH1z1(){
		List<GameTypeEnum> GameTypeList = new ArrayList<GameTypeEnum>();
		GameTypeList.add(GameTypeEnum.KOTK);
		GameTypeList.add(GameTypeEnum.JS);
		GameTypeList.add(GameTypeEnum.RUN);
		GameTypeList.add(GameTypeEnum.TF);
		return GameTypeList;
	}
	
	

	
	public static GameTypeEnum getGameType(int code){
		GameTypeEnum gameType = GameTypeEnum.CSGO;	
		if(code == GameTypeEnum.CSGO.code){
			gameType = GameTypeEnum.CSGO;
		}else if(code == GameTypeEnum.DOTA2.code){
			gameType = GameTypeEnum.DOTA2;
		}else if(code == GameTypeEnum.KOTK.code){
			gameType=GameTypeEnum.KOTK;
		}else if(code == GameTypeEnum.JS.code){
			gameType=GameTypeEnum.JS;
		}else if(code == GameTypeEnum.RUN.code){
			gameType=GameTypeEnum.RUN;
		}else if(code == GameTypeEnum.TF.code){
			gameType=GameTypeEnum.TF;
		}
		return gameType;
	}
}
