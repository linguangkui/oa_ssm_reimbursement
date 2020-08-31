package com.web.oa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String findUserList(String userId,Model model) {
		List<SysRole> allRoles = sysService.findAllRoles();
		List<EmployeeCustom> list = employeeService.findUserAndRoleList();
		model.addAttribute("userList", list);
		model.addAttribute("allRoles", allRoles);
		return "userlist";
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
	public String toAddRole(Model model) {
		List<MenuTree> allPermissions = sysService.loadMenuTree();
		List<SysPermission> menus = sysService.findAllMenus();
		List<SysRole> permissionList = sysService.findRolesAndPermissions();
		model.addAttribute("allPermissions", allPermissions);
		model.addAttribute("menuTypes", menus);
		model.addAttribute("roleAndPermissionsList", permissionList);
		return "rolelist";
	}
	
	@RequestMapping("/saveRoleAndPermissions")
	public void saveRoleAndPermissions(SysRole role,int[] permissionIds,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//设置role主键，使用uuid
		String uuid = UUID.randomUUID().toString();
		role.setId(uuid);
		//默认可用
		role.setAvailable("1");
		sysService.addRoleAndPermissions(role, permissionIds);
		out.write("<script type=\"text/javascript\">");
		out.write("alert(\"保存角色成功！\");");
		out.write("window.location.href=\"findRoles\"");
		out.write("</script>");
	}
	
	@RequestMapping("/saveSubmitPermission")
	public void saveSubmitPermission(SysPermission permission,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (permission.getAvailable() == null) {
			permission.setAvailable("0");
		}
		sysService.addSysPermission(permission);
		out.write("<script type=\"text/javascript\">");
		out.write("alert(\"保存权限成功！\");");
		out.write("window.location.href=\"toAddRole\"");
		out.write("</script>");
	}
	
	@RequestMapping("/findRoles")  
	public String findRoles(Model model) {
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		List<SysRole> roles = sysService.findAllRoles();
		List<MenuTree> allMenuAndPermissions = sysService.getAllMenuAndPermision();
		model.addAttribute("allRoles", roles);
		model.addAttribute("activeUser", activeUser);
		model.addAttribute("allMenuAndPermissions", allMenuAndPermissions);
		return "permissionlist";
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
