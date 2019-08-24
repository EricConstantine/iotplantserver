package com.rederic.iotplant.applicationserver.service;

import com.rederic.iotplant.applicationserver.entity.ModelDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DeviceService {
	
	public ModelDevice findById(String deviceid);//根据主键获取对象
	
	public Page<ModelDevice> findAll(Pageable pageable,Object[] args);//获取分页信息
	
	public void deleteById(String deviceid);//获取分页信息
	
	public ModelDevice save(ModelDevice device);//保存数据
	
	public int saveFromList(List<Map<String,String>> list) throws Exception;//导入

	public Page<Map<String,Object>> getDevicePage (Pageable pageable,String keywords);
}