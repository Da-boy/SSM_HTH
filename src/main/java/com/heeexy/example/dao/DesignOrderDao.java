package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author:
 * @description: 设计订单信息Dao层
 */
public interface DesignOrderDao {
	/**
	 * 新增设计订单信息
	 */
	int addDesignOrder(JSONObject jsonObject);

	/**
	 * 统计设计订单信息总数
	 */
	int countDesignOrder(JSONObject jsonObject);

	/**
	 * 统计设计父订单信息总数
	 */
	int countDesignMordOrder(JSONObject jsonObject);

	/**
	 * 更新设计订单信息
	 */
	int updateDesignOrderMark(JSONObject jsonObject);

	/**
	 * 按列排序JSONObject listDesignOrderBySort(JSONObject jsonObject);
	 */


	/**
	 * 更新设计订单信息
	 */
	int updateDesignOrderDesignContent(JSONObject jsonObject);

	/**
	 * 更新订单状态信息
	 */
	int updateOrdersStatusBySSOI(JSONObject jsonObject);
	/**
	 * 搜索
	 */
	List<JSONObject> listDesignOrderBySearch(JSONObject jsonObject);
	int countDesignOrderBySearch(JSONObject jsonObject);

	List<JSONObject> listDesignOrderBySort(JSONObject jsonObject);
	int countDesignOrderBySort(JSONObject jsonObject);

	/**
	 * 设计订单列表
	 */
	List<JSONObject> listDesignOrder(JSONObject jsonObject);

	/**
	 * 设计父订单列表
	 */
	List<JSONObject> listDesignMordOrder(JSONObject jsonObject);

	List<JSONObject> getDesignOrderContentFixedById(JSONObject jsonObject);
	/**
	 * 更新设计订单信息
	 */
	int updateDesignOrder(JSONObject jsonObject);

	/**
	 * 删除设计订单信息
	 */

	int deleteDesignOrder(JSONObject jsonObject);


	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
	 */
	int todoSearchByStatus(JSONObject jsonObject);
	List<JSONObject> todoSearchEventByStatus(JSONObject jsonObject);
}