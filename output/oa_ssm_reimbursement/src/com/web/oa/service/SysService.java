package com.web.oa.service;

import java.util.List;

import com.web.oa.pojo.Employee;
import com.web.oa.pojo.MenuTree;
import com.web.oa.pojo.SysPermission;
import com.web.oa.pojo.SysRole;

public interface SysService {
	/**
	 * 根据用户id查询权限范围的菜单
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<SysPermission> findMenuListByUserId(String userid) throws Exception;
	/**
	 * 根据用户id查询权限范围的url
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<SysPermission> findPermissionListByUserId(String userid) throws Exception;
	/**
	 * 加载菜单
	 * @return
	 */
	public List<MenuTree> loadMenuTree();
	/**
	 * 查询所有的角色
	 * @return
	 */
	public List<SysRole> findAllRoles();
	/**
	 * 根据userId查询所有角色和权限
	 * @param userId
	 * @return
	 */
	public SysRole findRolesAndPermissionsByUserId(String userId);
	/**
	 * 
	 * @param role
	 * @param permissionIds
	 */
	public void addRoleAndPermissions(SysRole role,int[] permissionIds);
	/**
	 * 查询所有menu类permission
	 * @return
	 */
	public List<SysPermission> findAllMenus();
	/**
	 * 添加权限
	 * @param permission
	 */
	public void addSysPermission(SysPermission permission);
	/**
	 * 根据用户ID查询其所有的菜单和权限
	 * @param userId
	 * @return
	 */
	public List<SysPermission> findMenuAndPermissionByUserId(String userId);
	/**
	 * 查询所有的菜单和权限
	 * @return
	 */
	public List<MenuTree> getAllMenuAndPermision();
	/**
	 * 根据角色ID查询权限
	 * @param roleId
	 * @return
	 */
	public List<SysPermission> findPermissionsByRoleId(String roleId);
	/**
	 * 修改角色和权限
	 * @param roleId
	 * @param permissionIds
	 */
	public void updateRoleAndPermissions(String roleId,int[] permissionIds);
	/**
	 * 查询所有的角色和权限
	 * @return
	 */
	public List<SysRole> findRolesAndPermissions();
}
