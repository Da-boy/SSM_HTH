package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.ChargeoffService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author: bguan
 * @description: 催账销账相关
 */
@RestController
@RequestMapping("/chargeoff")
public class ChargeoffController {
    @Autowired
    private ChargeoffService chargeoffService;

    /**
     * 按状态搜索（待办事项功能）(author:bguan)
     */
//    @RequiresPermissions("article:list")
    @PostMapping("/todoSearchByStatus")
    public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
        System.out.println("requestJson:"+requestJson);
        CommonUtil.hasAllRequired(requestJson, "status");
        return chargeoffService.todoSearchByStatus(requestJson);
    }

    /**
     * 新增催账销账单，供其他模块调用，如物流模块签收后可调用
     */
	@RequiresPermissions("orderbill:add")
    @PostMapping("/addOrderBill")
    public JSONObject addOrderBill(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "order_mord_id, orderbill_status");
        return chargeoffService.addOrderBill(requestJson);
    }

    /**
     * 销账操作
     */
    @RequiresPermissions("orderbill:chargeoff")
    @PostMapping("/orderChargeoff")
    public JSONObject orderChargeoff(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "sum, status, mord_id, advance_payment");
        return chargeoffService.orderChargeoff(requestJson);
    }
//    @RequiresPermissions("order:list")
    @GetMapping("/listChargeoffRecord")
    public JSONObject listChargeoffRecord(HttpServletRequest request) {
        return chargeoffService.listChargeoffRecord(CommonUtil.request2Json(request));
    }
    //销账催账：仅列出账单状态的父订单：["已签收待支付","部分支付","已支付"]
//    @RequiresPermissions("order:list")
    @GetMapping("/listBillOrder")
    public JSONObject listBillOrder(HttpServletRequest request) {
        return chargeoffService.listBillOrder(CommonUtil.request2Json(request));
    }
    //销账催账：检索功能
//    @RequiresPermissions("order:list")
    @GetMapping("/listBillOrderBySearch")
    public JSONObject listBillOrderBySearch(HttpServletRequest request) {
        return chargeoffService.listBillOrderBySearch(CommonUtil.request2Json(request));
    }
    //销账催账：发送邮件短信功能，同时记录数据库
    @RequiresPermissions("orderbill:urgebill")
    @PostMapping("/doNotification")
    public JSONObject doNotification(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "toStaffEmail, emailSubject, emailContent");
        return chargeoffService.doNotification(requestJson);
    }

    /**
     * 添加客户到黑名单/移除客户从黑名单
     */
    @RequiresPermissions(value={"orderbill:blacklistin", "orderbill:blacklistout"}, logical = Logical.OR)
    @PostMapping("/updateClientBlackList")
    public JSONObject updateClientBlackList(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_id, in_blacklist");
        return chargeoffService.updateClientBlackList(requestJson);
    }
    //列出所有黑名单客户
//    @RequiresPermissions("orderbill:add")
    @PostMapping("/listClientBlackList")
    public JSONObject listClientBlackList(HttpServletRequest request) {
        return chargeoffService.listClientBlackList(CommonUtil.request2Json(request));
    }
}
