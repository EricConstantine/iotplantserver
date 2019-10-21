package com.rederic.iotplant.applicationserver.service;

import com.rederic.iotplant.applicationserver.entity.ModelDictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DictionaryService {
	
	public ModelDictionary findById(String dictionaryid);//根据主键获取对象
	
	public Page<ModelDictionary> findAll(Pageable pageable,Object[] args);//获取分页信息
	
	public void deleteById(String dictionaryid);//获取分页信息
	
	public ModelDictionary save(ModelDictionary dictionary);//保存数据
	
	public int saveFromList(List<Map<String,String>> list) throws Exception;//导入
	
}