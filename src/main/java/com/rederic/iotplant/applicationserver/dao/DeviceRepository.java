package com.rederic.iotplant.applicationserver.dao;

import com.rederic.iotplant.applicationserver.entity.ModelDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Map;

@RepositoryRestResource(exported=false)
public interface DeviceRepository extends JpaRepository<ModelDevice, String>,JpaSpecificationExecutor<ModelDevice> {
		//do somethings
        @Query(value="SELECT * FROM (SELECT a.id,a.sn,a.name,a.describes,a.treaty,a.location,a.userid,DATE_FORMAT(a.createtime,'%Y-%m-%d %H:%i:%S') createtime,GROUP_CONCAT(b.name) sensorstr FROM model_device a\n" +
                "LEFT JOIN model_sensor b ON a.id = b.pid " +
                "WHERE a.name LIKE %:keywords% or a.location LIKE %:keywords% or a.sn LIKE %:keywords% GROUP BY a.id) a"
                ,countQuery="SELECT count(1) FROM (SELECT a.*,GROUP_CONCAT(b.name) sensorstr FROM model_device a\n" +
                "LEFT JOIN model_sensor b ON a.id = b.pid " +
                "WHERE a.name LIKE %:keywords% or a.location LIKE %:keywords% or a.sn LIKE %:keywords% GROUP BY a.id) a"
                ,nativeQuery=true)//原生sql语句SELECT 获取root组所有分组
        Page<Map<String , Object>> getDevicePage(Pageable pageable, @Param("keywords") String keywords);
}