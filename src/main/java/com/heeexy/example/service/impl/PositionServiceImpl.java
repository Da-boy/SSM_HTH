package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PositionDao;
import com.heeexy.example.service.PositionService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author: dengchenglong
 */
@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionDao positionDao;

	/**
	 * 新增岗位信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addPosition(JSONObject jsonObject) {
		positionDao.addPosition(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 全部岗位信息列表
	 */
	@Override
	public JSONObject listPosition(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = positionDao.countPosition(jsonObject);
		List<JSONObject> list = positionDao.listPosition(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	@Override
	public JSONObject getPositionByContent(JSONObject jsonObject) {
		List<JSONObject> list = positionDao.getPositionByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}

	/**
	 * 搜索岗位信息列表
	 */
	@Override
	public JSONObject listPositionBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = positionDao.countPositionBySearch(jsonObject);
		List<JSONObject> list = positionDao.listPositionBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 更新岗位信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updatePosition(JSONObject jsonObject) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jsonObject.put("update_time", df.format(new Date()));
		positionDao.updatePosition(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除岗位信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deletePosition(JSONObject jsonObject) {
		positionDao.deletePosition(jsonObject);
		return CommonUtil.successJson();
	}
}