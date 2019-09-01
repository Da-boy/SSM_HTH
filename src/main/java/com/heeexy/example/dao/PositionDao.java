package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: dengchenglong
 * @description: 岗位信息Dao层
 */
public interface PositionDao {
	/**
	 * 新增岗位信息
	 */
	int addPosition(JSONObject jsonObject);

	/**
	 * 统计岗位信息总数
	 */
	int countPosition(JSONObject jsonObject);

	/**
	 * 统计条件文章的总数
	 */

	/**
	 *
	 * 判断岗位名称是否重复
	 */
	List<JSONObject> getPositionByContent(JSONObject jsonObject);

	int countPositionBySearch(JSONObject jsonObject);

	/**
	 * 岗位信息列表
	 */
	List<JSONObject> listPosition(JSONObject jsonObject);

	/**
	 * 条件岗位信息列表
	 */
	List<JSONObject> listPositionBySearch(JSONObject jsonObject);

	/**
	 * 更新岗位信息
	 */
	int updatePosition(JSONObject jsonObject);

	/**
	 * 删除岗位信息
	 */

	int deletePosition(JSONObject jsonObject);
}