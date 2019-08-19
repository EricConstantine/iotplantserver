package com.rederic.iotplant.applicationserver.service;

import com.rederic.iotplant.applicationserver.entity.IotDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DeviceService {
    public IotDevice findById(String gatewayid);//根据主键获取对象

    public Page<IotDevice> findAll(Pageable pageable, Object[] args);//获取分页信息

    public void deleteById(String gatewayid);//删除信息

    public IotDevice save(IotDevice gateway);//保存数据
}
