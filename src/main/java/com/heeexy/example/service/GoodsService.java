package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: zandaoguang
 */
public interface GoodsService {
	/**
	 * 新增采购订单
	 */
	JSONObject addGoods(JSONObject jsonObject);

	/**
	 * 货品列表
	 */
	JSONObject listGoods(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */

	JSONObject listGoodsBySearch(JSONObject jsonObject);
	/**
	 * 更新货品信息
	 */
	JSONObject updateGoods(JSONObject jsonObject);
	/**
	 * 删除采购订单（修改订单状态为：“采购取消”）
	 */
	JSONObject deleteGoodsById(JSONObject jsonObject);

}
