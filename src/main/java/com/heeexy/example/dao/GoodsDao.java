package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: zandaoguang
 * @description: 文章Dao层
 */
public interface GoodsDao {

	/**
	 * 新增物品
	 */
	int addGoods(JSONObject jsonObject);

	/**
	 * 统计货品总数
	 */
	int countGoods(JSONObject jsonObject);

	/**
	 * 货品列表
	 */
	List<JSONObject> listGoods(JSONObject jsonObject);


	List<JSONObject> listGoodsBySearch(JSONObject jsonObject);
	int countGoodsBySearch(JSONObject jsonObject);
	/**
	 * 更新货品信息
	 */
	int updateGoods(JSONObject jsonObject);

	/**
	 * 根据货品ID删除货品信息
	 */
	int deleteGoodsById(JSONObject jsonObject);

}
