package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: bguan
 */
public interface ProjectService {
	/**
	 * 新增项目
	 */
	JSONObject addProject(JSONObject jsonObject);

	/**
	 * 项目列表
	 */
	JSONObject listProject(JSONObject jsonObject);

	/**
	 * 获取所有项目岗位
	 */
	JSONObject getAllProjectPositions(JSONObject jsonObject);

	/**
	 * 更新项目
	 */
	JSONObject updateProject(JSONObject jsonObject);

	/**
	 * 删除项目
	 */
	JSONObject deleteProject(JSONObject jsonObject);

	/**
	 * 按名称查询项目信息
	 */
	JSONObject getProjectByName(JSONObject request2Json);

	/**
	 * 按项目ID查成员
	 */
	JSONObject getProjectMemberByID(JSONObject request2Json);

	/**
	 * 删除项目成员
	 */
	JSONObject deleteProjectMember(JSONObject jsonObject);

    /**
     * 添加成员到指定ID项目中
     */
    JSONObject addProjectMember(JSONObject jsonObject);

	/**
	 * 搜索列表(not used)
	 */

	JSONObject listProjectBySearch(JSONObject jsonObject);

	/**
	 * 按照内容排序列表(not used)
	 */
	JSONObject listProjectByOrder(JSONObject jsonObject);

	/**
	 * 当搜索部门的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部，按照内容排序列表(not used)
	 */
	JSONObject listProjectByContentHaveSearch(JSONObject jsonObject);



	/**
	 * 当搜索部门的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码(not used)
	 */
	JSONObject getListByOrderCreateTimeHaveSearch(JSONObject jsonObject);
	/**
	 * 按照创建时间排序列表，默认搜索全部(not used)
	 */
	JSONObject listProjectByOrderCreateTime(JSONObject jsonObject);

    JSONObject getProjectListById(JSONObject requestJson);

}
