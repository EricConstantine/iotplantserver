package com.rederic.iotplant.applicationserver.dao;

import com.rederic.iotplant.applicationserver.entity.ModelSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported=false)
public interface SensorRepository extends JpaRepository<ModelSensor, String>,JpaSpecificationExecutor<ModelSensor> {
		//do somethings
    @Query(value="SELECT * FROM  model_sensor where pid =:pid",nativeQuery=true)//原生sql语句
    List<ModelSensor> getSensorsByPid(@Param("pid") String pid);
}