package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
/**
 * @author: lingling
 * @description: 物流信息Dao层
 */
public interface DeliveryDao {
    /**
     * 统计物流信息总数
     */
    int countDelivery(JSONObject jsonObject);

    /**
     * 物流信息列表
     */
    List<JSONObject> listDelivery(JSONObject jsonObject);
    /**
     * 按列排序+搜索
     */
    List<JSONObject> listDeliveryBySearch(JSONObject jsonObject);
    int countDeliveryBySearch(JSONObject jsonObject);
    /**
     * 更新物流信息
     */
    int updateDelivery(JSONObject jsonObject);

    /**
     * 新增物流待发货单
     */
    int addDeliveryOrder(JSONObject jsonObject);

    /**
     * 按状态搜索（待办事项功能）(author: bguan)
     */
    int todoSearchByStatus(JSONObject jsonObject);
    List<JSONObject> todoSearchEventByStatus(JSONObject jsonObject);
}
