package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: bguan
 * @description: 项目Dao层
 */
public interface ProjectDao {
	/**
	 * 新增项目
	 */
	int addProject(JSONObject jsonObject);

	/**
	 * 更新部门
	 */
	int updateProject(JSONObject jsonObject);

	/**
	 * 删除部门
	 */

	int deleteProject(JSONObject jsonObject);

	List<JSONObject> getProjectByName(JSONObject jsonObject);

	/**
	 * 按项目ID获取成员列表
	 */
	List<JSONObject> getProjectMemberByID(JSONObject jsonObject);

	/**
	 * 获取所有项目岗位
	 */
	List<JSONObject> getAllProjectPositions(JSONObject jsonObject);


	/**
	 * 删除项目成员
	 */
	int deleteProjectMember(JSONObject jsonObject);
	int deleteProjectStaffPosition(JSONObject jsonObject);

    /**
     * 添加成员到指定ID项目中，同时添加成员的项目岗位
     */
    int addProjectMember(JSONObject jsonObject);
	int addProjectStaffPosition(JSONObject jsonObject);

	/**
	 * 添加成员到指定ID项目的同时，必要时同时添加项目岗位到表pro_position中
	 */
	int addProjectPosition(JSONObject jsonObject);
	int getProjectPositionId(JSONObject jsonObject);


	/**
	 * 统计项目总数
	 */
	int countProject(JSONObject jsonObject);

	/**
	 * 统计条件部门的总数(not used)
	 */

	int countProjectBySearch(JSONObject jsonObject);

	/**
	 * 项目列表
	 */
	List<JSONObject> listProject(JSONObject jsonObject);

	/**
	 * 条件部门列表(not used)
	 */
	List<JSONObject> listProjectBySearch(JSONObject jsonObject);

	/**
	 * 排序项目列表(not used)
	 */
	List<JSONObject> listProjectByOrderAsc(JSONObject jsonObject);

	List<JSONObject> listProjectByContentDesc(JSONObject jsonObject);

	List<JSONObject> listProjectByContentHaveSearchAsc(JSONObject jsonObject);

	List<JSONObject> listProjectByContentHaveSearchDesc(JSONObject jsonObject);

	List<JSONObject> listProjectByOrderCreateTimeAsc(JSONObject jsonObject);

	List<JSONObject> listProjectByOrderCreateTimeDesc(JSONObject jsonObject);

	List<JSONObject> listProjectByOrderCreateTimeHaveSearchAsc(JSONObject jsonObject);

	List<JSONObject> listProjectByOrderCreateTimeHaveSearchDesc(JSONObject jsonObject);


    List<JSONObject> getProjectListById(JSONObject jsonObject);

}
