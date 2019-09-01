package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.GoodsService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zandaoguang
 * @description: 登录相关Controller
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	/**
	 * 查询列表
	 */
	//@RequiresPermissions("goods:list")
//	@RequiresPermissions("article:list")
	@GetMapping("/listGoods")
	public JSONObject listGoods(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return goodsService.listGoods(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("article:list")
	@GetMapping("/listGoodsBySearch")
	public JSONObject getWarehouseOutByContent(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return goodsService.listGoodsBySearch(CommonUtil.request2Json(request));
	}

	/**
	 * 新增货品
	 */
	@RequiresPermissions("goods:add")
	@PostMapping("/addGoods")
	public JSONObject addPurchase(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "sc_item_name"); //TODO
		return goodsService.addGoods(requestJson);
	}

	/**
	 * 修该货品信息
	 */
	@RequiresPermissions("goods:update")
	@PostMapping("/updateGoods")
	public JSONObject updateGoods(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "sc_item_name");
		return goodsService.updateGoods(requestJson);
	}

	/**
	 * 根据id删除货物
	 */
	@RequiresPermissions("goods:delete")
	@PostMapping("/deleteGoodsById")
	public JSONObject deleteGoodsById(@RequestBody JSONObject requestJson) {
		System.out.println("sc_item_id:"+requestJson);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		CommonUtil.hasAllRequired(requestJson, "sc_item_id");
		return goodsService.deleteGoodsById(requestJson);
	}
}
