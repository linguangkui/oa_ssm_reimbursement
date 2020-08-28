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
	
	//根据主键查找员工
	Employee findEmployeeManager(long id);
	
	List<Employee> findUsers();
	
	List<EmployeeCustom> findUserAndRoleList();
	
	void updateEmployeeRole(String roleId,String userId);
	
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
