package com.heeexy.example.service.impl;

//import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PurchaseDao;
import com.heeexy.example.service.PurchaseService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;
import java.util.ArrayList;

//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.Workbook;
//import cn.afterturn.easypoi.excel.ExcelExportUtil;

/**
 * @author: zandaoguang
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseDao purchaseDao;

//	public String getCurTimestamp() {
//		//JSONObject resultJson = new JSONObject();
//
//		//使用Date创建日期对象
//		Date date = new Date();
//		System.out.println("当前的日期是------>"+date);
//		SimpleDateFormat curTime = new SimpleDateFormat("yyyyMMddHHmmss");
//		System.out.println("格式化后的时间------->"+curTime.format(date));
//
//		return curTime.format(date);
//	}

	/**
	 * 新增采购订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addPurchase(JSONObject jsonObject) {
		JSONObject newObject = new JSONObject();

		String timeStrId = CommonUtil.getCurTimestamp();
		System.out.println("getCurTimestamp: "+timeStrId);

		//newObject.put("purchase_mord_id", jsonObject.getString("purchase_id"));			//采购父订单号
		newObject.put("purchase_mord_id", timeStrId);
		newObject.put("application_id", jsonObject.getString("apply_id"));				//申请单号
		newObject.put("application_time", jsonObject.getString("order_time"));			//申请日期
		newObject.put("require_goods_time", jsonObject.getString("required_time"));	//要货日期
		newObject.put("purchase_status", "待审核");											//采购状态
		newObject.put("supplier_id", jsonObject.getInteger("company_id"));				//供应商id（关联Company表）
		newObject.put("supplier_contact", jsonObject.getString("supplier_contact"));	//供应商联系人
		newObject.put("application_staff_id", jsonObject.getInteger("staff_id"));		//申请人id（关联Staff表）
		newObject.put("application_dept_id", jsonObject.getInteger("department_id")); 	//申请部门id（关联Department表）

		JSONArray tableData = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tableData.size(); i++){
			newObject.put("purchase_son_order_id", timeStrId + i);									//采购子订单号
			newObject.put("goods_id", tableData.getJSONObject(i).getInteger("product_id"));	//货品id （关联表dim_sc_item的sc_item_id字段）
			//newObject.put("unit", "");															//单位
			newObject.put("purchase_num", tableData.getJSONObject(i).getInteger("quantity"));			//采购数量
			newObject.put("storage_put_num_ought", tableData.getJSONObject(i).getInteger("quantity"));//应采购数量等于采购数量
			newObject.put("storage_put_num", tableData.getJSONObject(i).getInteger("quantity"));		//实采购数量等于采购数量
			newObject.put("unit_price", tableData.getJSONObject(i).getDouble("unit_price"));			//单价
			newObject.put("sum_price", tableData.getJSONObject(i).getDouble("total_price"));			//总价(单个货品)
			newObject.put("mark", tableData.getJSONObject(i).getString("remark"));						//备注

			//System.out.println("newObject: "+newObject);
			purchaseDao.addPurchase(newObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 全部采购订单列表
	 */
	@Override
	public JSONObject listPurchase(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = purchaseDao.countPurchase(jsonObject);
		List<JSONObject> list = purchaseDao.listPurchase(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 采购订单列表（查询父订单）(not used)
	 */
	@Override
	public JSONObject getPurchaseById(JSONObject jsonObject){
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = purchaseDao.countPurchaseByParentId(jsonObject);
		List<JSONObject> list = purchaseDao.listPurchaseByParentId(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 搜索采购订单列表
	 */
	@Override
	public JSONObject listPurchaseBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = purchaseDao.countPurchaseBySearch(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = purchaseDao.listPurchaseBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 排序采购订单列表
	 */
	@Override
	public JSONObject listPurchaseByOrder(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = purchaseDao.countPurchase(jsonObject);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			 list = purchaseDao.listPurchaseByOrderAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			 list = purchaseDao.listPurchaseByContentDesc(jsonObject);
		}else{
			list = purchaseDao.listPurchase(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 当搜索采购订单的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部
	 */
	@Override
	public JSONObject listPurchaseByContentHaveSearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = purchaseDao.countPurchaseBySearch(jsonObject);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			list = purchaseDao.listPurchaseByContentHaveSearchAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			list = purchaseDao.listPurchaseByContentHaveSearchDesc(jsonObject);
		}else{
			list = purchaseDao.listPurchase(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 搜索采购订单的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码
	 */
	@Override
	public JSONObject getListByOrderCreateTimeHaveSearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = purchaseDao.countPurchaseBySearch(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = purchaseDao.listPurchaseByOrderCreateTimeHaveSearchAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			list = purchaseDao.listPurchaseByOrderCreateTimeHaveSearchDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = purchaseDao.listPurchase(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 排序采购订单列表
	 */
	@Override
	public JSONObject listPurchaseByOrderCreateTime(JSONObject jsonObject) {
		System.out.println("我是昝道广Create Time！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = purchaseDao.countPurchase(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = purchaseDao.listPurchaseByOrderCreateTimeAsc(jsonObject);
		}else {
			System.out.println("DESC..........................");
			list = purchaseDao.listPurchaseByOrderCreateTimeDesc(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 更新采购订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updatePurchase(JSONObject jsonObject) {
		purchaseDao.updatePurchaseParent(jsonObject);

		//更新子订单
		JSONObject newObject = new JSONObject();
		JSONArray tableData = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tableData.size(); i++){
			newObject.put("purchase_id", jsonObject.getString("purchase_id")); 		//采购子订单号
			newObject.put("product_id", tableData.getJSONObject(i).getInteger("product_id"));				//货品id （关联表dim_sc_item的sc_item_id字段）
			newObject.put("quantity", tableData.getJSONObject(i).getInteger("quantity"));				//应采购数量
			newObject.put("unit", tableData.getJSONObject(i).getString("unit"));						//单位
			newObject.put("unit_price", tableData.getJSONObject(i).getDouble("unit_price"));			//单价
			newObject.put("total_price", tableData.getJSONObject(i).getDouble("total_price"));			//总价(单个货品)
			newObject.put("remark", tableData.getJSONObject(i).getString("remark"));						//备注

			purchaseDao.updatePurchase(newObject);
			break;
		}

		return CommonUtil.successJson();
	}



	/**
	 * 删除采购订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deletePurchase(JSONObject jsonObject) {
		purchaseDao.deletePurchase(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 修改订单状态为“审核通过”，“采购取消”，“待入库”，“订单中止”
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject changeOrderStatus(JSONObject jsonObject){
		purchaseDao.changeOrderStatus(jsonObject);
		if(jsonObject.getString("message") != ""){
			purchaseDao.changeOrderStatusMessage(jsonObject);
		}
		return CommonUtil.successJson();
	}


	/**
	 * 增加入库项，同时更新状态为“已入库”
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addWarehouseEntry(JSONObject jsonObject) {
		String timeStrId = CommonUtil.getCurTimestamp();
		System.out.println("getCurTimestamp: "+timeStrId);

		//入库父订单ID
//		String inWarehouseEntryParentId = "P" + timeStrId;
		String inWarehouseEntryParentId = timeStrId;
		jsonObject.put("warehousingParentId", inWarehouseEntryParentId);


		List<JSONObject> list = purchaseDao.getPurchaseSonOrderByParentId(jsonObject);
		System.out.println("list.size():" + list.size());
		for(int i = 0; i < list.size(); i++){
			//入库子订单ID
			String inWarehouseEntryParentSonId = inWarehouseEntryParentId + String.format("%03d", i);
			jsonObject.put("warehousingSonId", inWarehouseEntryParentSonId);

			jsonObject.put("purchaseSonId", list.get(i).getString("purchaseSonId"));
			jsonObject.put("goodsId", list.get(i).getInteger("goodsId"));
			jsonObject.put("unit", list.get(i).getString("unit"));
			jsonObject.put("storagePutNumOught", list.get(i).getInteger("shouldPurchase"));
			jsonObject.put("storagePutNum", list.get(i).getInteger("realPurchase"));

			//System.out.println("jsonObject: "+jsonObject);
			purchaseDao.addWarehouseEntry(jsonObject);
		}

		//更新状态为“入库待审批”，
		purchaseDao.changeOrderStatus(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）
	 */
//	@Override
//	public JSONObject todoSearchByStatus2_bak(JSONObject jsonObject){
//		JSONObject newObject = new JSONObject();
//		List<JSONObject> list = new ArrayList<>();
//		//int count = 0;
//
//		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//		System.out.println("jsonObject.size(): " + jsonObject.size());
//		for(int i = 0; i < jsonObject.size(); i++){
//			System.out.println(jsonObject.get("status").toString());
//			int num = purchaseDao.todoSearchByStatus(newObject);
//			JSONObject eachObject = new JSONObject();
//			eachObject.put("status", jsonObject.get("status").toString());
//			eachObject.put("number", num);
//			list.add(eachObject);
//		}listPurchaseBySort
//		int count = list.size();
//		System.out.println("count.size: " + count);
//		System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
////		JSONObject obj = new JSONObject();
////		List<SomeClass> sList = new ArrayList<SomeClass>();
////
////		SomeClass obj1 = new SomeClass();
////		obj1.setValue("val1");
////		sList.add(obj1);
////
////		SomeClass obj2 = new SomeClass();
////		obj2.setValue("val2");
////		sList.add(obj2);
////
////		obj.put("list", sList);
//
//		return CommonUtil.successPage(jsonObject, list, count);
//	}
	/**
	 * 按状态搜索采购订单列表（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
	 */
	@Override
	public JSONObject todoSearchByStatus(JSONObject jsonObject) {
		System.out.println("todoSearchByStatus:xxxxxxxxxxxxxxxx");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = purchaseDao.todoSearchByStatus(jsonObject);
		System.out.println(jsonObject.get("status").toString());
		System.out.println("status:"+count);
		List<JSONObject> list = purchaseDao.todoSearchEventByStatus(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}


	@Override
	public JSONObject getPurchaseByContent(JSONObject jsonObject) {
//		List<JSONObject> list = purchaseDao.listPurchase(jsonObject);
//		return CommonUtil.successPage(jsonObject, list, count);
		List<JSONObject> list = purchaseDao.getPurchaseByContent(jsonObject);
		System.out.println("list.size():"+list.size());
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}

	}

	@Override
	public JSONObject downExcel(HttpServletResponse response) throws IOException {
//		List<JSONObject> list = purchaseDao.getAllPurchase();
//		System.out.println(list.toString());
//		//指定列表标题和工作表名称
//		ExportParams params = new ExportParams("采购订单信息表","采购订单");
//		Workbook workbook = ExcelExportUtil.exportExcel(params,Object.class,list);
//		response.setHeader("content-Type","application/vnd.ms-excel");
//		response.setHeader("Content-Disposition","attachment;filename="+System.currentTimeMillis()+".xls");
//		response.setCharacterEncoding("UTF-8");
//		try {
//			workbook.write(response.getOutputStream());
//			workbook.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return CommonUtil.successJson("导出成功！");

//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet("信息表");
//
//		List<JSONObject> purchaseList = purchaseDao.getAllPurchase();
//		System.out.println("purchaseList:"+purchaseList);
//		String fileName = "purchase"  + ".xls";//设置要导出的文件的名字
//		//新增数据行，并且设置单元格数据
//
//		int rowNum = 1;
//
//		String[] headers = { "id", "内容", "创建时间", "更新时间", "是否删除"};
//		//headers表示excel表中第一行的表头
//
//		HSSFRow row = sheet.createRow(0);
//		//在excel表中添加表头
//
//		for(int i=0;i<headers.length;i++){
//			HSSFCell cell = row.createCell(i);
//			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//			cell.setCellValue(text);
//		}
//
//		//在表中存放查询到的数据放入对应的列
//		for (JSONObject purchase : purchaseList) {
//			HSSFRow row1 = sheet.createRow(rowNum);
//			row1.createCell(0).setCellValue(purchase.getIntValue("id"));
//			row1.createCell(1).setCellValue(purchase.getString("content"));
//			row1.createCell(2).setCellValue(purchase.getTimestamp("create_time"));
//			row1.createCell(3).setCellValue(purchase.getTimestamp("update_time"));
//			row1.createCell(4).setCellValue(purchase.getIntValue("delete_status"));
//			rowNum++;
//		}
//
//		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//		response.flushBuffer();
//		workbook.write(response.getOutputStream());
		return CommonUtil.successJson("导出成功！");
	}

	/**
	 * 供应商表相关
	 */
	/**
	 * 全部供应商信息列表
	 */
	@Override
	public JSONObject listSupplier(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = purchaseDao.countSupplier(jsonObject);
		List<JSONObject> list = purchaseDao.listSupplier(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 新增供应商信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addSupplier(JSONObject jsonObject) {
		purchaseDao.addSupplier(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 更新供应商信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateSupplier(JSONObject jsonObject) {
		purchaseDao.updateSupplier(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 按列排序
	 */
	@Override
	public JSONObject listPurchaseBySort(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = purchaseDao.countPurchaseBySort(jsonObject);
		List<JSONObject> list = purchaseDao.listPurchaseBySort(jsonObject);
		System.out.print(list);
		return CommonUtil.successPage(jsonObject, list, count);
	}
}
