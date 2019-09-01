package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author: dengchenglong
 */
public interface PositionService {
	/**
	 * 新增岗位信息
	 */
	JSONObject addPosition(JSONObject jsonObject);

	/**
	 * 岗位信息列表
	 */
	JSONObject listPosition(JSONObject jsonObject);

	/**
	 *
	 * 根据岗位名称判断是否重复
	 */
	JSONObject getPositionByContent(JSONObject request2Json);

	/**
	 * 搜索列表
	 */

	JSONObject listPositionBySearch(JSONObject jsonObject);

	/**
	 * 更新岗位信息
	 */
	JSONObject updatePosition(JSONObject jsonObject);

	/**
	 * 删除岗位信息
	 */
	JSONObject deletePosition(JSONObject jsonObject);
}