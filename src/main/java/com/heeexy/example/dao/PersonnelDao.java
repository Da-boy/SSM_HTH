package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zandaoguang
 * @description: 用户/角色/权限
 */
public interface PersonnelDao {

	/**
	 * 新增personnel关系数据表
	 */
	int addPersonnel(JSONObject jsonObject);

    void updatePersonnel(JSONObject jsonObject);

    void delDeptById(JSONObject preJson);

	void addDept(JSONObject valueJson);

}
