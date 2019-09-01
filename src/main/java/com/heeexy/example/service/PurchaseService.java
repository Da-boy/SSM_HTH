package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: zandaoguang
 */
public interface PurchaseService {
	/**
	 * 新增采购订单
	 */
	JSONObject addPurchase(JSONObject jsonObject);

	/**
	 * 采购订单列表
	 */
	JSONObject listPurchase(JSONObject jsonObject);

	/**
	 * 采购订单列表（查询父订单）(not used)
	 */
	JSONObject getPurchaseById(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */

	JSONObject listPurchaseBySearch(JSONObject jsonObject);

	/**
	 * 按照内容排序列表
	 */
	JSONObject listPurchaseByOrder(JSONObject jsonObject);

	/**
	 * 当搜索采购订单的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部，按照内容排序列表
	 */
	JSONObject listPurchaseByContentHaveSearch(JSONObject jsonObject);



	/**
	 * 当搜索采购订单的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码
	 */
	JSONObject getListByOrderCreateTimeHaveSearch(JSONObject jsonObject);
	/**
	 * 按照创建时间排序列表，默认搜索全部
	 */
	JSONObject listPurchaseByOrderCreateTime(JSONObject jsonObject);

	/**
	 * 更新采购订单
	 */
	JSONObject updatePurchase(JSONObject jsonObject);

	/**
	 * 删除采购订单（修改订单状态为：“采购取消”）
	 */
	JSONObject deletePurchase(JSONObject jsonObject);

	/**
	 * 修改订单状态为“审核通过”，“采购取消”，“待入库”，“订单中止”
	 */
	JSONObject changeOrderStatus(JSONObject jsonObject);

	/**
	 * 增加入库项，同时更新状态为“入库待审批”
	 */
	JSONObject addWarehouseEntry(JSONObject jsonObject);



	JSONObject getPurchaseByContent(JSONObject request2Json);

	JSONObject downExcel(HttpServletResponse httpServletResponse) throws IOException;

//	List<JSONObject> getAllPurchase();

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
	 */
	JSONObject todoSearchByStatus(JSONObject jsonObject);

	/**
	 * 供应商表相关
	 */
	/**
	 * 供应商信息列表
	 */
	JSONObject listSupplier(JSONObject jsonObject);
	/**
	 * 新增供应商信息
	 */
	JSONObject addSupplier(JSONObject jsonObject);
	/**
	 * 更新供应商信息
	 */
	JSONObject updateSupplier(JSONObject jsonObject);


	/**
	 * 按列排序
	 */
	JSONObject listPurchaseBySort(JSONObject jsonObject);
}
