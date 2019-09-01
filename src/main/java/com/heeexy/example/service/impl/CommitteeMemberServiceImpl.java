package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CommitteeMemberDao;
import com.heeexy.example.dao.CommitteeMemberRelationDao;
import com.heeexy.example.service.CommitteeMemberService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CommitteeMemberServiceImpl implements CommitteeMemberService {

	@Autowired
	private CommitteeMemberDao committeMemberDao;
	@Autowired
	private CommitteeMemberRelationDao committeeMemberRelationDao;

	/**
	 * 新增委员会成员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addCommitteeMember(JSONObject jsonObject) {
		System.out.println("jsonObject:"+jsonObject);
		String id = jsonObject.getString("committee_member_id");
		if (null == id || id.length() == 0) {
			committeMemberDao.addCommitteeMember(jsonObject);
			List<JSONObject> list = committeMemberDao.getCommitteeMemberIdByCertificateIdOrStaffId(jsonObject);
			jsonObject.put("committee_member_id", list.get(0).get("committee_member_id"));
			System.out.println("jsonObject:" + jsonObject);
		}
		committeeMemberRelationDao.addCommitteeMember(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 全部委员会成员列表
	 */
	@Override
	public JSONObject listCommitteeMember(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = committeMemberDao.countCommitteeMember(jsonObject);
		List<JSONObject> list = committeMemberDao.listCommitteeMember(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 全部委员会成员列表
	 */
	@Override
	public JSONObject listCommitteeMemberByCommittee(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = committeMemberDao.countCommitteeMemberByCommittee(jsonObject);
		List<JSONObject> list = committeMemberDao.listCommitteeMemberByCommittee(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 搜索委员会成员列表
	 */
	@Override
	public JSONObject listCommitteeMemberByCommitteeAndSearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = committeMemberDao.countCommitteeMemberByCommitteeAndSearch(jsonObject);
		List<JSONObject> list = committeMemberDao.listCommitteeMemberByCommitteeAndSearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 更新委员会成员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateCommitteeMember(JSONObject jsonObject) {
		committeMemberDao.updateCommitteeMember(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除委员会成员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteCommitteeMember(JSONObject jsonObject) {
		committeMemberDao.deleteCommitteeMember(jsonObject);
		committeeMemberRelationDao.deleteCommitteeMember(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除委员会成员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteMemberFromCommittee(JSONObject jsonObject) {
		committeeMemberRelationDao.deleteMemberFromCommittee(jsonObject);
		return CommonUtil.successJson();
	}

//	@Override
//	public JSONObject getCommitteeMemberByContent(JSONObject jsonObject) {
////		List<JSONObject> list = committeMemberDao.listCommitteeMember(jsonObject);
////		return CommonUtil.successPage(jsonObject, list, count);
//		List<JSONObject> list = committeMemberDao.getCommitteeMemberByContent(jsonObject);
//		System.out.println("list.size():"+list.size());
//		if(list.size() >= 1){
//			return CommonUtil.successJson("fail");
//		}
//		else{
//			return CommonUtil.successJson("success");
//		}
//
//	}
}
