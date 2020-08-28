package com.web.oa.service;

import java.util.List;

import com.web.oa.pojo.BaoxiaoBill;

public interface BaoxiaoService {

	List<BaoxiaoBill> findBaoxiaoBillListByUser(Long userid);
	
	void saveBaoxiao(BaoxiaoBill baoxiaoBill); 
	
	BaoxiaoBill findBaoxiaoBillById(Long id);
	
	int deleteBaoxiaoBillById(Long id);

	List<BaoxiaoBill> findLeaveBillListByUser(Long id);
}
