package com.rederic.iotplant.applicationserver.service.impl;

import com.rederic.iotplant.applicationserver.common.util.GsonUtil;
import com.rederic.iotplant.applicationserver.dao.DictionaryRepository;
import com.rederic.iotplant.applicationserver.entity.ModelDictionary;
import com.rederic.iotplant.applicationserver.service.DictionaryService;
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
public class DictionaryServiceImpl implements DictionaryService {
	
    @Autowired
    private DictionaryRepository dictionaryRepository;

    @SuppressWarnings("serial")
	@Override
	public Page<ModelDictionary> findAll(Pageable pageable,Object[] args) {
		Page<ModelDictionary> result = dictionaryRepository.findAll(new Specification<ModelDictionary>() {
	        @Override
	        public Predicate toPredicate(Root<ModelDictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            List<Predicate> list = new ArrayList<Predicate>();

	            if (!StringUtils.isEmpty(args[0])) {
					list.add(cb.like(root.get("id").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("pid").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("dname").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("dvalue").as(String.class), "%" + args[0] + "%"));
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
	public ModelDictionary findById(String dictionaryid) {
		return dictionaryRepository.findById(dictionaryid).get();
	}

	@Override
	public void deleteById(String dictionaryid) {
		dictionaryRepository.deleteById(dictionaryid);;
	}

	@Override
	public ModelDictionary save(ModelDictionary dictionary) {
		return dictionaryRepository.save(dictionary);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveFromList(List<Map<String,String>> list) throws Exception {
		int i=0;
		List<ModelDictionary> dictionarylist = new ArrayList<ModelDictionary>();
		for(Map<String,String> map:list){
			ModelDictionary dictionary = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(map),ModelDictionary.class);
			dictionarylist.add(dictionary);
		}
		for(ModelDictionary dictionary:dictionarylist){
			dictionaryRepository.save(dictionary);
			i++;
		}
		return i;
	}

    
}
