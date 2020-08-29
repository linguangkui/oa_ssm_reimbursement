package com.web.oa.service.impl;

import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.oa.mapper.EmployeeMapper;
import com.web.oa.mapper.SysPermissionMapperCustom;
import com.web.oa.mapper.SysRoleMapper;
import com.web.oa.mapper.SysUserRoleMapper;
import com.web.oa.pojo.Employee;
import com.web.oa.pojo.EmployeeCustom;
import com.web.oa.pojo.EmployeeExample;
import com.web.oa.pojo.SysUserRole;
import com.web.oa.pojo.SysUserRoleExample;
import com.web.oa.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private SysPermissionMapperCustom permissionMapper;
	@Autowired
	private SysUserRoleMapper userRoleMapper;
	@Autowired
	private SysRoleMapper roleMapper;
	@Override
	public Employee findEmployeeByName(String name) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		List<Employee> list = employeeMapper.selectByExample(example);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Employee findEmployeeManager(long id) {
		return employeeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Employee> findUsers() {
		return employeeMapper.selectByExample(null);
	}

	@Override
	public List<EmployeeCustom> findUserAndRoleList() {
		return permissionMapper.findUserAndRoleList();
	}

	@Override
	public void updateEmployeeRole(String roleId, String userId) {
		SysUserRoleExample example = new SysUserRoleExample();
		SysUserRoleExample.Criteria criteria = example.createCriteria();
		criteria.andSysUserIdEqualTo(userId);
		
		SysUserRole userRole = userRoleMapper.selectByExample(example).get(0);
		userRole.setSysRoleId(roleId);
		
		userRoleMapper.updateByPrimaryKey(userRole);
	}

	//根据员工级别查找员工信息
	@Override
	public List<Employee> findEmployeeByLevel(int level) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andRoleEqualTo(level);
		List<Employee> list = employeeMapper.selectByExample(example);
		return list;
	}

	@Override
	public void saveEmployee(Employee employee) {
		String password = employee.getPassword();
		String salt = employee.getSalt();
		//进行md5加密
		Md5Hash md5Hash = new Md5Hash(password, salt, 2);
		//重新封装员工的密码
		employee.setPassword(md5Hash.toString());
		employeeMapper.insertSelective(employee);
		//根据名字查询出员工信息
		Employee emp = this.findEmployeeByName(employee.getName());
		//封装员工信息到SysUserRole
		SysUserRole userRole = new SysUserRole();
		userRole.setId(emp.getId().toString());
		userRole.setSysUserId(emp.getName());
		userRole.setSysRoleId(emp.getRole().toString());
		userRoleMapper.insertSelective(userRole);
		}

	@Override
	public int deleteRoleById(String id) {
		return  roleMapper.deleteByPrimaryKey(id);
	}

}
