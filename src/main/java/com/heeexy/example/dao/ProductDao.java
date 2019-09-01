package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ProductDao {
	/**
	 * 新增产品订单
	 */
	int addProduct(JSONObject jsonObject);

	/**
	 * 统计产品订单总数
	 */
	int countProduct(JSONObject jsonObject);

	/**
	 * 统计条件产品订单的总数
	 */

	int countProductBySearch(JSONObject jsonObject);

	/**
	 * 产品订单列表
	 */
	List<JSONObject> listProduct(JSONObject jsonObject);

	/**
	 * 条件产品订单列表
	 */
	List<JSONObject> listProductBySearch(JSONObject jsonObject);



	/**
	 * 更新产品订单
	 */
	int updateProduct(JSONObject jsonObject);

	/**
	 * 审核产品订单
	 */
	int updateOrdersStatus(JSONObject jsonObject);
	int updateProductStatus(JSONObject jsonObject);
	int auditProductForProduct(JSONObject jsonObject);

	/**
	 * 删除产品订单
	 */

	int deleteProduct(JSONObject jsonObject);

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
