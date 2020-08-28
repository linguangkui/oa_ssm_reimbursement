package com.web.oa.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.web.oa.pojo.BaoxiaoBill;

public interface WorkFlowService {
	/**
	 * 部署流程（发布流程）
	 * @param in
	 * @param filename
	 */
	void saveNewDeploye(InputStream in, String filename);
	/**
	 * 查询流程部署对象的信息
	 * @return
	 */
	List<Deployment> findDeploymentList();
	/**
	 * 查询流程定义列表
	 * @return
	 */
	List<ProcessDefinition> findProcessDefinitionList();
	/**
	 * 保存报销单信息到数据库，并启动流程
	 * @param baoxiaoId
	 * @param username
	 */
	void saveStartProcess(long baoxiaoId,String username);
	/**
	 * 查询当前待办人的待办任务
	 * @param name
	 * @return
	 */
	List<Task> findTaskListByName(String name);
	/**
	 * 根据taskId查询报销单的信息
	 * @param taskId
	 * @return
	 */
	BaoxiaoBill findBaoxiaoBillByTaskId(String taskId);
	/**
	 * 根据taskId查询批注信息
	 * @param taskId
	 * @return
	 */
	List<Comment> findCommentByTaskId(String taskId);
	/**
	 * 根据taskId查询流程图连线的内容信息
	 * @param taskId
	 * @return
	 */
	List<String> findOutComeListByTaskId(String taskId);
	/**
	 * 提交任务（完成任务）
	 * @param id
	 * @param taskId
	 * @param comemnt
	 * @param outcome
	 * @param username
	 */
	void saveSubmitTask(long id,String taskId,String comemnt,String outcome,String username);
	/**
	 * 根据taskId查询流程定义
	 * @param taskId
	 * @return
	 */
	ProcessDefinition findProcessDefinitionByTaskId(String taskId);
	/**
	 * 根据taskId查询流程图的坐标
	 * @param taskId
	 * @return
	 */
	Map<String, Object> findCoordingByTask(String taskId);
	/**
	 * 读取图片的输入流
	 * @param deploymentId
	 * @param imageName
	 * @return
	 */
	InputStream findImageInputStream(String deploymentId, String imageName);
	/**
	 * 根据BUSSINESS_KEY查询任务
	 * @param BUSSINESS_KEY
	 * @return
	 */
	Task findTaskByBussinessKey(String BUSSINESS_KEY);
	/**
	 * 根据报销单ID查询历史批注
	 * @param id
	 * @return
	 */
	List<Comment> findCommentByBaoxiaoBillId(long id);
	/**
	 * 根据deploymentId删除流程部署对象
	 * @param deploymentId
	 */
	void deleteProcessByDeploymentId(String deploymentId);

	
}
