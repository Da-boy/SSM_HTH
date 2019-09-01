package com.heeexy.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.DeliveryService;

/**
 * @author: lingling
 * @description: 物流信息Controller
 */

import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:
 * @description: 物流订单相关Controller
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;

	/**
	 * 物流
	 */
//	@RequiresPermissions("delivery:list")
	@GetMapping("/sfGetById")
	public JSONObject sfGetById(HttpServletRequest request) {
		JSONObject requestJson=CommonUtil.request2Json(request);
		System.out.println("sfGetById -----requestJson:"+requestJson);
//		CommonUtil.hasAllRequired(requestJson, "type");
		String type=requestJson.get("d_type").toString();
//
		String sfXML="";
		String url=requestJson.get("d_url").toString();
		String clientCode=requestJson.get("clientCode").toString();
		String checkword=requestJson.get("checkword").toString();
		if(type.equals("1")){
			sfXML=deliveryService.getOrderServiceRequestXml(requestJson);
		}
		System.out.println(sfXML);
		String resultXML=deliveryService.callSf(sfXML,url,clientCode,checkword);
		System.out.println(resultXML);
		JSONObject result=deliveryService.getOrderServiceRequestXmlResult(resultXML);
		List<JSONObject> list=new ArrayList<JSONObject>();
		list.add(result);
//		System.out.println(resultXML);
//		return CommonUtil.successJson("fail");
		return CommonUtil.successPage1(list, 1);
	}


//	@RequiresPermissions("delivery:list")
	@GetMapping("/sfSearchById1")
	public JSONObject sfSearchById1(HttpServletRequest request) {
		JSONObject requestJson=CommonUtil.request2Json(request);
		System.out.println("requestJson:"+requestJson);
//		CommonUtil.hasAllRequired(requestJson, "type");
		String type=requestJson.get("type").toString();
//
		String sfXML="";
		String url=requestJson.get("url").toString();
		String clientCode=requestJson.get("clientCode").toString();
		String checkword=requestJson.get("checkword").toString();
		if(type.equals("2")){
			sfXML=deliveryService.getOrderSearchServiceRequestXml(requestJson);
		}
		System.out.println(sfXML);
		String resultXML=deliveryService.callSf(sfXML,url,clientCode,checkword);
		System.out.println("searchj:"+resultXML);
		JSONObject result=deliveryService.getOrderSearchServiceRequestXmlResult(resultXML);
		List<JSONObject> list=new ArrayList<JSONObject>();
		list.add(result);
//		System.out.println(resultXML);
//		return CommonUtil.successJson("fail");
		return CommonUtil.successPage1(list, 1);
	}

//	@RequiresPermissions("delivery:list")
	@GetMapping("/sfSearchById")
	public JSONObject sfSearchById(HttpServletRequest request) {
		JSONObject requestJson=CommonUtil.request2Json(request);
		System.out.println("requestJson:"+requestJson);
//		CommonUtil.hasAllRequired(requestJson, "type");
		String type=requestJson.get("type").toString();
//
		String sfXML="";
		String url=requestJson.get("url").toString();
		String clientCode=requestJson.get("clientCode").toString();
		String checkword=requestJson.get("checkword").toString();
		if(type.equals("2")){
			sfXML=deliveryService.getOrderSearchServiceRequestXml(requestJson);
		}
		System.out.println(sfXML);
		String resultXML=deliveryService.callSf(sfXML,url,clientCode,checkword);
		System.out.println("searchj:"+resultXML);
		List<JSONObject> list=deliveryService.getOrderSearchServiceRequestXmlResultByList(resultXML);
		//list.add(result);
//		System.out.println(resultXML);
//		return CommonUtil.successJson("fail");
		return CommonUtil.successPage1(list, 1);
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
	 */
//
//	public JSONObject todoSearchByStatus(JSONObject jsonObject) {
//		System.out.println("todoSearchByStatus:xxxxxxxxxxxxxxxx");
//		CommonUtil.fillPageParam(jsonObject);
//		System.out.println("jsonObject:"+jsonObject);
//		System.out.println(jsonObject.get("status").toString());
//		System.out.println("status:"+1);
//		List<JSONObject> list=new ArrayList<>();
//		list.add(deliveryService.getOrderServiceRequestXmlResult(jsonObject.toString()));
//
//		return CommonUtil.successPage(jsonObject, list, 1);
//	}







//	@RequiresPermissions("article:list")
	@GetMapping("/listDelivery")
	public JSONObject listOrder(HttpServletRequest request) {
		return deliveryService.listDelivery(CommonUtil.request2Json(request));
	}

	/**
	 * 按列排序+搜索
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listDeliveryBySearch")
	public JSONObject listDeliveryBySearch(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return deliveryService.listDeliveryBySearch(CommonUtil.request2Json(request));
	}

	/**
	 * 修改物流状态
	 */
	@RequiresPermissions("delivery:update")
	@PostMapping("/updateDelivery")
	public JSONObject updateDelivery(@RequestBody JSONObject requestJson) {
		System.out.println("updateDelivery requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "delivery_no");
		return deliveryService.updateDelivery(requestJson);
	}

	/**
	 * 新增物流待发货单，供其他模块调用，如出库模块
	 */
	@RequiresPermissions("delivery:add")
	@PostMapping("/addDeliveryOrder")
	public JSONObject addDeliveryOrder(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "delivery_status, order_mord_id");
		return deliveryService.addDeliveryOrder(requestJson);
	}

	/**
	 * 按状态搜索（待办事项功能）(author:bguan)
	 */
//	@RequiresPermissions("delivery:list")
	@PostMapping("/todoSearchByStatus")
	public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "status");
		return deliveryService.todoSearchByStatus(requestJson);
	}
}
