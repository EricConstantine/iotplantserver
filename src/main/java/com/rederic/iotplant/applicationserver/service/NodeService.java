package com.rederic.iotplant.applicationserver.service;

import com.rederic.iotplant.applicationserver.entity.ModelNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface NodeService {
	
	public ModelNode findById(String nodeid);//根据主键获取对象

	public List<ModelNode> findByPId(String pid);//根据主键获取对象
	
	public Page<ModelNode> findAll(Pageable pageable,Object[] args);//获取分页信息
	
	public void deleteById(String nodeid);//获取分页信息
	
	public ModelNode save(ModelNode node);//保存数据
	
	public int saveFromList(List<Map<String,String>> list) throws Exception;//导入
	
}