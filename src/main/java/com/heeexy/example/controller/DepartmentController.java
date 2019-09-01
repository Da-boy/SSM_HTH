package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.DepartmentService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: bguan
 * @description: 部门相关Controller
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 查询部门列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listDepartment")
	public JSONObject listDepartment(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return departmentService.listDepartment(CommonUtil.request2Json(request));
	}

	/**
	 * 查询子部门列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listDepartmentByParent")
	public JSONObject listDepartmentByParent(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return departmentService.listDepartmentByParent(CommonUtil.request2Json(request));
	}

    /**
     * 搜索部门getAllDeparments
     */
//    @RequiresPermissions("article:list")
    @GetMapping("/listDepartmentBySearch")
    public JSONObject listDepartmentBySearch(HttpServletRequest request) {
        return departmentService.listDepartmentBySearch(CommonUtil.request2Json(request));
    }


	/**
	 * 搜索文章列表(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listDepartmentByOrder")
	public JSONObject listDepartmentByOrder(HttpServletRequest request) {
		return departmentService.listDepartmentByOrder(CommonUtil.request2Json(request));
	}


	/**
	 * 搜索文章列表(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listDepartmentByOrderCreateTime")
	public JSONObject listDepartmentByOrderCreateTime(HttpServletRequest request) {
		return departmentService.listDepartmentByOrderCreateTime(CommonUtil.request2Json(request));
	}


	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listDepartmentByContentHaveSearch")
	public JSONObject listDepartmentByContentHaveSearch(HttpServletRequest request) {
		return departmentService.listDepartmentByContentHaveSearch(CommonUtil.request2Json(request));
	}


	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/getListByOrderCreateTimeHaveSearch")
	public JSONObject getListByOrderCreateTimeHaveSearch(HttpServletRequest request) {
		return departmentService.getListByOrderCreateTimeHaveSearch(CommonUtil.request2Json(request));
	}

	/**
	 * 新增部门
	 */
	@RequiresPermissions("department:add")
	@PostMapping("/addDepartment")
	public JSONObject addDepartment(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "departmentName, departmentDesc, staffId, loginUserId");
		return departmentService.addDepartment(requestJson);
	}

	/**
	 * 修改部门
	 */
	@RequiresPermissions("department:update")
	@PostMapping("/updateDepartment")
	public JSONObject updateDepartment(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "departmentId, departmentName,departmentDesc,staffId,loginUserId");
		return departmentService.updateDepartment(requestJson);
	}

	/**
	 * 删除部门
	 */
	@RequiresPermissions("department:delete")
	@PostMapping("/deleteDepartment")
	public JSONObject deleteDepartment(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "department_id");
		return departmentService.deleteDepartment(requestJson);
	}

	/**
	 * 查询部门是否存在(按部门名称查询部门信息)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/getDepartmentByName")
	public JSONObject getDepartmentByName(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return departmentService.getDepartmentByName(CommonUtil.request2Json(request));
	}
}
