package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: bguan
 * @description: 部门Dao层
 */
public interface DepartmentDao {
	/**
	 * 新增部门
	 */
	int addDepartment(JSONObject jsonObject);

	/**
	 * 统计部门总数
	 */
	int countDepartment(JSONObject jsonObject);

	/**
	 * 统计条件部门的总数
	 */

	int countDepartmentBySearch(JSONObject jsonObject);

	/**
	 * 部门列表
	 */
	List<JSONObject> listDepartment(JSONObject jsonObject);

	/**
	 * 统计子部门总数
	 */
	int countDepartmentByParent(JSONObject jsonObject);

	/**
	 * 子部门列表
	 */
	List<JSONObject> listDepartmentByParent(JSONObject jsonObject);

	/**
	 * 条件部门列表
	 */
	List<JSONObject> listDepartmentBySearch(JSONObject jsonObject);


	/**
	 * 排序部门列表
	 */
	List<JSONObject> listDepartmentByOrderAsc(JSONObject jsonObject);

	List<JSONObject> listDepartmentByContentDesc(JSONObject jsonObject);

	List<JSONObject> listDepartmentByContentHaveSearchAsc(JSONObject jsonObject);

	List<JSONObject> listDepartmentByContentHaveSearchDesc(JSONObject jsonObject);

	List<JSONObject> listDepartmentByOrderCreateTimeAsc(JSONObject jsonObject);

	List<JSONObject> listDepartmentByOrderCreateTimeDesc(JSONObject jsonObject);

	List<JSONObject> listDepartmentByOrderCreateTimeHaveSearchAsc(JSONObject jsonObject);

	List<JSONObject> listDepartmentByOrderCreateTimeHaveSearchDesc(JSONObject jsonObject);

	/**
	 * 更新部门
	 */
	int updateDepartment(JSONObject jsonObject);

	/**
	 * 删除部门
	 */

	int deleteDepartment(JSONObject jsonObject);

	List<JSONObject> getDepartmentByName(JSONObject jsonObject);
}
