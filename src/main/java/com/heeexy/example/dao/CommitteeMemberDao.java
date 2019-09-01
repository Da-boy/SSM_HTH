package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CommitteeMemberDao {
	/**
	 * 新增委员会成员
	 */
	int addCommitteeMember(JSONObject jsonObject);

	/**
	 * 根据身份证id查询member_id，用于新增是建立relation
	 */
	List<JSONObject> getCommitteeMemberIdByCertificateIdOrStaffId(JSONObject jsonObject);

	/**
	 * 统计委员会成员总数
	 */
	int countCommitteeMember(JSONObject jsonObject);

	/**
	 * 委员会成员列表
	 */
	List<JSONObject> listCommitteeMember(JSONObject jsonObject);

	/**
	 * 统计委员会成员总数
	 */
	int countCommitteeMemberByCommittee(JSONObject jsonObject);

	/**
	 * 统计条件委员会成员的总数
	 */

	int countCommitteeMemberByCommitteeAndSearch(JSONObject jsonObject);

	/**
	 * 委员会成员列表
	 */
	List<JSONObject> listCommitteeMemberByCommittee(JSONObject jsonObject);

	/**
	 * 条件委员会成员列表
	 */
	List<JSONObject> listCommitteeMemberByCommitteeAndSearch(JSONObject jsonObject);



	/**
	 * 更新委员会成员
	 */
	int updateCommitteeMember(JSONObject jsonObject);

	/**
	 * 删除委员会成员
	 */

	int deleteCommitteeMember(JSONObject jsonObject);

	List<JSONObject> getCommitteeMemberByContent(JSONObject jsonObject);
}
