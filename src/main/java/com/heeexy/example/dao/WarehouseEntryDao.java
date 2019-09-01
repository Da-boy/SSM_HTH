package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: dengchenglong
 * @description: 订单信息Dao层
 */
public interface WarehouseEntryDao {

    List<JSONObject> listWarehouseEntry(JSONObject jsonObject);

    int countWarehouseEntry(JSONObject jsonObject);

    void updateWarehouseEntry(JSONObject requestJson);
    void updateWarehouseEntry2(JSONObject requestJson);

    List<JSONObject> isToday(JSONObject requestJson);

    void insertInventory(JSONObject requestJson);

    JSONObject getIncentoryByTime(JSONObject requestJson);

    void insertInventoryOfNull(JSONObject requestJson);
    void updateOrderStatus(JSONObject requestJson);

    void updateInventory(JSONObject requestJson);

    JSONObject getWarehouseEnd(JSONObject requestJson);


    /**
     * 排序采
     */
    int countWarehouseEntryBySearch(JSONObject jsonObject);
    List<JSONObject> listWarehouseEntryBySearch(JSONObject jsonObject);

    /**
     * 按状态搜索采购订单列表（待办事项功能）(author: bguan)
     */
    int todoSearchByStatus(JSONObject jsonObject);
    List<JSONObject> todoSearchEventByStatus(JSONObject jsonObject);

    /**
     * 审核功能：修改订单状态为“已入库”，“入库取消”(未使用)
     */
    int changeOrderStatus(JSONObject jsonObject);
    int changeOrderStatusMessage(JSONObject jsonObject);

    /**
     * 按列排序
     */
    List<JSONObject> listWarehouseEntryBySort(JSONObject jsonObject);
    int countWarehouseEntryBySort(JSONObject jsonObject);

    /**
     * 删除入库订单
     */
    int deleteWarehouseById(JSONObject jsonObject);
}