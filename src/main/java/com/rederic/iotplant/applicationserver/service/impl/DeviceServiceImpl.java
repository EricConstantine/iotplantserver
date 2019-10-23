package com.rederic.iotplant.applicationserver.service.impl;

import com.rederic.iotplant.applicationserver.common.util.GsonUtil;
import com.rederic.iotplant.applicationserver.dao.DeviceRepository;
import com.rederic.iotplant.applicationserver.entity.ModelDevice;
import com.rederic.iotplant.applicationserver.service.DeviceService;
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
public class DeviceServiceImpl implements DeviceService {
	
    @Autowired
    private DeviceRepository deviceRepository;

    @SuppressWarnings("serial")
	@Override
	public Page<ModelDevice> findAll(Pageable pageable, Object[] args) {
		Page<ModelDevice> result = deviceRepository.findAll(new Specification<ModelDevice>() {
	        @Override
	        public Predicate toPredicate(Root<ModelDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            List<Predicate> list = new ArrayList<Predicate>();

	            if (!StringUtils.isEmpty(args[0])) {
					list.add(cb.like(root.get("id").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("pid").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("name").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("describes").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("location").as(String.class), "%" + args[0] + "%"));
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
	public ModelDevice findById(String deviceid) {
		return deviceRepository.findById(deviceid).get();
	}

	@Override
	public void deleteById(String deviceid) {
		deviceRepository.deleteById(deviceid);;
	}

	@Override
	public ModelDevice save(ModelDevice device) {
		return deviceRepository.save(device);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveFromList(List<Map<String,String>> list) throws Exception {
		int i=0;
		List<ModelDevice> devicelist = new ArrayList<ModelDevice>();
		for(Map<String,String> map:list){
			ModelDevice device = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(map),ModelDevice.class);
			devicelist.add(device);
		}
		for(ModelDevice device:devicelist){
			deviceRepository.save(device);
			i++;
		}
		return i;
	}

	@Override
	public List<Map<String,Object>> getDeviceByPid(String pid) {
		return deviceRepository.getDeviceByPid(pid);
	}

}
