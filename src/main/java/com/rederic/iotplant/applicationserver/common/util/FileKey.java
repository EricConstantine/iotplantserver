package com.rederic.iotplant.applicationserver.common.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Base64;
@Component
public class FileKey {
	
	private static String servicekeypath;
	
	public static String readLocalKey() {
		File f = new File(servicekeypath);
		String result = "";
		if(f.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line ="";
				while ((line = br.readLine()) != null) {
	                // 一次读入一行数据
		            result = new String (Base64.getDecoder().decode(line));//解密
	            }
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	public static void writeLocalKey(String key) {
		File file = new File(servicekeypath);
		File dir = file.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			 //创建字符流（使用字节流比较麻烦）
            FileWriter fw = new FileWriter(file);
            fw.write(Base64.getEncoder().encodeToString(key.getBytes()));//加密
            //这里要说明一下，write方法是写入缓存区，并没有写进file文件里面，要使用flush方法才写进去
            fw.flush();
            //关闭资源
            fw.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String getServicekeypath() {
		return servicekeypath;
	}
	
//	@Value("${service.key.path}")
	public void setServicekeypath(String servicekeypath) {
		FileKey.servicekeypath = servicekeypath;
	}
	

}
