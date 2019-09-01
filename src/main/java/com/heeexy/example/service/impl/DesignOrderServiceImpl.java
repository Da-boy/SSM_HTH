package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.DesignOrderDao;
import com.heeexy.example.service.DesignOrderService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:
 */
@Service
public class DesignOrderServiceImpl implements DesignOrderService {

	@Autowired
	private DesignOrderDao designOrderDao;

	/**
	 * 新增设计订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addDesignOrder(JSONObject jsonObject) {
		designOrderDao.addDesignOrder(jsonObject);
		return CommonUtil.successJson();
	}
	/**
	 * 全部设计订单信息列表
	 */
	@Override
	public JSONObject listDesignOrder(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = designOrderDao.countDesignOrder(jsonObject);
		List<JSONObject> list = designOrderDao.listDesignOrder(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 全部设计父订单信息列表
	 */
	@Override
	public JSONObject listDesignMordOrder(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = designOrderDao.countDesignMordOrder(jsonObject);
		List<JSONObject> list = designOrderDao.listDesignMordOrder(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}
	/**
	 * 按列排序
	 */
	@Override
	public JSONObject listDesignOrderBySort(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = designOrderDao.countDesignOrderBySort(jsonObject);
		List<JSONObject> list = designOrderDao.listDesignOrderBySort(jsonObject);
//		System.out.print(list);
		return CommonUtil.successPage(jsonObject, list, count);
	}
	/**
	 * 获取信息content
	 */
	@Override
	public JSONObject getDesignOrderContentFixedById(JSONObject jsonObject) {

		List<JSONObject> list = designOrderDao.getDesignOrderContentFixedById(jsonObject);
//		System.out.println(list.size());
//		System.out.println(list);
		if(list.size() == 1){
			//JSONObject result = successJson();
			return CommonUtil.successPage1(list, 1);
		}
		else{
			return CommonUtil.successJson("fail");
		}
	}
	/**
	 * 更新设计订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateDesignOrder(JSONObject jsonObject) {
		designOrderDao.updateDesignOrder(jsonObject);
		return CommonUtil.successJson();
	}
	/**
	 * 搜索
	 */
	@Override
	public JSONObject listDesignOrderBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
//		System.out.println("jsonObject:" + jsonObject);
		int count = designOrderDao.countDesignOrderBySearch(jsonObject);
//		System.out.println("count:" + count);
		List<JSONObject> list = designOrderDao.listDesignOrderBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}
	/**
	 * 更新设计订单信息mark
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateDesignOrderMark(JSONObject jsonObject) {
		designOrderDao.updateDesignOrderMark(jsonObject);
		return CommonUtil.successJson();
	}
	/**
	 * 更新设计订单信息mark
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateDesignOrderDesignContent(JSONObject jsonObject) {
		designOrderDao.updateDesignOrderDesignContent(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 更新订单状态updateOrdersStatusBySSOI
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOrdersStatusBySSOI(JSONObject jsonObject) {
		designOrderDao.updateOrdersStatusBySSOI(jsonObject);
		return CommonUtil.successJson();
	}
	/**
	 * 删除岗位信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteDesignOrder(JSONObject jsonObject) {
		designOrderDao.deleteDesignOrder(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
	 */
	@Override
	public JSONObject todoSearchByStatus(JSONObject jsonObject) {
//		System.out.println("todoSearchByStatus:xxxxxxxxxxxxxxxx");
		CommonUtil.fillPageParam(jsonObject);
//		System.out.println("jsonObject:"+jsonObject);
		int count = designOrderDao.todoSearchByStatus(jsonObject);
//		System.out.println(jsonObject.get("status").toString());
//		System.out.println("status:"+count);
		List<JSONObject> list = designOrderDao.todoSearchEventByStatus(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}
}