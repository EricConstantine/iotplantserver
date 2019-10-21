package com.rederic.iotplant.applicationserver.service;

import com.rederic.iotplant.applicationserver.entity.ModelProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ProductService {
	
	public ModelProduct findById(String productid);//根据主键获取对象
	
	public Page<ModelProduct> findAll(Pageable pageable,Object[] args);//获取分页信息
	
	public void deleteById(String productid);//获取分页信息
	
	public ModelProduct save(ModelProduct product);//保存数据
	
	public int saveFromList(List<Map<String,String>> list) throws Exception;//导入

	Page<Map<String , Object>> getProductByPage(Pageable pageable, @Param("keywords") String keywords);
}