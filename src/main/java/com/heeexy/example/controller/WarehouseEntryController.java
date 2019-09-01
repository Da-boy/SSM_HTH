package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WarehouseEntryService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: aguang
 * @description: 订单信息Controller
 */
@RestController
@RequestMapping("/warehouse_entry")
public class WarehouseEntryController {

	@Autowired
	private WarehouseEntryService warehouseEntryService;

	/**
	 * 查询岗位信息列表
	 */
	@RequiresPermissions("warehouse_entry:list")
	@GetMapping("/listWarehouseEntry")
	public JSONObject listWarehouseEntry(HttpServletRequest request) {
		return warehouseEntryService.listWarehouseEntry(CommonUtil.request2Json(request));
//		return CommonUtil.successJson("成功");
	}

	@RequiresPermissions("warehouse_entry:check")
	@PostMapping("/checkWarehouseEntry")
	public JSONObject checkWarehouseEntry(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "warehousing_son_order_id, warehousing_status");
		return warehouseEntryService.checkWarehouseEntry(requestJson);
//		return CommonUtil.successJson("成功");
	}

	/**
	 *
	 * 审核功能：修改订单状态为“已入库”，“入库取消”(未使用)
	 */
	@RequiresPermissions("warehouse_entry:update")
	@PostMapping("/changeStatus")
	public JSONObject changeOrderStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "warehousing_son_order_id, warehousing_status");
		return warehouseEntryService.changeOrderStatus(requestJson);
	}
	/**
	 *
	 * 删除功能：根据单号删除入库订单
	 */
	@RequiresPermissions("warehouse_entry:delete")
	@PostMapping("/deleteWarehouseById")
	public JSONObject deleteWarehouseById(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "warehousing_son_order_id");
		return warehouseEntryService.deleteWarehouseById(requestJson);
	}

	/**
	 * 搜索列表（全文检索）
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listWarehouseEntryBySearch")
	public JSONObject listWarehouseEntryBySearch(HttpServletRequest request) {
		return warehouseEntryService.listWarehouseEntryBySearch(CommonUtil.request2Json(request));
	}
	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author:bguan)
	 */
//	@RequiresPermissions("article:list")
	@PostMapping("/todoSearchByStatus")
	public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "status");
		return warehouseEntryService.todoSearchByStatus(requestJson);
	}

	/**
	 * 按列排序
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listWarehouseEntryBySort")
	public JSONObject listPurchaseBySort(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return warehouseEntryService.listWarehouseEntryBySort(CommonUtil.request2Json(request));
	}

}
