package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: dengchenglong
 */
public interface OrderService {
	/**
	 * 新增Oligo订单信息
	 */
	JSONObject addOligo(JSONObject jsonObject);

	/**
	 * 修改Oligo订单信息
	 */
	JSONObject updateOligo(JSONObject jsonObject);

	/**
	 * 新增试剂盒&引物探针套装订单信息
	 */
	JSONObject addKitProbe(JSONObject jsonObject);
	/**
	 * 修改试剂盒&引物探针套装订单信息
	 */
	JSONObject updateKitProbe(JSONObject jsonObject);

	/**
	 * 新增载体订单信息
	 */
	JSONObject addCarrier(JSONObject jsonObject);

	/**
	 * 修改载体订单信息
	 */
	JSONObject updateCarrier(JSONObject jsonObject);

	/**
	 * 新增DNA探针合成订购单信息
	 */
	JSONObject addDNAProbe(JSONObject jsonObject);
	/**
	 * 修改DNA探针合成订购单信息
	 */
	JSONObject updateDNAProbe(JSONObject jsonObject);

	/**
	 * 新增FISH试剂盒&其他订购单信息
	 */
	JSONObject addFISH(JSONObject jsonObject);
	/**
	 * 修改FISH试剂盒&其他订购单信息
	 */
	JSONObject updateFISH(JSONObject jsonObject);

	/**
	 * 新增病毒订购单信息
	 */
	JSONObject addVirus(JSONObject jsonObject);
	/**
	 * 修改病毒订购单信息
	 */
	JSONObject updateVirus(JSONObject jsonObject);

	/**
	 * 新增全基因合成订购单信息
	 */
	JSONObject addWholeGene(JSONObject jsonObject);
	/**
	 * 修改全基因合成订购单信息
	 */
	JSONObject updateWholeGene(JSONObject jsonObject);


	/**
	 * 订单信息列表
	 */
	JSONObject listOrder(JSONObject jsonObject);

	/**
	 * 父订单基本信息列表
	 */
	JSONObject listMordOrder(JSONObject jsonObject);

	/**
	 * 详细订单信息列表
	 */
	JSONObject getRowDetailByID(JSONObject jsonObject);

	/**
	 * 销售经理信息列表
	 */
	JSONObject listSales(JSONObject jsonObject);

	/**
	 * 根据order_id搜索order_type
	 */
	JSONObject getOrderTypeByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关RNA Oligo内容
	 */
	JSONObject getRNAOligoByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关载体内容
	 */
	JSONObject getCarrierByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关DNA探针内容
	 */
	JSONObject getDNAProbeByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关FISH试剂盒及其他内容
	 */
	JSONObject getFISHKitByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关试剂盒引物探针套装内容
	 */
	JSONObject getKitProbeByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关病毒内容
	 */
	JSONObject getVirusByID(JSONObject jsonObject);

	/**
	 * 根据order_id搜索所有相关全基因内容
	 */
	JSONObject getWholeGeneByID(JSONObject jsonObject);

	/**
	 * 审核订单
	 */
	JSONObject checkOrder(JSONObject jsonObject);

	/**
	 * 更新订单状态
	 */
	JSONObject updateOrderStatus(JSONObject jsonObject);

	/**
	 * 删除订单信息
	 */
	JSONObject deleteOrder(JSONObject jsonObject);

//	JSONObject deleteCompany(JSONObject jsonObject);

    JSONObject updateOrderByCheck(JSONObject requestJson);

	JSONObject addexwarehouse(JSONObject requestJson);

	/**
	 * 销售人员创建设计订单操作；和二次更新订单操作
	 * @param requestJson
	 * @return
	 */
	JSONObject adddesign(JSONObject requestJson);
	JSONObject updatedesign(JSONObject requestJson);
	JSONObject updatedesigndonetime(JSONObject requestJson);

	JSONObject addproduct(JSONObject requestJson);

	/**
	 * 按状态搜索订单列表（待办事项功能）(author: bguan)
	 */
	JSONObject todoSearchByStatus(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */
	JSONObject listOrderBySearch(JSONObject jsonObject);

	/**
	 * 按列排序
	 */
	JSONObject listOrderBySort(JSONObject jsonObject);


	/**
	 * 根据各项参数搜索所有符合条件的销售子订单全字段数据（目前只使用mord_id)
	 */
	JSONObject listSubSaleOrderByParams(JSONObject jsonObject);

	JSONObject getSonOrderByfathor(JSONObject jsonObject);


//注：以下代码已移至代码ChargeoffService.java中！！（测试无误后，在交付版本中需删除）
//	/**
//	 * 销账操作
//	 */
//	JSONObject orderChargeoff(JSONObject jsonObject);
//	JSONObject listChargeoffRecord(JSONObject jsonObject);
//	//
//	JSONObject listBillOrder(JSONObject jsonObject);
//	//销账催账：检索功能
//	JSONObject listBillOrderBySearch(JSONObject jsonObject);
//	//销账催账：发送邮件短信功能，同时记录数据库
//	JSONObject doNotification(JSONObject jsonObject);
}