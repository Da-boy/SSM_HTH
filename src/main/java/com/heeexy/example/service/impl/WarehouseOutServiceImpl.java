package com.heeexy.example.service.impl;

//import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ArticleDao;
import com.heeexy.example.dao.OrderDao;
import com.heeexy.example.dao.WarehouseOutDao;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.WarehouseOutService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.TimeUtil;
import com.heeexy.example.util.constants.Constants;
import com.heeexy.example.util.constants.ErrorEnum;

//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import cn.afterturn.easypoi.excel.ExcelExportUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: zandaoguang
 */
@Service
public class WarehouseOutServiceImpl implements WarehouseOutService {

	@Autowired
	private WarehouseOutDao whoutDao;
	
	@Autowired
	private OrderDao orderDao;

	/**
	 * 全部文章列表
	 */
	@Override
	public JSONObject listWarehouseOut(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:" + jsonObject);
		int count = whoutDao.countWarehouseOut(jsonObject);
		List<JSONObject> list = whoutDao.listWarehouseOut(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	@Override
	public JSONObject addArticle(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject updateAuditStatus(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		int count = whoutDao.updateWarehouseOutAudit(jsonObject);		
		jsonObject.put("saling_son_order_id", whoutDao.findWarehouseOutById(jsonObject).get("saling_son_order_id"));
		System.out.println("bugs:"+jsonObject);
		orderDao.updatewarehouseStatus(jsonObject);
		return CommonUtil.successJson();
	}
	
	@Override
	public JSONObject updateAuditStatusBatch(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		List list = new ArrayList<Integer>();
		for (String s: jsonObject.getString("exIds").split(",")) {
			list.add(Integer.parseInt(s));
		}
		jsonObject.put("exIds", list);
		
		System.out.println(jsonObject);
		int count = whoutDao.updateWarehouseOutAuditBatch(jsonObject);
		System.out.println("批量审核数量:"+count);

		JSONObject resultJson = new JSONObject();

		resultJson.put("code", 100);
		resultJson.put("msg", Constants.SUCCESS_MSG);
//		resultJson.put("info", "共有 " + count + "个出库子订单通过审核");
		resultJson.put("info", "批量审核已完成");
		return resultJson;
//		return CommonUtil.successJson();
	}

	@Override
	public JSONObject listWarehouseOutBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
//		System.out.println("jsonObject:" + jsonObject);
		int count = whoutDao.countWarehouseOutBySearch(jsonObject);
//		System.out.println("count:" + count);
		List<JSONObject> list = whoutDao.listWarehouseOutBySearch(jsonObject);
		System.out.print(list);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	@Override
	public JSONObject updateWarehouseOut(JSONObject jsonObject) {
		System.out.println("jsonObject:"+jsonObject);
		String exwarehouseId = jsonObject.getString("exId");
		String exwarehouse_son_order_id = jsonObject.getString("exwarehouse_son_order_id");
		JSONObject obj = new JSONObject();
		
		JSONObject exJson = whoutDao.findWarehouseOutById(jsonObject);
	
//		System.out.println("exJSON:\t"+exJson);
		String itemId = exJson.getString("goods_id");
		String whId = exJson.getString("warehouse_id");
		
		obj.put("itemId", itemId);
		obj.put("whId", whId);
//		System.out.println("jsonObject:"+jsonObject);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("code", 100);
		resultJson.put("msg", Constants.SUCCESS_MSG);
		
		
		JSONObject inv = whoutDao.selectLatestInventory(obj);
		if(inv==null) {
			//Inventory无数据，插入第一条
			inv = new JSONObject();
			inv.put("sc_item_id", itemId);
			inv.put("warehouse_id", whId);
			inv.put("inventory_date", new Date());
			inv.put("begin_stock_good", 0);
			inv.put("begin_stock_bad", 0);
			inv.put("end_stock_good", 0);
			inv.put("end_stock_bad", 0);
			inv.put("transportation_inventory", 0);
			inv.put("outgoing_vol", 0);
			inv.put("warehousing_vol",0);
			
			whoutDao.insertInventory(inv);
			
			resultJson.put("info", "failure");
			return resultJson;
		}
		else {
			//inventory中有数据，判断是否是今天
			System.out.println(inv);
			Date invdate = inv.getDate("inventory_date");
			Date today = new Date();
			if (!TimeUtil.isSameDate(invdate, today)) {
				// another day
				inv.remove("inventory");
				inv.put("inventory_date", new Date());
				inv.put("outgoing_vol", 0);
				inv.put("warehousing_vol", 0);

				whoutDao.insertInventory(inv);
			}
		}
		
		inv = whoutDao.selectLatestInventory(obj);

		int end_stock_good = inv.getInteger("end_stock_good");
		int out_num = exJson.getInteger("storage_put_num");
		System.out.println("left:\t" + end_stock_good + "\t  out num:" + out_num);

		if (out_num <= end_stock_good) {
			// 可以出库
			//修改库存
			inv.put("end_stock_good", end_stock_good - out_num);
			inv.put("outgoing_vol", inv.getInteger("outgoing_vol") + out_num);
			whoutDao.updateInventoryWhout(inv);
			
			//修改出库状态
			JSONObject updateWarehouseOutStatus = new JSONObject();
			updateWarehouseOutStatus.put("status", "已出库待签收");
			updateWarehouseOutStatus.put("exId", exwarehouseId);
			updateWarehouseOutStatus.put("exwarehouse_son_order_id",exwarehouse_son_order_id);
			whoutDao.updateWarehouseOutStatus(updateWarehouseOutStatus);
			
			updateWarehouseOutStatus.put("saling_son_order_id", whoutDao.findWarehouseOutById(jsonObject).get("saling_son_order_id"));
			orderDao.updatewarehouseStatus(updateWarehouseOutStatus);
			
			resultJson.put("info", "success");

			return resultJson;
		} else {
			resultJson.put("info", "failure");
//				return CommonUtil.errorJson(ErrorEnum.E_OUT_OF_STOCK);
			return resultJson;
		}
	}


	/**
	 * 按状态搜索采购订单列表（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
	 */
	@Override
	public JSONObject todoSearchByStatus(JSONObject jsonObject) {
//		System.out.println("todoSearchByStatus:xxxxxxxxxxxxxxxx");
		CommonUtil.fillPageParam(jsonObject);
//		System.out.println("jsonObject:"+jsonObject);
		int count = whoutDao.todoSearchByStatus(jsonObject);
//		System.out.println(jsonObject.get("status").toString());
//		System.out.println("status:"+count);
		List<JSONObject> list = whoutDao.todoSearchEventByStatus(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}

}
