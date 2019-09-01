package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface WarehouseOutDao {
	
	/**
	 * 委员会列表
	 */
	List<JSONObject> listWarehouseOut(JSONObject jsonObject);
	
	
	/**
	 * 统计委员会总数
	 */
	int countWarehouseOut(JSONObject jsonObject);

	int updateWarehouseOutAudit(JSONObject jsonObject);
	
	int updateWarehouseOutAuditBatch(JSONObject jsonObject);

	List<JSONObject> listWarehouseOutBySearch(JSONObject jsonObject);
	

	int countWarehouseOutBySearch(JSONObject jsonObject);
	
	JSONObject selectLatestInventory(JSONObject jsonObject);
	
	int insertInventory(JSONObject jsonObject);
	
	JSONObject findWarehouseOutById(JSONObject jsonObject);
	
	int updateInventoryWhout(JSONObject o);
	
	int updateWarehouseOutStatus(JSONObject o);
//	
//	/**
//	 * 新增委员会
//	 */
//	int addCommittee(JSONObject jsonObject);
//
//	/**
//	 * 统计委员会总数
//	 */
//	int countCommittee(JSONObject jsonObject);
//
//	/**
//	 * 统计条件委员会的总数
//	 */
//
//	int countCommitteeBySearch(JSONObject jsonObject);
//
//	/**
//	 * 委员会列表
//	 */
//	List<JSONObject> listCommittee(JSONObject jsonObject);
//
//	/**
//	 * 条件委员会列表
//	 */
//	List<JSONObject> listCommitteeBySearch(JSONObject jsonObject);
//
//
//
//	/**
//	 * 更新委员会
//	 */
//	int updateCommittee(JSONObject jsonObject);
//
//	/**
//	 * 删除委员会
//	 */
//
//	int deleteCommittee(JSONObject jsonObject);

//	List<JSONObject> getCommitteeByContent(JSONObject jsonObject);

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
	 */
	int todoSearchByStatus(JSONObject jsonObject);
	List<JSONObject> todoSearchEventByStatus(JSONObject jsonObject);
}
