package com.web.oa.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.BaoxiaoBill;
import com.web.oa.pojo.Employee;
import com.web.oa.service.BaoxiaoService;
import com.web.oa.service.EmployeeService;
import com.web.oa.service.WorkFlowService;
import com.web.oa.utils.Constants;

@Controller
public class BaoxiaoBillController {
	
	@Autowired
	private BaoxiaoService baoxiaoService;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	//显示当前待办人的报销单
	@RequestMapping("/myBaoxiaoBill")
	public String myBaoxiaoBill(ModelMap model,HttpSession session){
		//查询所有的请假信息（对应BaoxiaoBill），返回List<LeaveBill>
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		List<BaoxiaoBill> list = baoxiaoService.findBaoxiaoBillListByUser(activeUser.getId()); 
		//放置到上下文对象中
		model.addAttribute("baoxiaoList", list);
		return "baoxiaobill";
	}
	
	//查看当前流程图
	@RequestMapping("/viewCurrentImageByBill")
	public String viewCurrentImageByBill(long billId,ModelMap model) {
		String BUSSINESS_KEY = Constants.BAOXIAO_KEY + "." + billId;
		Task task = this.workFlowService.findTaskByBussinessKey(BUSSINESS_KEY);
		//获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
		ProcessDefinition pd = workFlowService.findProcessDefinitionByTaskId(task.getId());

		model.addAttribute("deploymentId", pd.getDeploymentId());
		model.addAttribute("imageName", pd.getDiagramResourceName());
		/*查看当前活动，获取当期活动对应的坐标x,y,width,height，
		 * 将4个值存放到Map<String,Object>中
		 * */
		Map<String, Object> map = workFlowService.findCoordingByTask(task.getId());
		model.addAttribute("acs", map);
		return "viewimage";
	}
	
	@RequestMapping("/leaveBillAction_delete")
	public void leaveBillAction_delete(long id,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int rows = baoxiaoService.deleteBaoxiaoBillById(id);
		if (rows>0) {
			out.write("<script type=\"text/javascript\">");
			out.write("alert(\"删除成功！\");");
			out.write("window.location.href=\"myBaoxiaoBill\"");
			out.write("</script>");
		}else {
			out.write("<script type=\"text/javascript\">");
			out.write("alert(\"删除失败！\");");
			out.write("window.location.href=\"myBaoxiaoBill\"");
			out.write("</script>");
		}
	}
}
