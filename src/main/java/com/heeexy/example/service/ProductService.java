package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface ProductService {
	/**
	 * 新增产品订单
	 */
	JSONObject addProduct(JSONObject jsonObject);

	/**
	 * 产品订单列表
	 */
	JSONObject listProduct(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */

	JSONObject listProductBySearch(JSONObject jsonObject);

	/**
	 * 更新产品订单
	 */
	JSONObject updateProduct(JSONObject jsonObject);

	/**
	 * 更新订单状态
	 */
	JSONObject updateOrdersStatus(JSONObject jsonObject);

	/**
	 * 审核产品订单
	 */
	JSONObject auditProduct(JSONObject jsonObject);

	/**
	 * 删除产品订单
	 */
	JSONObject deleteProduct(JSONObject jsonObject);

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
