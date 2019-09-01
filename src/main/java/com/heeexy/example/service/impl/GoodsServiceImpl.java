package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ArticleDao;
import com.heeexy.example.dao.GoodsDao;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.GoodsService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zandaoguang
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 新增物品
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addGoods(JSONObject jsonObject) {
		JSONObject newObject = new JSONObject();

		newObject.put("sc_item_id", jsonObject.getInteger("sc_item_id"));				//申请单号
		newObject.put("sc_item_name", jsonObject.getString("sc_item_name"));			//申请日期
		newObject.put("sc_item_type", jsonObject.getString("sc_item_type"));	//要货日期
		newObject.put("sc_item_desp", jsonObject.getString("sc_item_desp") );											//采购状态
		newObject.put("sc_item_attribute", jsonObject.getString("sc_item_attribute"));				//供应商id（关联Company表）
        newObject.put("is_gift", jsonObject.getInteger("is_gift"));	//供应商联系人
		newObject.put("is_chemicals", jsonObject.getInteger("is_chemicals"));		//申请人id（关联Staff表）
        newObject.put("is_explosive", jsonObject.getInteger("is_explosive"));	//供应商联系人
        newObject.put("is_packing", jsonObject.getInteger("is_packing"));	//供应商联系人
        newObject.put("is_consumable", jsonObject.getInteger("is_consumable"));	//供应商联系人
        newObject.put("inspection_cycle", jsonObject.getString("inspection_cycle"));	//供应商联系人
        newObject.put("sc_item_price", jsonObject.getInteger("sc_item_price"));	//供应商联系人

        goodsDao.addGoods(newObject);
		return CommonUtil.successJson();
	}

	/**
	 * 全部货品列表
	 */
	@Override
	public JSONObject listGoods(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = goodsDao.countGoods(jsonObject);
		List<JSONObject> list = goodsDao.listGoods(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	@Override
	public JSONObject listGoodsBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
//		System.out.println("jsonObject:" + jsonObject);
		int count = goodsDao.countGoodsBySearch(jsonObject);
//		System.out.println("count:" + count);
		List<JSONObject> list = goodsDao.listGoodsBySearch(jsonObject);
		System.out.print(list);
		return CommonUtil.successPage(jsonObject, list, count);
	}
	/**
	 * 更新货品信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateGoods(JSONObject jsonObject) {
		goodsDao.updateGoods(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 根据货品ID删除货品信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteGoodsById(JSONObject jsonObject) {
		goodsDao.deleteGoodsById(jsonObject);
		return CommonUtil.successJson();
	}

}
