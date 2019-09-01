package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: dengchenglong
 */
public interface DesignOrderService {
	/**
	 * 新增设计订单信息
	 */
	JSONObject addDesignOrder(JSONObject jsonObject);

	/**
	 * 设计订单信息列表
	 */
	JSONObject listDesignOrder(JSONObject jsonObject);

	/**
	 * 设计父订单信息列表
	 */
	JSONObject listDesignMordOrder(JSONObject jsonObject);

	/**
	 * 获取固定信息
	 */
	JSONObject getDesignOrderContentFixedById(JSONObject jsonObject);
	/**
	 * 设计订单信息mark更新
	 */
	JSONObject updateDesignOrderMark(JSONObject jsonObject);

	/**
	 * 设计订单信息designcontent更新
	 */
	JSONObject updateDesignOrderDesignContent(JSONObject jsonObject);

	/**
	 * 更新订单状态信息
	 */
	JSONObject updateOrdersStatusBySSOI(JSONObject jsonObject);
	/**
	 * 按列排序
	 */
	JSONObject listDesignOrderBySort(JSONObject jsonObject);
	/**
	 * 搜索
	 */
	JSONObject listDesignOrderBySearch(JSONObject jsonObject);

	/**
	 * 更新设计订单信息
	 */
	JSONObject updateDesignOrder(JSONObject jsonObject);

	/**
	 * 删除设计订单信息
	 */
	JSONObject deleteDesignOrder(JSONObject jsonObject);

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
	 */
	JSONObject todoSearchByStatus(JSONObject jsonObject);
}