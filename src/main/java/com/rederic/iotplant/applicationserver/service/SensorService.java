package com.rederic.iotplant.applicationserver.service;

import com.rederic.iotplant.applicationserver.entity.ModelSensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SensorService {
	
	public ModelSensor findById(String sensorid);//根据主键获取对象
	
	public Page<ModelSensor> findAll(Pageable pageable,Object[] args);//获取分页信息
	
	public void deleteById(String sensorid);//获取分页信息
	
	public ModelSensor save(ModelSensor sensor);//保存数据
	
	public int saveFromList(List<Map<String,String>> list) throws Exception;//导入

	public List<ModelSensor> getSensorsByPid(String pid);

}