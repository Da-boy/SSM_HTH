package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: bguan
 */
public interface DepartmentService {
	/**
	 * 新增部门
	 */
	JSONObject addDepartment(JSONObject jsonObject);

	/**
	 * 部门列表
	 */
	JSONObject listDepartment(JSONObject jsonObject);

	/**
	 * 子部门列表
	 */
	JSONObject listDepartmentByParent(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */

	JSONObject listDepartmentBySearch(JSONObject jsonObject);

	/**
	 * 按照内容排序列表
	 */
	JSONObject listDepartmentByOrder(JSONObject jsonObject);

	/**
	 * 当搜索部门的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部，按照内容排序列表
	 */
	JSONObject listDepartmentByContentHaveSearch(JSONObject jsonObject);



	/**
	 * 当搜索部门的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码
	 */
	JSONObject getListByOrderCreateTimeHaveSearch(JSONObject jsonObject);
	/**
	 * 按照创建时间排序列表，默认搜索全部
	 */
	JSONObject listDepartmentByOrderCreateTime(JSONObject jsonObject);

	/**
	 * 更新部门
	 */
	JSONObject updateDepartment(JSONObject jsonObject);

	/**
	 * 删除部门
	 */
	
	JSONObject deleteDepartment(JSONObject jsonObject);

	/**
	* 按部门名称查询部门信息
	 */
	JSONObject getDepartmentByName(JSONObject request2Json);
}
