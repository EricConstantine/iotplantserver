package com.rederic.iotplant.applicationserver.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	public static final Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	public static Gson getGson(){
		return gson;
	}
	
}
