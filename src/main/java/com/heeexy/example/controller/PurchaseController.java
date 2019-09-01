package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PurchaseService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: bguan
 * @description: 采购订单相关Controller
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	/**
	 * 查询采购订单列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listPurchase")
	public JSONObject listPurchase(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return purchaseService.listPurchase(CommonUtil.request2Json(request));
	}


	/**
	 * 查询采购订单（按照父订单查询所有子订单）(not used)
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/getPurchaseById")
	public JSONObject getPurchaseById(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return purchaseService.getPurchaseById(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("article:list")
	@GetMapping("/getPurchaseByContent")
	public JSONObject getPurchaseByContent(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return purchaseService.getPurchaseByContent(CommonUtil.request2Json(request));
	}

    /**
     * 搜索采购订单列表
     */
//    @RequiresPermissions("article:list")
    @GetMapping("/listPurchaseBySearch")
    public JSONObject listPurchaseBySearch(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return purchaseService.listPurchaseBySearch(CommonUtil.request2Json(request));
    }


	/**
	 * 搜索采购订单列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listPurchaseByOrder")
	public JSONObject listPurchaseByOrder(HttpServletRequest request) {
		return purchaseService.listPurchaseByOrder(CommonUtil.request2Json(request));
	}


	/**
	 * 搜索采购订单列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listPurchaseByOrderCreateTime")
	public JSONObject listPurchaseByOrderCreateTime(HttpServletRequest request) {
		return purchaseService.listPurchaseByOrderCreateTime(CommonUtil.request2Json(request));
	}


	/**
	 * 当搜索采购订单的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listPurchaseByContentHaveSearch")
	public JSONObject listPurchaseByContentHaveSearch(HttpServletRequest request) {
		return purchaseService.listPurchaseByContentHaveSearch(CommonUtil.request2Json(request));
	}


	/**
	 * 当搜索采购订单的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/getListByOrderCreateTimeHaveSearch")
	public JSONObject getListByOrderCreateTimeHaveSearch(HttpServletRequest request) {
		return purchaseService.getListByOrderCreateTimeHaveSearch(CommonUtil.request2Json(request));
	}

	/**
	 * 新增采购订单
	 */
	@RequiresPermissions("purchase:add")
	@PostMapping("/addPurchase")
	public JSONObject addPurchase(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "department_name, staff_name"); //TODO
		return purchaseService.addPurchase(requestJson);
	}

	/**
	 * 修改采购订单
	 */
	@RequiresPermissions("purchase:update")
	@PostMapping("/updatePurchase")
	public JSONObject updatePurchase(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "purchase_id");
		return purchaseService.updatePurchase(requestJson);
	}

	/**
	 * 删除采购订单（修改订单状态为：“采购取消”）
	 */
	@RequiresPermissions("purchase:delete")
	@PostMapping("/deletePurchase")
	public JSONObject deletePurchase(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "purchaseId,purchaseStatus");
		return purchaseService.deletePurchase(requestJson);
	}

	/**
	 *
	 * 修改订单状态为“审核通过”，“采购取消”，“待入库”，“订单中止”
	 */
	@RequiresPermissions("purchase:update")
	@PostMapping("/changeStatus")
	public JSONObject changeOrderStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "purchaseSubId, purchaseStatus");
		return purchaseService.changeOrderStatus(requestJson);
	}

	/**
	 * 增加入库项，同时更新状态为“入库待审批”
	 */
	@RequiresPermissions("purchase:warehousein")
	@PostMapping("/addWarehouseEntry")
	public JSONObject addWarehouseEntry(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "purchaseId, purchaseStatus"); //TODO
		return purchaseService.addWarehouseEntry(requestJson);
	}


	/**
	 * 根据id删除采购订单
	 */
	@RequiresPermissions("purchase:delete")
	@PostMapping("/deletePurchaseById")
	public JSONObject deletePurchaseById(@RequestBody JSONObject requestJson) {
		System.out.println("purchaseId:"+requestJson);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//		CommonUtil.hasAllRequired(requestJson, "id,content,deleteStatus");
//		return purchaseService.deletePurchase(requestJson);
		return new JSONObject();
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author:bguan)
	 */
//	@RequiresPermissions("article:list")
	@PostMapping("/todoSearchByStatus")
	public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "status");
		return purchaseService.todoSearchByStatus(requestJson);
	}


	/**
	 * 操作供应商表
	 */
	/**
	 * 查询供应商信息列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listSupplier")
	public JSONObject listSupplier(HttpServletRequest request) {
		return purchaseService.listSupplier(CommonUtil.request2Json(request));
	}

	/**
	 * 新增供应商信息
	 */
	@RequiresPermissions("purchase:update")
	@PostMapping("/addSupplier")
	public JSONObject addSupplier(@RequestBody JSONObject requestJson) {
		//CommonUtil.hasAllRequired(requestJson, "company_name, telephone, address, register_id, taxpayer_identification_number, organizational_code");
		CommonUtil.hasAllRequired(requestJson, "company_name"); //采购订单模块也有调用
		return purchaseService.addSupplier(requestJson);
	}
	/**
	 * 修改供应商信息
	 */
	@RequiresPermissions("purchase:update")
	@PostMapping("/updateSupplier")
	public JSONObject updateSupplier(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "company_name, telephone, contact");
		return purchaseService.updateSupplier(requestJson);
	}

	/**
	 * 按列排序
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listPurchaseBySort")
	public JSONObject listPurchaseBySort(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return purchaseService.listPurchaseBySort(CommonUtil.request2Json(request));
	}
}
