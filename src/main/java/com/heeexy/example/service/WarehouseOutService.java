package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;


/**
 * @author: zandaoguang
 */
public interface WarehouseOutService {
	/**
	 * 新增文章
	 */
	JSONObject addArticle(JSONObject jsonObject);

	/**
	 * 列表
	 */
	JSONObject listWarehouseOut(JSONObject jsonObject);

	/**
	 * update 
	 */

	JSONObject updateAuditStatus(JSONObject jsonObject);
	
	JSONObject updateAuditStatusBatch(JSONObject jsonObject);
	/**
	 * 搜索列表
	 */
	JSONObject listWarehouseOutBySearch(JSONObject jsonObject);
	
	JSONObject updateWarehouseOut(JSONObject jsonObject);

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
	 */
	JSONObject todoSearchByStatus(JSONObject jsonObject);
}
