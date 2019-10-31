package com.rederic.iotplant.applicationserver.service.impl;

import com.rederic.iotplant.applicationserver.common.util.GsonUtil;
import com.rederic.iotplant.applicationserver.dao.NodeRepository;
import com.rederic.iotplant.applicationserver.entity.ModelNode;
import com.rederic.iotplant.applicationserver.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@EnableTransactionManagement
public class NodeServiceImpl implements NodeService {
	
    @Autowired
    private NodeRepository nodeRepository;

    @SuppressWarnings("serial")
	@Override
	public Page<ModelNode> findAll(Pageable pageable,Object[] args) {
		Page<ModelNode> result = nodeRepository.findAll(new Specification<ModelNode>() {
	        @Override
	        public Predicate toPredicate(Root<ModelNode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            List<Predicate> list = new ArrayList<Predicate>();

	            if (!StringUtils.isEmpty(args[0])) {
					list.add(cb.like(root.get("id").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("pid").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("name").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("skey").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("rwtype").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("stype").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("detail").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("sunit").as(String.class), "%" + args[0] + "%"));
					list.add(cb.like(root.get("describes").as(String.class), "%" + args[0] + "%"));
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
	public ModelNode findById(String nodeid) {
		return nodeRepository.findById(nodeid).get();
	}

	@Override
	public void deleteById(String nodeid) {
		nodeRepository.deleteById(nodeid);;
	}

	@Override
	public ModelNode save(ModelNode node) {
		return nodeRepository.save(node);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveFromList(List<Map<String,String>> list) throws Exception {
		int i=0;
		List<ModelNode> nodelist = new ArrayList<ModelNode>();
		for(Map<String,String> map:list){
			ModelNode node = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(map),ModelNode.class);
			nodelist.add(node);
		}
		for(ModelNode node:nodelist){
			nodeRepository.save(node);
			i++;
		}
		return i;
	}

	@Override
	public List<Map<String,Object>> findByPId(String pid) {
		return nodeRepository.findByPid(pid);
	}
}
