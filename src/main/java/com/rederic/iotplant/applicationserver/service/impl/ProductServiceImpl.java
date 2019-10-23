package com.rederic.iotplant.applicationserver.service.impl;

import com.rederic.iotplant.applicationserver.common.util.GsonUtil;
import com.rederic.iotplant.applicationserver.dao.ProductRepository;
import com.rederic.iotplant.applicationserver.entity.ModelProduct;
import com.rederic.iotplant.applicationserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@EnableTransactionManagement
public class ProductServiceImpl implements ProductService {
	
    @Autowired
    private ProductRepository productRepository;

    @SuppressWarnings("serial")
	@Override
	public Page<ModelProduct> findAll(Pageable pageable,Object[] args) {
		Page<ModelProduct> result = productRepository.findAll(new Specification<ModelProduct>() {
	        @Override
	        public Predicate toPredicate(Root<ModelProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            List<Predicate> list = new ArrayList<Predicate>();

	            if (!StringUtils.isEmpty(args[0])) {
					list.add(cb.like(root.get("id").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("name").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("describes").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("treaty").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("creator").as(String.class), "%" + args[0] + "%"));
	                Predicate[] p = new Predicate[list.size()];
		            return cb.or(list.toArray(p));
	            }else{
	            	return null;
	            }
	        }
	    }, pageable);
	    return result;
	}
    
	@Override
	public ModelProduct findById(String productid) {
		return productRepository.findById(productid).get();
	}

	@Override
	public void deleteById(String productid) {
		productRepository.deleteById(productid);;
	}

	@Override
	public ModelProduct save(ModelProduct product) {
		return productRepository.save(product);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveFromList(List<Map<String,String>> list) throws Exception {
		int i=0;
		List<ModelProduct> productlist = new ArrayList<ModelProduct>();
		for(Map<String,String> map:list){
			ModelProduct product = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(map),ModelProduct.class);
			productlist.add(product);
		}
		for(ModelProduct product:productlist){
			productRepository.save(product);
			i++;
		}
		return i;
	}

	@Override
	public Page<Map<String, Object>> getProductByPage(Pageable pageable, String keywords) {
		return productRepository.getProductByPage(pageable,keywords);
	}

	@Override
	public List<ModelProduct> getAllProduct() {
		return productRepository.findAll();
	}
}
