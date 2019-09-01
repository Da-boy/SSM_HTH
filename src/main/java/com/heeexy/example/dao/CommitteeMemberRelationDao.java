package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CommitteeMemberRelationDao {
	/**
	 * 新增委员会成员
	 */
	int addCommitteeMember(JSONObject jsonObject);


	/**
	 * 删除委员会成员
	 */
	int deleteCommitteeMember(JSONObject jsonObject);

	/**
	 * 删除委员会成员
	 */
	int deleteMemberFromCommittee(JSONObject jsonObject);

	/**
	 * 删除委员会
	 */
	int deleteCommittee(JSONObject jsonObject);

}
