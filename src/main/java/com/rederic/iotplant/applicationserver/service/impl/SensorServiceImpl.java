package com.rederic.iotplant.applicationserver.service.impl;

import com.rederic.iotplant.applicationserver.dao.SensorRepository;
import com.rederic.iotplant.applicationserver.entity.IotSensor;
import com.rederic.iotplant.applicationserver.service.SensorService;
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
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public IotSensor findById(String gatewayid) {
        return  sensorRepository.findById(gatewayid).get();
    }
    @SuppressWarnings("serial")
    @Override
    public Page<IotSensor> findAll(Pageable pageable, Object[] args) {
        Page<IotSensor> result = sensorRepository.findAll(new Specification<IotSensor>() {
            @Override
            public Predicate toPredicate(Root<IotSensor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
        sensorRepository.deleteById(gatewayid);
    }

    @Override
    public IotSensor save(IotSensor gateway) {
        return sensorRepository.save(gateway);
    }

    @Override
    public List<IotSensor> getSensorsByPid(String pid) {
        return sensorRepository.getSensorsByPid(pid);
    }

}
