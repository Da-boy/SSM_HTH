package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.DesignOrderService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:
 * @description: 公司信息Controller
 */
@RestController
@RequestMapping("/designOrder")
public class DesignOrderController {

	@Autowired
	private DesignOrderService designOrderService;

	/**
	 * 查询订单列表
	 */
//	@RequiresPermissions("design_order:list")
	@GetMapping("/listDesignOrder")
	public JSONObject listArticle(HttpServletRequest request) {
		return designOrderService.listDesignOrder(CommonUtil.request2Json(request));
	}

	/**
	 * 查询父订单列表
	 */
//	@RequiresPermissions("design_order:list")
	@GetMapping("/listDesignMordOrder")
	public JSONObject listDesignMordOrder(HttpServletRequest request) {
		return designOrderService.listDesignMordOrder(CommonUtil.request2Json(request));
	}

	/*@RequiresPermissions("designOrder:list")
	@GetMapping("/listDesignOrder")
	public JSONObject listDesignOrder(HttpServletRequest request) {
		return designOrderService.listDesignOrder(CommonUtil.request2Json(request));
	}

	@RequiresPermissions("designOrder:list")
	@GetMapping("/getDesignOrderByContent")
	public JSONObject getDesignOrderByContent(HttpServletRequest request) {
		return designOrderService.getDesignOrderByContent(CommonUtil.request2Json(request));
	}
	@RequiresPermissions("designOrder:list")
	@GetMapping("/getTelephoneByContent")
	public JSONObject getTelephoneByContent(HttpServletRequest request) {
		return designOrderService.getTelephoneByContent(CommonUtil.request2Json(request));
	}
	@RequiresPermissions("designOrder:list")
	@GetMapping("/getAddressByContent")
	public JSONObject getAddressByContent(HttpServletRelistCompanyBySearchquest request) {
		return designOrderService.getAddressByContent(CommonUtil.request2Json(request));
	}
	@RequiresPermissions("designOrder:list")
	@GetMapping("/getRegisterIdByContent")
	public JSONObject getRegisterIdByContent(HttpServletRequest request) {
		return designOrderService.getRegisterIdByContent(CommonUtil.request2Json(request));
	}
	@RequiresPermissions("designOrder:list")
	@GetMapping("/getTaxpayerByContent")
	public JSONObject getTaxpayerByContent(HttpServletRequest request) {
		return designOrderService.getTaxpayerByContent(CommonUtil.request2Json(request));
	}
	@RequiresPermissions("designOrder:list")
	@GetMapping("/getOrganizationalByContent")
	public JSONObject getOrganizationalByContent(HttpServletRequest request) {
		return designOrderService.getOrganizationalByContent(CommonUtil.request2Json(request));
	}
	@RequiresPermissions("designOrder:list")
	@GetMapping("/getSocialByContent")
	public JSONObject getSocialByContent(HttpServletRequest request) {
		return designOrderService.getSocialByContent(CommonUtil.request2Json(request));
	}
	@RequiresPermissions("designOrder:list")
	@GetMapping("/getMailByContent")
	public JSONObject getMailByContent(HttpServletRequest request) {
		return designOrderService.getMailByContent(CommonUtil.request2Json(request));
	}
	@RequiresPermissions("designOrder:list")
	@GetMapping("/getFaxByContent")
	public JSONObject getFaxByContent(HttpServletRequest request) {
		return designOrderService.getFaxByContent(CommonUtil.request2Json(request));
	}

    *//**
     * 搜索公司信息列表
     *//*
    @RequiresPermissions("designOrder:list")
    @GetMapping("/listDesignOrderBySearch")
    public JSONObject listDesignOrderBySearch(HttpServletRequest request) {
        return designOrderService.listDesignOrderBySearch(CommonUtil.request2Json(request));
    }*/

	/**
	 * 新增公司信息getDesignOrderContentFixedById
	 */
	@RequiresPermissions("design_order:add")
	@PostMapping("/addDesignOrder")
	public JSONObject addDesignOrder(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "designOrder_name, telephone, address, register_id, taxpayer_identification_number, organizational_code");
		return designOrderService.addDesignOrder(requestJson);
	}
	/**
	 * 获取信息
	 */
//	@RequiresPermissions("design_order:list")
	@GetMapping("/getDesignOrderContentFixedById")
	public JSONObject getDesignOrderContentFixedById(HttpServletRequest request) {
		return designOrderService.getDesignOrderContentFixedById(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("design_order:list")
	@GetMapping("/listDesignOrderBySearch")
	public JSONObject listDesignOrderBySearch(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return designOrderService.listDesignOrderBySearch(CommonUtil.request2Json(request));
	}


	/**
	 * 修改原因
	 */
	@RequiresPermissions("design_order:update")
	@PostMapping("/updateDesignOrderMark")
	public JSONObject updateDesignOrderMark(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "design_order_id,mark");
		return designOrderService.updateDesignOrderMark(requestJson);
	}

	/**
	 * 修改设计内容
	 */
	@RequiresPermissions("design_order:update")
	@PostMapping("/updateDesignOrderDesignContent")
	public JSONObject updateDesignOrderDesignContent(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "design_order_id,design_content1,design_content2,design_content3");
		System.out.println(requestJson);
		return designOrderService.updateDesignOrderDesignContent(requestJson);
	}

/*	*//**
	 * 修改公司信息
	 *//*
	@RequiresPermissions("design_order:update")
	@PostMapping("/updateDesignOrder")
	public JSONObject updateDesignOrder(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "designOrder_name, telephone, address, register_id, taxpayer_identification_number, organizational_code");
		return designOrderService.updateDesignOrder(requestJson);
	}*/

	/**
	 * 修改设计内容
	 */
	@RequiresPermissions("design_order:update")
	@PostMapping("/updateOrdersStatusBySSOI")
	public JSONObject updateOrdersStatusBySSOI(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "sales_son_order_id,order_status");
		//System.out.println(requestJson);
		return designOrderService.updateOrdersStatusBySSOI(requestJson);
	}

	/**
	 * 按列排序
	 */
//	@RequiresPermissions("design_order:list")
	@GetMapping("/listDesignOrderBySort")
	public JSONObject listDesignOrderBySort(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return designOrderService.listDesignOrderBySort(CommonUtil.request2Json(request));
	}

	/**
	 * 删除公司信息
	 */
	@RequiresPermissions("design_order:delete")
	@PostMapping("/deleteDesignOrder")
	public JSONObject deleteDesignOrder(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "designOrder_id");
		return designOrderService.deleteDesignOrder(requestJson);
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(author:bguan)
	 */
//	@RequiresPermissions("article:list")
	@PostMapping("/todoSearchByStatus")
	public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "status");
		return designOrderService.todoSearchByStatus(requestJson);
	}
}
