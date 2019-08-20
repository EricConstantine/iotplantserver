package com.rederic.iotplant.applicationserver.dao;

import com.rederic.iotplant.applicationserver.entity.IotSensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;

@RepositoryRestResource(exported=false)
public interface SensorRepository extends JpaRepository<IotSensor, String>, JpaSpecificationExecutor<IotSensor> {
    @Query(value="SELECT * FROM  iot_device"
            ,countQuery="SELECT count(1)  FROM iot_device"
            ,nativeQuery=true)//原生sql语句SELECT 获取非root组分页
    Page<Map<String , Object>> getAllByPage(Pageable pageable, @Param("keywords") String keywords, @Param("eid") String eid);

    @Query(value="SELECT * FROM  iot_sensor where pid =:pid",nativeQuery=true)//原生sql语句
    List<IotSensor> getSensorsByPid(@Param("pid") String pid);

}
