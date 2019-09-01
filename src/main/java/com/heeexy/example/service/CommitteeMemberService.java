package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface CommitteeMemberService {
	/**
	 * 新增委员会成员
	 */
	JSONObject addCommitteeMember(JSONObject jsonObject);

	/**
	 * 委员会成员列表
	 */
	JSONObject listCommitteeMember(JSONObject jsonObject);

	/**
	 * 委员会成员列表
	 */
	JSONObject listCommitteeMemberByCommittee(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */
	JSONObject listCommitteeMemberByCommitteeAndSearch(JSONObject jsonObject);

	/**
	 * 更新委员会成员
	 */
	JSONObject updateCommitteeMember(JSONObject jsonObject);

	/**
	 * 删除委员会成员
	 */
	JSONObject deleteCommitteeMember(JSONObject jsonObject);

	/**
	 * 删除委员会成员
	 */
	JSONObject deleteMemberFromCommittee(JSONObject jsonObject);

}
