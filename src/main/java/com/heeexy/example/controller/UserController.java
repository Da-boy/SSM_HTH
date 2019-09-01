package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PersonnelService;
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
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private PersonnelService personnelService;

	@RequiresPermissions("department:update")
	@PostMapping("/updateDepartment")
	public JSONObject updateDepartment(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, " value, deleteStatus, userId");
		System.out.println("requestJson:"+requestJson);

		return personnelService.updateDepartments(requestJson);

	}

	/**
	 * 查询用户列表
	 */
//	@RequiresPermissions("user:list")
	@GetMapping("/list")
	public JSONObject listUser(HttpServletRequest request) {
		return userService.listUser(CommonUtil.request2Json(request));
	}


	/**
	 * 查询用户列表
	 */
//	@RequiresPermissions("project:list")
	@GetMapping("/getAllProjects")
	public JSONObject getAllProjects(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return userService.getAllProjects(CommonUtil.request2Json(request));
//		return userService.listUser(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("project:list")
	@PostMapping("/getAllProjectsByPerson")
	public JSONObject getAllProjectsByPerson(@RequestBody JSONObject jsonObject) {
//		System.out.println(CommonUtil.request2Json(request));
		System.out.println("jsonObject:"+jsonObject);
		return userService.getAllProjectsByPerson(jsonObject);
//		return userService.listUser(CommonUtil.request2Json(request));
	}

	/**
	 * 查询用户列表
	 */
//	@RequiresPermissions("user:list")
	@GetMapping("/listUserBySearch")
	public JSONObject listUserBySearch(HttpServletRequest request) {
		return userService.listUserBySearch(CommonUtil.request2Json(request));
	}

	@RequiresPermissions("staff:add")
	@PostMapping("/addUser")
	public JSONObject addUser(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "username, password, nickname, roleId ,degreeId,gender,nation,major,native,graduated_from,staffNameEn,email,emergencyPeople,nationality,certificateNumber,phone,emergencyPhone,certificateId,age,marital_status,staff_state,birthday");
		System.out.println(requestJson);
		return userService.addUser(requestJson);
	}

	@RequiresPermissions("staff:update")
	@PostMapping("/updateUser")
	public JSONObject updateUser(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, " nickname,   roleId, deleteStatus, userId");
		System.out.println("requestJson:"+requestJson);
		return userService.updateUser(requestJson);
//		return new JSONObject();
	}

	@RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
	@GetMapping("/getAllRoles")
	public JSONObject getAllRoles() {
		return userService.getAllRoles();
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
