package com.rederic.iotplant.applicationserver.service;

import com.rederic.iotplant.applicationserver.entity.IotSensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SensorService {
    public IotSensor findById(String gatewayid);//根据主键获取对象

    public Page<IotSensor> findAll(Pageable pageable, Object[] args);//获取分页信息

    public void deleteById(String gatewayid);//删除信息

    public IotSensor save(IotSensor gateway);//保存数据

    public List<IotSensor> getSensorsByPid(String pid);
}
