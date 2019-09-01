package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @author: zandaoguang
 * @description: 用户/角色/权限
 */
public interface UserPositionDao {

	/**
	 * 新增personnel关系数据表
	 */
	void addUserPosition(JSONObject jsonObject);

	void updateUserPosition(JSONObject jsonObject);
}
