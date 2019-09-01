package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CompanyDao;
import com.heeexy.example.dao.OrderDao;
import com.heeexy.example.dao.WarehouseEntryDao;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.service.WarehouseEntryService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author: dengchenglong
 */
@Service
public class WarehouseEntryServiceImpl implements WarehouseEntryService {

	@Autowired
	private WarehouseEntryDao warehouseEntryDao;

	private JSONObject tempInventory = null;

	private List<JSONObject> tempInventoryList;

	long dt = 8000;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Override
	public JSONObject listWarehouseEntry(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = warehouseEntryDao.countWarehouseEntry(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = warehouseEntryDao.listWarehouseEntry(jsonObject);

		System.out.println("warehouseEntryDao:"+list);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	@Override
	public JSONObject checkWarehouseEntry(JSONObject requestJson) {
		System.out.println("req:"+requestJson);
		//warehouseEntryDao.updateWarehouseEntry(requestJson);
		warehouseEntryDao.updateWarehouseEntry2(requestJson);
		System.out.println("是否是今天"+isToday(requestJson));
		if(!isToday(requestJson)){
			System.out.println("我进来啦~");
			// 新货品，从来没添加过
			if(tempInventory == null){
				warehouseEntryDao.insertInventoryOfNull(requestJson);
			}else {
				System.out.println("resent:"+tempInventory.toJSONString());
				System.out.println("real:"+requestJson);
				System.out.println("咱道光："+tempInventory.getIntValue("end_stock_good") + requestJson.getIntValue("storage_put_num"));
				requestJson.put("inventory",tempInventory.getIntValue("inventory"));
				requestJson.put("sc_item_id",tempInventory.getIntValue("sc_item_id"));
				requestJson.put("warehouse_id",tempInventory.getIntValue("warehouse_id"));
				requestJson.put("begin_stock_good",tempInventory.getIntValue("end_stock_good") + requestJson.getIntValue("storage_put_num"));
				requestJson.put("begin_stock_bad",tempInventory.getIntValue("end_stock_bad"));
				requestJson.put("end_stock_good",tempInventory.getIntValue("end_stock_good") + requestJson.getIntValue("storage_put_num"));
				requestJson.put("end_stock_bad",tempInventory.getIntValue("end_stock_bad"));
				requestJson.put("transportation_inventory",tempInventory.getIntValue("transportation_inventory"));
				requestJson.put("outgoing_vol",tempInventory.getIntValue("outgoing_vol"));
				requestJson.put("warehousing_vol",tempInventory.getIntValue("warehousing_vol")+requestJson.getIntValue("storage_put_num"));
				System.out.println("timeIncentory:"+requestJson);
				warehouseEntryDao.insertInventory(requestJson);
			}
		}else{
			System.out.println("resent:"+tempInventory.toJSONString());
			System.out.println("real:"+requestJson);
			requestJson.put("inventory",tempInventory.getIntValue("inventory"));
			requestJson.put("sc_item_id",tempInventory.getIntValue("sc_item_id"));
			requestJson.put("warehouse_id",tempInventory.getIntValue("warehouse_id"));
			requestJson.put("begin_stock_good",tempInventory.getIntValue("end_stock_good") + requestJson.getIntValue("storage_put_num"));
			requestJson.put("begin_stock_bad",tempInventory.getIntValue("end_stock_bad"));
			requestJson.put("end_stock_good",tempInventory.getIntValue("end_stock_good") + requestJson.getIntValue("storage_put_num"));
			requestJson.put("end_stock_bad",tempInventory.getIntValue("end_stock_bad"));
			requestJson.put("transportation_inventory",tempInventory.getIntValue("transportation_inventory"));
			requestJson.put("outgoing_vol",tempInventory.getIntValue("outgoing_vol"));
			requestJson.put("warehousing_vol",tempInventory.getIntValue("warehousing_vol")+requestJson.getIntValue("storage_put_num"));
			System.out.println("timeIncentory:"+requestJson);
			warehouseEntryDao.updateInventory(requestJson);
		}
		if(requestJson.getIntValue("saleing_mord_id")>-1){
			System.out.println("222更新销售订单状态为 已入库！！");
			warehouseEntryDao.updateOrderStatus(requestJson);
		}

		return CommonUtil.successJson("成功");
	}

	private boolean isToday(JSONObject requestJson) {

		List<JSONObject> jsonObjectList = warehouseEntryDao.isToday(requestJson);
		JSONObject endWarehouse = warehouseEntryDao.getWarehouseEnd(requestJson);
//		System.out.println("endWare:"+endWarehouse.getDate("inventory_date"));
		tempInventory = endWarehouse;
		if (jsonObjectList == null){
			return false;
		}else{
			tempInventoryList = jsonObjectList;
			for(JSONObject jsonObject : jsonObjectList){
				Date currDate = jsonObject.getDate("inventory_date");
				System.out.println("sqlData:"+currDate);
				System.out.println("curr:"+new Date());
				if(TimeUtil.isSameDate(currDate,new Date())){
					return true;
				}
//				tempInventory = jsonObject;
			}
//			System.out.println("最近的记录："+sdf.format(tempInventory.getDate("inventory_date")));
			return false;
		}
	}

	/**
	 * 审核功能：修改订单状态为“已入库”，“入库取消”(未使用)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject changeOrderStatus(JSONObject jsonObject){
		warehouseEntryDao.changeOrderStatus(jsonObject);
		if(jsonObject.getString("message") != ""){
			warehouseEntryDao.changeOrderStatusMessage(jsonObject);
		}
		return CommonUtil.successJson();
	}
	/**
	 * 删除功能：删除入库订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteWarehouseById(JSONObject jsonObject){
		warehouseEntryDao.deleteWarehouseById(jsonObject);
		return CommonUtil.successJson();
	}



	/**
	 * 搜索列表（全文检索）
	 */
	@Override
	public JSONObject listWarehouseEntryBySearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = warehouseEntryDao.countWarehouseEntryBySearch(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = warehouseEntryDao.listWarehouseEntryBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
	 */
	@Override
	public JSONObject todoSearchByStatus(JSONObject jsonObject) {
		System.out.println("todoSearchByStatus:xxxxxxxxxxxxxxxx");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = warehouseEntryDao.todoSearchByStatus(jsonObject);
		System.out.println(jsonObject.get("status").toString());
		System.out.println("status:"+count);
		List<JSONObject> list = warehouseEntryDao.todoSearchEventByStatus(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 按列排序
	 */
	@Override
	public JSONObject listWarehouseEntryBySort(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = warehouseEntryDao.countWarehouseEntryBySort(jsonObject);
		List<JSONObject> list = warehouseEntryDao.listWarehouseEntryBySort(jsonObject);
		System.out.print(list);
		return CommonUtil.successPage(jsonObject, list, count);
	}

}