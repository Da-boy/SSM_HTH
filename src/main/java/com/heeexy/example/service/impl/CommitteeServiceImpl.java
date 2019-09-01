package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CommitteeDao;
import com.heeexy.example.dao.CommitteeMemberRelationDao;
import com.heeexy.example.service.CommitteeService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommitteeServiceImpl implements CommitteeService {

	@Autowired
	private CommitteeDao committeeDao;
	@Autowired
	private CommitteeMemberRelationDao committeeMemberRelationDao;

	/**
	 * 新增委员会
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addCommittee(JSONObject jsonObject) {
		committeeDao.addCommittee(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 全部委员会列表
	 */
	@Override
	public JSONObject listCommittee(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = committeeDao.countCommittee(jsonObject);
		List<JSONObject> list = committeeDao.listCommittee(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 搜索委员会列表
	 */
	@Override
	public JSONObject listCommitteeBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = committeeDao.countCommitteeBySearch(jsonObject);
		List<JSONObject> list = committeeDao.listCommitteeBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 更新委员会
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateCommittee(JSONObject jsonObject) {
		committeeDao.updateCommittee(jsonObject);
		return CommonUtil.successJson();
	}



	/**
	 * 删除委员会
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteCommittee(JSONObject jsonObject) {
		committeeDao.deleteCommittee(jsonObject);
		committeeMemberRelationDao.deleteCommittee(jsonObject);
		return CommonUtil.successJson();
	}

//	@Override
//	public JSONObject getCommitteeByContent(JSONObject jsonObject) {
////		List<JSONObject> list = committeeDao.listCommittee(jsonObject);
////		return CommonUtil.successPage(jsonObject, list, count);
//		List<JSONObject> list = committeeDao.getCommitteeByContent(jsonObject);
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
