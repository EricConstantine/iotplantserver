package com.rederic.iotplant.applicationserver.service.impl;

import com.rederic.iotplant.applicationserver.common.util.GsonUtil;
import com.rederic.iotplant.applicationserver.dao.SensorRepository;
import com.rederic.iotplant.applicationserver.entity.ModelSensor;
import com.rederic.iotplant.applicationserver.service.SensorService;
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
public class SensorServiceImpl implements SensorService {
	
    @Autowired
    private SensorRepository sensorRepository;

    @SuppressWarnings("serial")
	@Override
	public Page<ModelSensor> findAll(Pageable pageable, Object[] args) {
		Page<ModelSensor> result = sensorRepository.findAll(new Specification<ModelSensor>() {
	        @Override
	        public Predicate toPredicate(Root<ModelSensor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            List<Predicate> list = new ArrayList<Predicate>();

	            if (!StringUtils.isEmpty(args[0])) {
					list.add(cb.like(root.get("id").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("pid").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("name").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("skey").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("stype").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("svalue").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("sunit").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("img").as(String.class), "%" + args[0] + "%"));
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
	public ModelSensor findById(String sensorid) {
		return sensorRepository.findById(sensorid).get();
	}

	@Override
	public void deleteById(String sensorid) {
		sensorRepository.deleteById(sensorid);;
	}

	@Override
	public ModelSensor save(ModelSensor sensor) {
		return sensorRepository.save(sensor);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveFromList(List<Map<String,String>> list) throws Exception {
		int i=0;
		List<ModelSensor> sensorlist = new ArrayList<ModelSensor>();
		for(Map<String,String> map:list){
			ModelSensor sensor = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(map),ModelSensor.class);
			sensorlist.add(sensor);
		}
		for(ModelSensor sensor:sensorlist){
			sensorRepository.save(sensor);
			i++;
		}
		return i;
	}

	@Override
	public List<ModelSensor> getSensorsByPid(String pid) {
		return sensorRepository.getSensorsByPid(pid);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteSensorByPid(String pid) {
		sensorRepository.deleteSensorByPid(pid);
	}
}
