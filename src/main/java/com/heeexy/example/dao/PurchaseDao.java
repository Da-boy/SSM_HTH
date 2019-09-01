package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: zandaoguang
 * @description: 采购订单Dao层
 */
public interface PurchaseDao {
	/**
	 * 新增采购订单
	 */
	int addPurchase(JSONObject jsonObject);

	/**
	 * 统计采购订单总数
	 */
	int countPurchase(JSONObject jsonObject);

	/**
	 * 统计条件采购订单的总数
	 */

	int countPurchaseBySearch(JSONObject jsonObject);

	/**
	 * 采购订单列表
	 */
	List<JSONObject> listPurchase(JSONObject jsonObject);

	/**
	 * 采购订单列表
	 */
	List<JSONObject> listPurchaseByParentId(JSONObject jsonObject);
	int countPurchaseByParentId(JSONObject jsonObject);


	/**
	 * 条件采购订单列表
	 */
	List<JSONObject> listPurchaseBySearch(JSONObject jsonObject);


	/**
	 * 排序采购订单列表
	 */
	List<JSONObject> listPurchaseByOrderAsc(JSONObject jsonObject);

	List<JSONObject> listPurchaseByContentDesc(JSONObject jsonObject);

	List<JSONObject> listPurchaseByContentHaveSearchAsc(JSONObject jsonObject);

	List<JSONObject> listPurchaseByContentHaveSearchDesc(JSONObject jsonObject);

	List<JSONObject> listPurchaseByOrderCreateTimeAsc(JSONObject jsonObject);

	List<JSONObject> listPurchaseByOrderCreateTimeDesc(JSONObject jsonObject);

	List<JSONObject> listPurchaseByOrderCreateTimeHaveSearchAsc(JSONObject jsonObject);

	List<JSONObject> listPurchaseByOrderCreateTimeHaveSearchDesc(JSONObject jsonObject);

	/**
	 * 更新采购订单
	 */
	int updatePurchaseParent(JSONObject jsonObject);
	int updatePurchase(JSONObject jsonObject);

	/**
	 * 删除采购订单
	 */
	int deletePurchase(JSONObject jsonObject);

	/**
	 * 修改订单状态为“审核通过”，“采购取消”，“待入库”，“已入库”，“订单中止”
	 */
	int changeOrderStatus(JSONObject jsonObject);
	int changeOrderStatusMessage(JSONObject jsonObject);

	/**
	 * 增加入库项
	 */
	int addWarehouseEntry(JSONObject jsonObject);

	/**
	 * 按采购父订单查询所有子订单，用于入库
	 */
	List<JSONObject> getPurchaseSonOrderByParentId(JSONObject jsonObject);




	List<JSONObject> getPurchaseByContent(JSONObject jsonObject);

//	List<JSONObject> getAllPurchase();

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
	 */
	int todoSearchByStatus(JSONObject jsonObject);
	List<JSONObject> todoSearchEventByStatus(JSONObject jsonObject);

	/**
	 * 供应商表相关
	 */
	/**
	 * 统计供应商信息总数
	 */
	int countSupplier(JSONObject jsonObject);
	List<JSONObject> listSupplier(JSONObject jsonObject);
	/**
	 * 新增供应商信息
	 */
	int addSupplier(JSONObject jsonObject);
	/**
	 * 更新供应商信息
	 */
	int updateSupplier(JSONObject jsonObject);

	/**
	 * 按列排序
	 */
	List<JSONObject> listPurchaseBySort(JSONObject jsonObject);
	int countPurchaseBySort(JSONObject jsonObject);
}
