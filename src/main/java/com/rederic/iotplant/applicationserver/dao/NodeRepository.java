package com.rederic.iotplant.applicationserver.dao;

import com.rederic.iotplant.applicationserver.entity.ModelNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;

@RepositoryRestResource(exported=false)
public interface NodeRepository extends JpaRepository<ModelNode, String>,JpaSpecificationExecutor<ModelNode> {
		//do somethings
        @Query(value="select a.*,'' svalue from model_node a where a.pid = ?",nativeQuery=true)
        List<Map<String,Object>> findByPid(String pid);
}