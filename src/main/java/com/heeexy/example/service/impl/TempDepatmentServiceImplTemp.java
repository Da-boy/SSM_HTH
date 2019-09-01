package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.tempDepartmentDao;
import com.heeexy.example.dao.UserDao;
import com.heeexy.example.service.tempDepartmentService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author: zandaoguang
 * @description: 用户/角色/权限
 */
@Service
public class TempDepatmentServiceImplTemp implements tempDepartmentService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private tempDepartmentDao tempDepartmentDao;

	List<JSONObject> jsonList = new ArrayList<>();

	/**
	 * 用户列表
	 */
	@Override
	public JSONObject listUser(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = userDao.countUser(jsonObject);
		List<JSONObject> list = userDao.listUser(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 添加用户
	 */
	@Override
	public JSONObject addUser(JSONObject jsonObject) {
		int exist = userDao.queryExistUsername(jsonObject);
		if (exist > 0) {
			return CommonUtil.errorJson(ErrorEnum.E_10009);
		}
		userDao.addUser(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 查询所有的角色
	 * 在添加/修改用户的时候要使用此方法
	 */
	@Override
	public JSONObject getAllDepartments() {
		List<JSONObject> departments = tempDepartmentDao.getAllDepartments();
		return CommonUtil.successPage(departments);
	}

	@Override
	public JSONObject getAllDepartmentsArch(JSONObject jsonObject) {
		System.out.println(jsonObject);
		System.out.println(jsonObject.get("deptId"));
//		List<JSONObject> jsonObjectList = new ArrayList<>();
//		jsonObjectList.add(jsonObject);
		jsonList.clear();
		getJLDeptById(jsonObject.getIntValue("deptId"));
		System.out.println("jsonList:"+jsonList);
		return CommonUtil.successJson(jsonList);
	}

	void getJLDeptById(int id){
		if(id==0){
			return;
		}else{
			JSONObject tempj = new JSONObject();
			tempj.put("deptId",id);
			JSONObject dept = tempDepartmentDao.getJLDeptById(tempj);
			if(dept == null){
				return;
			}
			jsonList.add(dept);
			System.out.println("superior_id"+dept.get("superior_id"));
			if(dept.get("superior_id") != null){
				getJLDeptById(dept.getIntValue("superior_id"));
			}else{
				getJLDeptById(0);
			}
		}
	}

	@Override
	public JSONObject getDepartmentPerson(JSONObject requestJson) {
		List<List> departPersonList = new ArrayList<>();
		List<JSONObject> depts = tempDepartmentDao.getDeptByUserId(requestJson);
		System.out.println("depts:"+depts);
		for (int i = 0 ; i < depts.size() ; i ++){
			System.out.println(depts.get(i).getIntValue("department_id"));
			JSONObject tempJs = new JSONObject();
			tempJs.put("deptId",depts.get(i).getIntValue("department_id"));
			List<JSONObject> persons = tempDepartmentDao.getPersonsByDeptId(tempJs);
			System.out.println("persons" + i + ":" + persons);
			departPersonList.add(persons);
		}
		System.out.println("departmentPersonList:"+departPersonList);
//		List depts = tempDepartmentDao.getDeptByUserId(requestJson.getIntValue("userId"));
//		for(int i = 0 ; i < depts.size();i++){
//			List<JSONObject> persons = tempDepartmentDao.getPersonsByDeptId(depts[i]);
//			departPersonList.add(persons);
//		}
		return CommonUtil.successJson(departPersonList);
	}

	/**
	 * 修改用户
	 */
	@Override
	public JSONObject updateUser(JSONObject jsonObject) {
		userDao.updateUser(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 角色列表
	 */
	@Override
	public JSONObject listRole() {
		List<JSONObject> roles = userDao.listRole();
		return CommonUtil.successPage(roles);
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@Override
	public JSONObject listAllPermission() {
		List<JSONObject> permissions = userDao.listAllPermission();
		return CommonUtil.successPage(permissions);
	}

	/**
	 * 添加角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject addRole(JSONObject jsonObject) {
		userDao.insertRole(jsonObject);
		userDao.insertRolePermission(jsonObject.getString("roleId"), (List<Integer>) jsonObject.get("permissions"));
		return CommonUtil.successJson();
	}

	/**
	 * 修改角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject updateRole(JSONObject jsonObject) {
		String roleId = jsonObject.getString("roleId");
		List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
		JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
		Set<Integer> oldPerms = (Set<Integer>) roleInfo.get("permissionIds");
		//修改角色名称
		dealRoleName(jsonObject, roleInfo);
		//添加新权限
		saveNewPermission(roleId, newPerms, oldPerms);
		//移除旧的不再拥有的权限
		removeOldPermission(roleId, newPerms, oldPerms);
		return CommonUtil.successJson();
	}

	/**
	 * 修改角色名称
	 */
	private void dealRoleName(JSONObject paramJson, JSONObject roleInfo) {
		String roleName = paramJson.getString("roleName");
		if (!roleName.equals(roleInfo.getString("roleName"))) {
			userDao.updateRoleName(paramJson);
		}
	}

	/**
	 * 为角色添加新权限
	 */
	private void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
		List<Integer> waitInsert = new ArrayList<>();
		for (Integer newPerm : newPerms) {
			if (!oldPerms.contains(newPerm)) {
				waitInsert.add(newPerm);
			}
		}
		if (waitInsert.size() > 0) {
			userDao.insertRolePermission(roleId, waitInsert);
		}
	}

	/**
	 * 删除角色 旧的 不再拥有的权限
	 */
	private void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
		List<Integer> waitRemove = new ArrayList<>();
		for (Integer oldPerm : oldPerms) {
			if (!newPerms.contains(oldPerm)) {
				waitRemove.add(oldPerm);
			}
		}
		if (waitRemove.size() > 0) {
			userDao.removeOldPermission(roleId, waitRemove);
		}
	}

	/**
	 * 删除角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject deleteRole(JSONObject jsonObject) {
		JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
		List<JSONObject> users = (List<JSONObject>) roleInfo.get("users");
		if (users != null && users.size() > 0) {
			return CommonUtil.errorJson(ErrorEnum.E_10008);
		}
		userDao.removeRole(jsonObject);
		userDao.removeRoleAllPermission(jsonObject);
		return CommonUtil.successJson();
	}


}
