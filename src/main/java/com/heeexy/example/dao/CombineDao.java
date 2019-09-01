package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CombineDao {

	/**
	 * 新增合成订单
	 */
	int addCombine(JSONObject[] jsonObject);
	/**
	 * ，查询合成订单编号
	 */
	List<JSONObject> getCombineBatchId(JSONObject jsonObject);

	/**
	 * 统计产品订单总数
	 */
	int countCombine(JSONObject jsonObject);
	int auditCombine(JSONObject jsonObject);
	/**
	 * 统计条件产品订单的总数
	 */

	int countCombineBySearch(JSONObject jsonObject);

	int countCombineBySort(JSONObject jsonObject);
	/**
	 * 产品订单列表
	 */
	List<JSONObject> listCombine(JSONObject jsonObject);


	List<JSONObject> listCombineBySort(JSONObject jsonObject);
	/**
	 * 条件产品订单列表
	 */
	List<JSONObject> listCombineBySearch(JSONObject jsonObject);



	/**
	 * 更新产品订单
	 */
	int updateCombine(JSONObject jsonObject);

	/**
	 * 审核产品订单
	 */
	int updateOrdersStatus(JSONObject jsonObject);
	int updateCombineStatus(JSONObject jsonObject);
	int auditCombineForCombine(JSONObject jsonObject);

	/**
	 * 删除产品订单
	 */

	int deleteCombine(JSONObject jsonObject);

	/**
	 * 新增入库单
	 */
	int createEntry(JSONObject jsonObject);

	/**
	 * 统计货品总数
	 */
	int countScItem(JSONObject jsonObject);

	/**
	 * 货品列表
	 */
	List<JSONObject> listScItem(JSONObject jsonObject);

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
	 */
	int todoSearchByStatus(JSONObject jsonObject);
	List<JSONObject> todoSearchEventByStatus(JSONObject jsonObject);
}
