package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface CombineService {

	/**
	 * 新增合成订单
	 */
	JSONObject addCombine(JSONObject[] jsonObject);
	/**
	 * 产品订单列表
	 */
	JSONObject listCombine(JSONObject jsonObject);

	/**
	 * 产品订单列表
	 */
	JSONObject getCombineBatchId(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */

	JSONObject listCombineBySearch(JSONObject jsonObject);

	JSONObject listCombineBySort(JSONObject jsonObject);
	/**
	 * 更新产品订单
	 */
	JSONObject updateCombine(JSONObject jsonObject);

	/**
	 * 更新订单状态
	 */
	JSONObject updateOrdersStatus(JSONObject jsonObject);

	/**
	 * 审核产品订单
	 */
	JSONObject auditCombine(JSONObject jsonObject);

	/**
	 * 删除产品订单
	 */
	JSONObject deleteCombine(JSONObject jsonObject);

	/**
	 * 货品列表
	 */
	public JSONObject listScItem(JSONObject jsonObject);

	/**
	 * 新增入库单
	 */
	public JSONObject createEntry(JSONObject jsonObject);

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
	 */
	JSONObject todoSearchByStatus(JSONObject jsonObject);
}
