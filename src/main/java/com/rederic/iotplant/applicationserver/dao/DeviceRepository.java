package com.rederic.iotplant.applicationserver.dao;

import com.rederic.iotplant.applicationserver.entity.ModelDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;

@RepositoryRestResource(exported=false)
public interface DeviceRepository extends JpaRepository<ModelDevice, String>,JpaSpecificationExecutor<ModelDevice> {
		//do somethings
//do somethings
        @Query(value="select a.id,a.pid,a.name,a.describes,a.location,DATE_FORMAT(a.createtime,'%Y-%m-%d %H:%i:%S') createtime," +
                "DATE_FORMAT(a.updatetime,'%Y-%m-%d %H:%i:%S') updatetime,a.id title,'text' buttontype,'Dev' type,'#bbbec4' color from model_device a where a.pid = ?",nativeQuery=true)
        List<Map<String,Object>> getDeviceByPid(String pid);
}