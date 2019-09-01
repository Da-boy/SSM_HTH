package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.CombineService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/combine")
public class CombineController {

	@Autowired
	private CombineService combine;

	/**
	 * 查询生产订单列表
	 */
//	@RequiresPermissions("combine:list")
	@GetMapping("/listCombine")
	public JSONObject listCombine(HttpServletRequest request) {
		System.out.println("request: " + CommonUtil.request2Json(request));
		return combine.listCombine(CommonUtil.request2Json(request));
	}
	/**
	 * 查询生产订单编号
	 */
//	@RequiresPermissions("combine:list")
	@GetMapping("/getCombineBatchId")
	public JSONObject getCombineBatchId(HttpServletRequest request) {
		System.out.println("request: " + CommonUtil.request2Json(request));
		return combine.getCombineBatchId(CommonUtil.request2Json(request));
	}
	/**
	 * 搜索生产订单列表
	 */
//	@RequiresPermissions("combine:list")
	@GetMapping("/listCombineBySearch")
	public JSONObject listCombineBySearch(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return combine.listCombineBySearch(CommonUtil.request2Json(request));
	}
	/**
	 * 按列排序
	 */
//	@RequiresPermissions("combine:list")
	@GetMapping("/listCombineBySort")
	public JSONObject listCombineBySort(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return combine.listCombineBySort(CommonUtil.request2Json(request));
	}
	/**
	 * 新增合成订单
	 */
	@RequiresPermissions("combine:add")
	@PostMapping("/addCombine")
	public JSONObject addCombine(@RequestBody JSONObject[] requestJson) {
		//CommonUtil.hasAllRequired(requestJson, "id");
		System.out.println(requestJson);
		return combine.addCombine(requestJson);
	}

	/**
	 * 修改生产订单
	 */
	@RequiresPermissions("combine:update")
	@PostMapping("/updateCombine")
	public JSONObject updateCombine(@RequestBody JSONObject requestJson) {
		return combine.updateCombine(requestJson);
	}

	/**
	 * 更新订单状态
	 */
	@RequiresPermissions("combine:update")
	@PostMapping("/updateOrdersStatus")
	public JSONObject updateOrdersStatus(@RequestBody JSONObject requestJson) {
		return combine.updateOrdersStatus(requestJson);
	}

	/**
	 * 审核合成订单
	 */
	@RequiresPermissions("combine:update")
	@PostMapping("/auditCombine")
	public JSONObject auditCombine(@RequestBody JSONObject requestJson) {
		System.out.println("combine/auditCombine"+requestJson);
		return combine.auditCombine(requestJson);
	}

	/**
	 * 删除合成订单
	 */
	@RequiresPermissions("combine:delete")
	@PostMapping("/deleteCombine")
	public JSONObject deleteCombine(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "id");
		return combine.deleteCombine(requestJson);
	}


	/**
	 * 新增入库单
	 */
	@RequiresPermissions("product_order:add")
	@PostMapping("/createEntry")
	public JSONObject createEntry(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "product_order_id");
		return combine.createEntry(requestJson);
	}

	/**
	 * 查询货品列表
	 */
//	@RequiresPermissions("product_order:list")
	@GetMapping("/listScItem")
	public JSONObject listScItem(HttpServletRequest request) {
		System.out.println("request: " + CommonUtil.request2Json(request));
		return combine.listScItem(CommonUtil.request2Json(request));
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author:bguan)
	 */
//	@RequiresPermissions("article:list")
	@PostMapping("/todoSearchByStatus")
	public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "status");
		return combine.todoSearchByStatus(requestJson);
	}
}
