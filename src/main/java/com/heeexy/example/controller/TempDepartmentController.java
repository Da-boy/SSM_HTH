package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.tempDepartmentService;
import com.heeexy.example.service.UserService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zandaoguang
 * @description: 用户/角色/权限相关controller
 */
@RestController
@RequestMapping("/department")
public class TempDepartmentController {
	@Autowired
	private UserService userService;
	@Autowired
	private tempDepartmentService tempDepartmentService;

	/**
	 * 查询用户列表
	 */
//	@RequiresPermissions("user:list")
	@GetMapping("/list")
	public JSONObject listUser(HttpServletRequest request) {
		return userService.listUser(CommonUtil.request2Json(request));
	}

	@RequiresPermissions("user:add")
	@PostMapping("/addUser")
	public JSONObject addUser(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "username, password, nickname,   roleId");
		System.out.println(requestJson);
		return userService.addUser(requestJson);
	}

	@RequiresPermissions("user:update")
	@PostMapping("/updateUser")
	public JSONObject updateUser(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, " nickname,   roleId, deleteStatus, userId");
		System.out.println("requestJson:"+requestJson);
		return userService.updateUser(requestJson);
	}

	@RequiresPermissions(value = {"department:add", "department:update"}, logical = Logical.OR)
	@GetMapping("/getAllDepartments")
	public JSONObject getAllDepartments() {
		return tempDepartmentService.getAllDepartments();
	}

	@RequiresPermissions(value = {"department:add", "department:update"}, logical = Logical.OR)
	@PostMapping("/getAllDepartmentsArch")
	public JSONObject getAllDepartmentsArch(@RequestBody JSONObject requestJson) {

//		System.out.println("requestJson:"+requestJson);
		return tempDepartmentService.getAllDepartmentsArch(requestJson);
//		return new JSONObject();
	}

	@RequiresPermissions(value = {"department:add", "department:update"}, logical = Logical.OR)
	@PostMapping("/getDepartmentPerson")
	public JSONObject getDepartmentPerson(@RequestBody JSONObject requestJson) {

//		System.out.println("requestJson:"+requestJson);
		return tempDepartmentService.getDepartmentPerson(requestJson);
//		return new JSONObject();
	}

	/**
	 * 角色列表
	 */
//	@RequiresPermissions("role:list")
	@GetMapping("/listRole")
	public JSONObject listRole() {
		return userService.listRole();
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
//	@RequiresPermissions("role:list")
	@GetMapping("/listAllPermission")
	public JSONObject listAllPermission() {
		return userService.listAllPermission();
	}

	/**
	 * 新增角色
	 */
	@RequiresPermissions("role:add")
	@PostMapping("/addRole")
	public JSONObject addRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
		return userService.addRole(requestJson);
	}

	/**
	 * 修改角色
	 */
	@RequiresPermissions("role:update")
	@PostMapping("/updateRole")
	public JSONObject updateRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");
		return userService.updateRole(requestJson);
	}

	/**
	 * 删除角色
	 */
	@RequiresPermissions("role:delete")
	@PostMapping("/deleteRole")
	public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId");
		return userService.deleteRole(requestJson);
	}
}
