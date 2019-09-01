package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: dengchenglong
 */
public interface WarehouseEntryService {

    JSONObject listWarehouseEntry(JSONObject request2Json);

    JSONObject checkWarehouseEntry(JSONObject requestJson);

    /**
     * 搜索列表（全文检索）
     */
    JSONObject listWarehouseEntryBySearch(JSONObject request);

    /**
     * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
     */
    JSONObject todoSearchByStatus(JSONObject jsonObject);

    /**
     * 审核功能：修改订单状态为“已入库”，“入库取消”(未使用)
     */
    JSONObject changeOrderStatus(JSONObject jsonObject);

    /**
     * 按列排序
     */
    JSONObject listWarehouseEntryBySort(JSONObject jsonObject);

    /**
     * 删除入库订单
     */
    JSONObject deleteWarehouseById(JSONObject jsonObject);
}