package com.web.oa.service;

import java.util.List;

import com.web.oa.pojo.Employee;
import com.web.oa.pojo.EmployeeCustom;

public interface EmployeeService {
	/**
	 * 根据员工帐号查找员工列表
	 * @param name
	 * @return
	 */
	Employee findEmployeeByName(String name);
	/**
	 * 根据主键查找员工
	 * @param id
	 * @return
	 */
	Employee findEmployeeManager(long id);
	/**
	 * 查询所有的员工
	 * @return
	 */
	List<Employee> findUsers();
	/**
	 * 查询所有的员工和角色列表
	 * @return
	 */
	List<EmployeeCustom> findUserAndRoleList();
	/**
	 * 修改员工的角色
	 * @param roleId
	 * @param userId
	 */
	void updateEmployeeRole(String roleId,String userId);
	/**
	 * 根据level查询所有员工
	 * @param level
	 * @return
	 */
	List<Employee> findEmployeeByLevel(int level);
	/**
	 * 插入员工信息
	 * @param employee
	 */
	void saveEmployee(Employee employee);
	/**
	 * 根据id删除角色信息
	 * @param id
	 * @return
	 */
	 int deleteRoleById(String id);
}
