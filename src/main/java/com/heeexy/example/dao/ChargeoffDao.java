package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: bguan
 * @description: 系统中设置相关
 */
public interface ChargeoffDao {

    /**
     * 按状态搜索（待办事项功能）(author: bguan)
     */
    int todoSearchByStatus(JSONObject jsonObject);
    List<JSONObject> todoSearchEventByStatus(JSONObject jsonObject);

    /**
     * 新增催账销账单，供其他模块调用，如物流模块签收后可调用
     */
    int addOrderBill(JSONObject jsonObject);

    /**
     * 销账操作
     */
    int addChargeoffRecord(JSONObject jsonObject);
    int updateChargeoffRecord(JSONObject jsonObject);
    int updateAdvancePayment(JSONObject jsonObject);
    int countChargeoffRecord(JSONObject jsonObject);
    List<JSONObject> listChargeoffRecord(JSONObject jsonObject);

    /**
     * 销账催账：仅列出账单状态的父订单：["已签收待支付","部分支付","已支付"]
     */
    int countBillOrder(JSONObject jsonObject);
    List<JSONObject> listBillOrder(JSONObject jsonObject);
    /**
     * 销账催账：检索功能
     */
    int countBillOrderBySearch(JSONObject jsonObject);
    List<JSONObject> listBillOrderBySearch(JSONObject jsonObject);
    int logUigeBillRecord(JSONObject jsonObject);

    /**
     * 添加客户到黑名单/移除客户从黑名单
     */
    int updateClientBlackList(JSONObject jsonObject);
    //列出所有黑名单客户
    List<JSONObject> listClientBlackList(JSONObject jsonObject);
}
