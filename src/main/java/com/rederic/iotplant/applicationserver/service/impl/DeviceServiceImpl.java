package com.rederic.iotplant.applicationserver.service.impl;

import com.rederic.iotplant.applicationserver.dao.DeviceRepository;
import com.rederic.iotplant.applicationserver.entity.IotDevice;
import com.rederic.iotplant.applicationserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableTransactionManagement
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Override
    public IotDevice findById(String gatewayid) {
        return  deviceRepository.findById(gatewayid).get();
    }
    @SuppressWarnings("serial")
    @Override
    public Page<IotDevice> findAll(Pageable pageable, Object[] args) {
        Page<IotDevice> result = deviceRepository.findAll(new Specification<IotDevice>() {
            @Override
            public Predicate toPredicate(Root<IotDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(args[0])) {
                    list.add(cb.like(root.get("id").as(String.class), "%" + args[0] + "%"));
                    list.add(cb.like(root.get("name").as(String.class), "%" + args[0] + "%"));
                    list.add(cb.like(root.get("describe").as(String.class), "%" + args[0] + "%"));
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
    public void deleteById(String gatewayid) {
        deviceRepository.deleteById(gatewayid);
    }

    @Override
    public IotDevice save(IotDevice gateway) {
        return deviceRepository.save(gateway);
    }

}
