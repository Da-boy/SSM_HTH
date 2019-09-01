package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CommitteeDao {
	/**
	 * 新增委员会
	 */
	int addCommittee(JSONObject jsonObject);

	/**
	 * 统计委员会总数
	 */
	int countCommittee(JSONObject jsonObject);

	/**
	 * 统计条件委员会的总数
	 */

	int countCommitteeBySearch(JSONObject jsonObject);

	/**
	 * 委员会列表
	 */
	List<JSONObject> listCommittee(JSONObject jsonObject);

	/**
	 * 条件委员会列表
	 */
	List<JSONObject> listCommitteeBySearch(JSONObject jsonObject);



	/**
	 * 更新委员会
	 */
	int updateCommittee(JSONObject jsonObject);

	/**
	 * 删除委员会
	 */

	int deleteCommittee(JSONObject jsonObject);

	List<JSONObject> getCommitteeByContent(JSONObject jsonObject);
}
