package com.web.oa.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Employee;
import com.web.oa.pojo.EmployeeCustom;
import com.web.oa.pojo.MenuTree;
import com.web.oa.pojo.SysPermission;
import com.web.oa.pojo.SysRole;
import com.web.oa.service.EmployeeService;
import com.web.oa.service.SysService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private SysService sysService;
	
	@RequestMapping("/findUserList")
	public ModelAndView findUserList(String userId) {
		ModelAndView mv = new ModelAndView();
		List<SysRole> allRoles = sysService.findAllRoles();
		List<EmployeeCustom> list = employeeService.findUserAndRoleList();
		
		mv.addObject("userList", list);
		mv.addObject("allRoles", allRoles);
		mv.setViewName("userlist");
		return mv;
	}
	
	@RequestMapping("/saveUser")
	public String saveUser(Employee employee) {
		long id = 1;
		employee.setSalt("eteokues");
		if (employee.getRole().equals(4)) {
			employee.setManagerId(id);
		}else if (employee.getRole().equals(3)) {
			employee.setManagerId(id);
		}else {
			employee.setManagerId(employee.getManagerId());
		}
		employeeService.saveEmployee(employee);
		return "redirect:/findUserList";		
	}
	
	
	@RequestMapping("/assignRole")
	@ResponseBody
	public Map<String, String> assignRole(String roleId,String userId) {
		Map<String, String> map = new HashMap<>(); 
		try {
			employeeService.updateEmployeeRole(roleId, userId);
			map.put("msg", "分配权限成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "分配权限失败");
		}
		return map;
	}
	
	@RequestMapping("/toAddRole")
	public ModelAndView toAddRole() {
		List<MenuTree> allPermissions = sysService.loadMenuTree();
		List<SysPermission> menus = sysService.findAllMenus();
		List<SysRole> permissionList = sysService.findRolesAndPermissions();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("allPermissions", allPermissions);
		mv.addObject("menuTypes", menus);
		mv.addObject("roleAndPermissionsList", permissionList);
		mv.setViewName("rolelist");
		return mv;
		
	}
	
	@RequestMapping("/saveRoleAndPermissions")
	public String saveRoleAndPermissions(SysRole role,int[] permissionIds) {
		//设置role主键，使用uuid
		String uuid = UUID.randomUUID().toString();
		role.setId(uuid);
		//默认可用
		role.setAvailable("1");
		sysService.addRoleAndPermissions(role, permissionIds);
		
		return "redirect:/toAddRole";
	}
	
	@RequestMapping("/saveSubmitPermission")
	public String saveSubmitPermission(SysPermission permission) {
		if (permission.getAvailable() == null) {
			permission.setAvailable("0");
		}
		sysService.addSysPermission(permission);
		return "redirect:/toAddRole";
	}
	
	@RequestMapping("/findRoles")  
	public ModelAndView findRoles() {
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		List<SysRole> roles = sysService.findAllRoles();
		List<MenuTree> allMenuAndPermissions = sysService.getAllMenuAndPermision();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("allRoles", roles);
		mv.addObject("activeUser",activeUser);
		mv.addObject("allMenuAndPermissions", allMenuAndPermissions);
		mv.setViewName("permissionlist");
		return mv;
	}
	
	@RequestMapping("/loadMyPermissions")
	@ResponseBody
	public List<SysPermission> loadMyPermissions(String roleId) {
		List<SysPermission> list = sysService.findPermissionsByRoleId(roleId);
		return list;
	}
	
	@RequestMapping("/updateRoleAndPermission")
	public String updateRoleAndPermission(String roleId,int[] permissionIds) {
		sysService.updateRoleAndPermissions(roleId, permissionIds);
		return "redirect:/findRoles";		
	}
	
	@RequestMapping("/viewPermissionByUser")
	@ResponseBody
	public SysRole viewPermissionByUser(String userName) {
		SysRole sysRole = sysService.findRolesAndPermissionsByUserId(userName);
		return sysRole;
	}
	
	@RequestMapping("/findNextManager")
	@ResponseBody
	public List<Employee> findNextManager(int level) {
		level++; //加一，表示下一个级别
		List<Employee> list = employeeService.findEmployeeByLevel(level);
		System.out.println(list);
		return list;
	}
	
	@RequestMapping("/deleteRoleById")
	public void deleteRoleById(String id,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int rows = employeeService.deleteRoleById(id);
		if (rows>0) {
			out.write("<script type=\"text/javascript\">");
			out.write("alert(\"删除成功！\");");
			out.write("window.location.href=\"findRoles\"");
			out.write("</script>");
		}else {
			out.write("<script type=\"text/javascript\">");
			out.write("alert(\"删除失败！\");");
			out.write("window.location.href=\"findRoles\"");
			out.write("</script>");
		}
	}
}
