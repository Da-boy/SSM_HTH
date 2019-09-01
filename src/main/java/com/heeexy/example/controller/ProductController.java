package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.ProductService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * 查询生产订单列表
	 */
//	@RequiresPermissions("product_order:list")
	@GetMapping("/listProducts")
	public JSONObject listProduct(HttpServletRequest request) {
		System.out.println("request: " + CommonUtil.request2Json(request));
		return productService.listProduct(CommonUtil.request2Json(request));
	}

    /**
     * 搜索生产订单列表
     */
//    @RequiresPermissions("product_order:list")
    @GetMapping("/listProductBySearch")
    public JSONObject listProductBySearch(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
        return productService.listProductBySearch(CommonUtil.request2Json(request));
    }

	/**
	 * 新增生产订单
	 */
	@RequiresPermissions("product_order:add")
	@PostMapping("/addProduct")
	public JSONObject addProduct(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "product_type,sc_item_id");
		return productService.addProduct(requestJson);
	}

	/**
	 * 修改生产订单
	 */
	@RequiresPermissions("product_order:update")
	@PostMapping("/updateProduct")
	public JSONObject updateProduct(@RequestBody JSONObject requestJson) {
		return productService.updateProduct(requestJson);
	}

	/**
	 * 更新订单状态
	 */
	@RequiresPermissions("product_order:update")
	@PostMapping("/updateOrdersStatus")
	public JSONObject updateOrdersStatus(@RequestBody JSONObject requestJson) {
		return productService.updateOrdersStatus(requestJson);
	}

	/**
	 * 审核生产订单
	 */
	@RequiresPermissions("product_order:check")
	@PostMapping("/auditProduct")
	public JSONObject auditProduct(@RequestBody JSONObject requestJson) {
		return productService.auditProduct(requestJson);
	}

	/**
	 * 删除生产订单
	 */
	@RequiresPermissions("product_order:delete")
	@PostMapping("/deleteProduct")
	public JSONObject deleteProduct(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "product_order_id");
		return productService.deleteProduct(requestJson);
	}

	/**
	 * 新增入库单
	 */
	@RequiresPermissions("product_order:add")
	@PostMapping("/createEntry")
	public JSONObject createEntry(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "product_order_id");
		return productService.createEntry(requestJson);
	}

	/**
	 * 查询货品列表
	 */
//	@RequiresPermissions("product_order:list")
	@GetMapping("/listScItem")
	public JSONObject listScItem(HttpServletRequest request) {
		System.out.println("request: " + CommonUtil.request2Json(request));
		return productService.listScItem(CommonUtil.request2Json(request));
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author:bguan)
	 */
//	@RequiresPermissions("article:list")
	@PostMapping("/todoSearchByStatus")
	public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "status");
		return productService.todoSearchByStatus(requestJson);
	}
}
