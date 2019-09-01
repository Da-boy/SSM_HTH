package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.ProjectService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: bguan
 * @description: 项目相关Controller
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	/**
	 * 查询项目列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listProject")
	public JSONObject listProject(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return projectService.listProject(CommonUtil.request2Json(request));
	}

    /**
     * 获取所有项目岗位
     */
//    @RequiresPermissions("article:list")
    @GetMapping("/projectPosition")
    public JSONObject getAllProjectPositions(HttpServletRequest request) {
        System.out.println(CommonUtil.request2Json(request));
        return projectService.getAllProjectPositions(CommonUtil.request2Json(request));
    }

	/**
	 * 新增项目
	 */
	@RequiresPermissions("project:add")
	@PostMapping("/addProject")
	public JSONObject addProject(@RequestBody JSONObject requestJson) {
		System.out.println("add_project");
		CommonUtil.hasAllRequired(requestJson, "projectName, managerId, projectDesc, committeeId, startDate, endDate, loginUserId");
		return projectService.addProject(requestJson);
	}

	/**
	 * 修改项目
	 */
	@RequiresPermissions("project:update")
	@PostMapping("/updateProject")
	public JSONObject updateProject(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "projectId, projectName, managerId, projectDesc,committeeId, startDate, endDate, loginUserId");
		return projectService.updateProject(requestJson);
	}

	/**
	 * 删除项目
	 */
	@RequiresPermissions("project:delete")
	@PostMapping("/deleteProject")
	public JSONObject deleteProject(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "project_id");
		return projectService.deleteProject(requestJson);
	}

	/**
	 * 查询部门是否存在(按部门名称查询部门信息)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/getProjectByName")
	public JSONObject getProjectByName(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return projectService.getProjectByName(CommonUtil.request2Json(request));
	}

	/**
	 * 按项目ID获取项目成员
	 */
//	@RequiresPermissions("article:list")
	@PostMapping("/getProjectMemberByID")
	public JSONObject getProjectMemberByID(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "projectId");
        System.out.println("test--------------------------");
		return projectService.getProjectMemberByID(requestJson);
	}
	/**
	 * 删除项目成员(从project_staff表删除)
	 */
	@RequiresPermissions("project:delete")
	@PostMapping("/deleteProjectMember")
	public JSONObject deleteProjectMember(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "projectId, userId");
		return projectService.deleteProjectMember(requestJson);
	}

	/**
     * 排序+搜索项目
     */
//    @RequiresPermissions("article:list")
    @GetMapping("/listProjectBySearch")
    public JSONObject listProjectBySearch(HttpServletRequest request) {
        return projectService.listProjectBySearch(CommonUtil.request2Json(request));
    }

    /**
     * 添加成员到指定ID项目中
     */
    @RequiresPermissions("project:add")
    @PostMapping("/addProjectMember")
    public JSONObject addProjectMember(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "projectId, userId");
        return projectService.addProjectMember(requestJson);
    }

	/**
	 * 搜索文章列表(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listProjectByOrder")
	public JSONObject listProjectByOrder(HttpServletRequest request) {
		return projectService.listProjectByOrder(CommonUtil.request2Json(request));
	}

	/**
	 * 搜索文章列表(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listProjectByOrderCreateTime")
	public JSONObject listProjectByOrderCreateTime(HttpServletRequest request) {
		return projectService.listProjectByOrderCreateTime(CommonUtil.request2Json(request));
	}

	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listProjectByContentHaveSearch")
	public JSONObject listProjectByContentHaveSearch(HttpServletRequest request) {
		return projectService.listProjectByContentHaveSearch(CommonUtil.request2Json(request));
	}

	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/getListByOrderCreateTimeHaveSearch")
	public JSONObject getListByOrderCreateTimeHaveSearch(HttpServletRequest request) {
		return projectService.getListByOrderCreateTimeHaveSearch(CommonUtil.request2Json(request));
	}
}
