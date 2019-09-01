package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: dengchenglong
 * @description: 订单信息Dao层
 */
public interface OrderDao {
	/**
	 * 新增Oligo订单信息
	 */
	int addOligo(JSONObject jsonObject);
	/**
	 * 修改Oligo订单信息
	 */
	int updateOligo(JSONObject jsonObject);
	/**
	 * 新增试剂盒&引物探针套装订单信息
	 */
	int addKitProbe(JSONObject jsonObject);
	/**
	 * 修改试剂盒&引物探针套装订单信息
	 */
	int updateKitProbe(JSONObject jsonObject);
	/**
	 * 新增载体订单信息
	 */
	int addCarrier(JSONObject jsonObject);
	/**
	 * 修改载体订单信息
	 */
	int updateCarrier(JSONObject jsonObject);
	/**
	 * 新增DNA探针合成订购单信息
	 */
	int addDNAProbe(JSONObject jsonObject);
	/**
	 * 修改DNA探针合成订购单信息
	 */
	int updateDNAProbe(JSONObject jsonObject);
	/**
	 * 新增FISH试剂盒&其他订购单信息
	 */
	int addFISH(JSONObject jsonObject);
	/**
	 * 修改FISH试剂盒&其他订购单信息
	 */
	int updateFISH(JSONObject jsonObject);
	/**
	 * 新增病毒订购单信息
	 */
	int addVirus(JSONObject jsonObject);
	/**
	 * 修改病毒订购单信息
	 */
	int updateVirus(JSONObject jsonObject);
	/**
	 * 新增全基因合成订购单信息
	 */
	int addWholeGene(JSONObject jsonObject);
	/**
	 * 修改全基因合成订购单信息
	 */
	int updateWholeGene(JSONObject jsonObject);
	/**
	 * 新增客户信息
	 */
	int addClient(JSONObject jsonObject);

	/**
	 * 更新客户信息
	 */
	int updateClient(JSONObject jsonObject);

	/**
	 * 获取客户姓名和手机号
	 */
	List<JSONObject> getClient_name_phone(JSONObject jsonObject);

	/**
	 * 查询销售经理ID
	 */
	int getSalesManagerID(JSONObject jsonObject);

	/**
	 * 查询客户ID
	 */
	int getClientID(JSONObject jsonObject);

	/**
	 * 查询最大的父订单ID
	 */
	int maxMord_Id(JSONObject jsonObject);
	/**
	 * 查询所有父订单ID
	 */
	int countMordByID(JSONObject jsonObject);

	/**
	 * 统计订单信息总数
	 */
	int countOrder(JSONObject jsonObject);

	/**
	 * 统计父订单基本信息总数
	 */
	int countMordOrder(JSONObject jsonObject);

	/**
	 * 订单信息列表
	 */
	List<JSONObject> listOrder(JSONObject jsonObject);

	/**
	 * 父订单基本信息列表
	 */
	List<JSONObject> listMordOrder(JSONObject jsonObject);

	/**
	 * 订单详细信息列表
	 */
	List<JSONObject> getRowDetailByID(JSONObject jsonObject);

	/**
	 * 销售经理列表
	 */
	List<JSONObject> listSales(JSONObject jsonObject);

	/**
	 * 根据order_id搜索order_type
	 */
	JSONObject getOrderTypeByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关RNA Oligo内容
	 */
	List<JSONObject> getRNAOligoByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关载体内容
	 */
	List<JSONObject> getCarrierByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关DNA探针内容
	 */
	List<JSONObject> getDNAProbeByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关FISH试剂盒及其他内容
	 */
	List<JSONObject> getFISHKitByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关试剂盒引物探针套装内容
	 */
	List<JSONObject> getKitProbeByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关病毒内容
	 */
	List<JSONObject> getVirusByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关全基因内容
	 */
	List<JSONObject> getWholeGeneByID(JSONObject jsonObject);

	/**
	 *  审核订单
	 */
	int checkOrder(JSONObject jsonObject);

	/**
	 *  更新订单状态
	 */
	int updateOrderStatus(JSONObject jsonObject);

	/**
	 * 根据sales_manager_id获取username
	 */
	String getUsernameByID(int id);

	/**
	 * 删除订单信息
	 */
	int deleteOrder(JSONObject jsonObject);

	int deleteCompany(JSONObject jsonObject);

	void updateOrderByCheck(JSONObject requestJson);

	void addexwarehouse(JSONObject requestJson);

	/**
	 * 销售人员创建设计订单操作
	 * @param requestJson
	 */
	void updatewarehouseStatus(JSONObject requestJson);
	void adddesign(JSONObject requestJson);
	void updatedesign(JSONObject requestJson);
	void updatedesigndonetime(JSONObject requestJson);

	void addproduct(JSONObject requestJson);

	/**
	 * 按状态搜索订单列表（待办事项功能）(author: bguan)
	 */
	int todoSearchByStatus(JSONObject jsonObject);
	List<JSONObject> todoSearchEventByStatus(JSONObject jsonObject);

	/**
	 * 统计条件订单的总数和列表
	 */
	int countOrderBySearch(JSONObject jsonObject);
	List<JSONObject> listOrderBySearch(JSONObject jsonObject);

	/**
	 * 按列排序
	 */
	List<JSONObject> listOrderBySort(JSONObject jsonObject);
	int countOrderBySort(JSONObject jsonObject);


	/**
	 * 根据各项参数搜索所有符合条件的销售子订单全字段数据（目前只使用mord_id)
	 */
	List<JSONObject> listSubSaleOrderByParams(JSONObject jsonObject);

	List<JSONObject> getSonOrderByfathor(JSONObject jsonObject);

	List<JSONObject> getDesignOrderList(JSONObject jsonObject);

//注：以下代码已移至代码ChargeoffDao.java中！！（测试无误后，在交付版本中需删除）
//	/**
//	 * 销账操作
//	 */
//	int addChargeoffRecord(JSONObject jsonObject);
//	int updateChargeoffRecord(JSONObject jsonObject);
//	int countChargeoffRecord(JSONObject jsonObject);
//	List<JSONObject> listChargeoffRecord(JSONObject jsonObject);
//
//	/**
//	 * 销账催账：仅列出账单状态的父订单：["已签收待支付","部分支付","已支付"]
//	 */
//	int countBillOrder(JSONObject jsonObject);
//	List<JSONObject> listBillOrder(JSONObject jsonObject);
//	/**
//	 * 销账催账：检索功能
//	 */
//	int countBillOrderBySearch(JSONObject jsonObject);
//	List<JSONObject> listBillOrderBySearch(JSONObject jsonObject);
//	int logUigeBillRecord(JSONObject jsonObject);
}