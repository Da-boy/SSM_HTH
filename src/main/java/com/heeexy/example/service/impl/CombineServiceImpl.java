package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CombineDao;
import com.heeexy.example.service.CombineService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CombineServiceImpl implements CombineService {

	@Autowired
	private CombineDao combineDao;



	/**
	 * 新增合成订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addCombine(JSONObject[] jsonObject) {
		combineDao.addCombine(jsonObject);
		return CommonUtil.successJson();
	}
	/**
	 * 全部产品订单列表
	 */
	@Override
	public JSONObject listCombine(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = combineDao.countCombine(jsonObject);
		List<JSONObject> list = combineDao.listCombine(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}
	/**
	 * 查询合成订单编号
	 */
	@Override
	public JSONObject getCombineBatchId(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		List<JSONObject> list = combineDao.getCombineBatchId(jsonObject);
		return CommonUtil.successPage(jsonObject, list, 1);
	}

	/**
	 * 搜索产品订单列表
	 */
	@Override
	public JSONObject listCombineBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = combineDao.countCombineBySearch(jsonObject);
		List<JSONObject> list = combineDao.listCombineBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 搜索产品订单列表
	 */
	@Override
	public JSONObject listCombineBySort(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = combineDao.countCombineBySort(jsonObject);
		List<JSONObject> list = combineDao.listCombineBySort(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}
	/**
	 * 更新产品订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateCombine(JSONObject jsonObject) {
		combineDao.updateCombine(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 更新订单状态
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOrdersStatus(JSONObject jsonObject) {
		String id = jsonObject.getString("order_id");
		if (null == id || id.length() == 0) {
			combineDao.updateCombineStatus(jsonObject);
		} else {
			combineDao.updateOrdersStatus(jsonObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 审核产品订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject auditCombine(JSONObject jsonObject) {
//		String id = jsonObject.getString("order_id");
//		if (null == id || id.length() == 0) {
//			combineDao.updateCombineStatus(jsonObject);
//		} else {
//			combineDao.updateOrdersStatus(jsonObject);
//		}
		combineDao.auditCombine(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除产品订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteCombine(JSONObject jsonObject) {
		combineDao.deleteCombine(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 货品列表
	 */
	@Override
	public JSONObject listScItem(JSONObject jsonObject) {
		int count = combineDao.countScItem(jsonObject);
		List<JSONObject> list = combineDao.listScItem(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 新增入库单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject createEntry(JSONObject jsonObject) {
		jsonObject.put("warehousing_son_order_id", CommonUtil.getCurTimestamp());
		combineDao.createEntry(jsonObject);
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
		int count = combineDao.todoSearchByStatus(jsonObject);
//		System.out.println(jsonObject.get("status").toString());
//		System.out.println("status:"+count);
		List<JSONObject> list = combineDao.todoSearchEventByStatus(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}
}
