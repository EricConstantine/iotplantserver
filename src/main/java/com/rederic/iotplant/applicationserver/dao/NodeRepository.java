package com.rederic.iotplant.applicationserver.dao;

import com.rederic.iotplant.applicationserver.entity.ModelNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface NodeRepository extends JpaRepository<ModelNode, String>,JpaSpecificationExecutor<ModelNode> {
		//do somethings

}