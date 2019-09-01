package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WarehouseOutService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zandaoguang
 * @description: 文章相关Controller
 */
@RestController
@RequestMapping("/whout")
public class WarehouseOutController {

	@Autowired
	private WarehouseOutService whoutServ;

	/**
	 * 查询文章列表
	 */
//	@RequiresPermissions("exwarehouse:list")
	@GetMapping("/listWhout")
	public JSONObject listWarehouseOut(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return whoutServ.listWarehouseOut(CommonUtil.request2Json(request));
	}
	
	@RequiresPermissions("exwarehouse:check")
	@GetMapping("/audit")
	public JSONObject AuditWarehouseOut(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return whoutServ.updateAuditStatus(CommonUtil.request2Json(request));
	}
	
//	@RequiresPermissions("exwarehouse:list")
	@GetMapping("/listWarehouseOutBySearch")
	public JSONObject getWarehouseOutByContent(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return whoutServ.listWarehouseOutBySearch(CommonUtil.request2Json(request));
	}

	@RequiresPermissions("exwarehouse:warehouseout")
	@GetMapping("/optrWarehouseOut")
	public JSONObject WarehouseOutOprt(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return whoutServ.updateWarehouseOut(CommonUtil.request2Json(request));
	}
	
	@RequiresPermissions("exwarehouse:update")
	@GetMapping("/auditBatch")
	public JSONObject AuditWarehouseOutBatchOptr(HttpServletRequest request) {
		System.out.println("This is auditBatch");
		System.out.println(CommonUtil.request2Json(request));
		return whoutServ.updateAuditStatusBatch(CommonUtil.request2Json(request));
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author:bguan)
	 */
//	@RequiresPermissions("article:list")
	@PostMapping("/todoSearchByStatus")
	public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "status");
		return whoutServ.todoSearchByStatus(requestJson);
	}
}
