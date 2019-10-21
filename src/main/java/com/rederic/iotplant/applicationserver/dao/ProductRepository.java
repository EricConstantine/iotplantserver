package com.rederic.iotplant.applicationserver.dao;

import com.rederic.iotplant.applicationserver.entity.ModelProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Map;

@RepositoryRestResource(exported=false)
public interface ProductRepository extends JpaRepository<ModelProduct, String>,JpaSpecificationExecutor<ModelProduct> {
		//do somethings
        @Query(value="SELECT * from (SELECT a.*,GROUP_CONCAT(b.name) nodestr FROM model_product a\n" +
                "LEFT JOIN model_node b ON a.id=b.pid \n " +
                "WHERE a.name LIKE %:keywords% or a.id LIKE %:keywords% or a.describes LIKE %:keywords% " +
                "or b.name LIKE %:keywords% GROUP BY a.id) a"
                ,countQuery="SELECT count(1) FROM (SELECT a.*,GROUP_CONCAT(b.name) nodestr FROM model_product a\n" +
                "LEFT JOIN model_node b ON a.id = b.pid " +
                "WHERE a.name LIKE %:keywords% or a.id LIKE %:keywords% or a.describes LIKE %:keywords% " +
                "or b.name LIKE %:keywords% GROUP BY a.id) a"
                ,nativeQuery=true)//
        Page<Map<String , Object>> getProductByPage(Pageable pageable, @Param("keywords") String keywords);
}