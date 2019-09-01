package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface CommitteeService {
	/**
	 * 新增委员会
	 */
	JSONObject addCommittee(JSONObject jsonObject);

	/**
	 * 委员会列表
	 */
	JSONObject listCommittee(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */

	JSONObject listCommitteeBySearch(JSONObject jsonObject);

	/**
	 * 更新文章
	 */
	JSONObject updateCommittee(JSONObject jsonObject);

	/**
	 * 删除文章
	 */
	
	JSONObject deleteCommittee(JSONObject jsonObject);

}
