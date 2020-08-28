package com.web.oa.service;

import java.util.List;

import com.web.oa.pojo.BaoxiaoBill;

public interface BaoxiaoService {
	/**
	 * 根据userid查询报销单列表信息
	 * @param userid
	 * @return
	 */
	List<BaoxiaoBill> findBaoxiaoBillListByUser(Long userid);
	/**
	 * 保存报销单到数据库
	 * @param baoxiaoBill
	 */
	void saveBaoxiao(BaoxiaoBill baoxiaoBill); 
	/**
	 * 根据id查询报销单信息
	 * @param id
	 * @return
	 */
	BaoxiaoBill findBaoxiaoBillById(Long id);
	/**
	 * 根据id删除报销单信息
	 * @param id
	 * @return
	 */
	int deleteBaoxiaoBillById(Long id);
	/**
	 * 通过员工查询报销单信息
	 * @param id
	 * @return
	 */
	List<BaoxiaoBill> findLeaveBillListByUser(Long id);
}
