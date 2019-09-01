package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: bguan
 * @description: 系统中设置相关
 */
public interface ChargeoffService {
    /**
     * 按状态搜索（待办事项功能）(author: bguan)
     */
    JSONObject todoSearchByStatus(JSONObject jsonObject);

    /**
     * 新增催账销账单，供其他模块调用，如物流模块签收后可调用
     */
    JSONObject addOrderBill(JSONObject requestJson);

    /**
     * 销账操作
     */
    JSONObject orderChargeoff(JSONObject jsonObject);
    JSONObject listChargeoffRecord(JSONObject jsonObject);
    //
    JSONObject listBillOrder(JSONObject jsonObject);
    //销账催账：检索功能
    JSONObject listBillOrderBySearch(JSONObject jsonObject);
    //销账催账：发送邮件短信功能，同时记录数据库
    JSONObject doNotification(JSONObject jsonObject);

    /**
     * 添加客户到黑名单/移除客户从黑名单
     */
    JSONObject updateClientBlackList(JSONObject requestJson);
    //列出所有黑名单客户
    JSONObject listClientBlackList(JSONObject request2Json);
}
