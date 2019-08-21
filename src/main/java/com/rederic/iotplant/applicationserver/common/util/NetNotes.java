package com.rederic.iotplant.applicationserver.common.util;

public enum NetNotes {
	//日志level
	info(1),
	warn(2),
	error(3),
	
	//status 启用，禁用
	open(1),
	close(0),
	
	//webservice 状态
	success(0),
	fail(-1),
	
	
	
	//定时任务类型 
	everyday(1),
	oneday(2),
	
	//usertype
	root(1),
	admin(2),
	ordinary(3),
	guest(4);

	
	private int value;

	private NetNotes(int value) {
		this.value = value;
	}

	public int toInteger() {
		return value;
	}

	public String toString() {
		return value + "";
	}

}
