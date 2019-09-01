package com.heeexy.example.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author:
 */
public interface DeliveryService {

//	JSONObject addPurchase(JSONObject jsonObject);


    String getOrderServiceRequestXml(JSONObject jsonObject);

    String callSf(String xmlStr, String url, String clientCode, String checkword);

    JSONObject getOrderServiceRequestXmlResult(String resultxmlStr);

    String getOrderSearchServiceRequestXml(JSONObject jsonObject);

    JSONObject getOrderSearchServiceRequestXmlResult(String resultxmlStr);

    List<JSONObject> getOrderSearchServiceRequestXmlResultByList(String resultxmlStr);
    /**
     * 物流信息列表
     */
    JSONObject listDelivery(JSONObject jsonObject);

    /**
     * 按列排序+搜索
     */
    JSONObject listDeliveryBySearch(JSONObject jsonObject);

    /**
     * 更新物流信息
     */
    JSONObject updateDelivery(JSONObject jsonObject);

    /**
     * 新增物流待发货单，供其他模块调用，如出库模块
     */
    JSONObject addDeliveryOrder(JSONObject requestJson);

    /**
     * 按状态搜索（待办事项功能）(author: bguan)
     */
    JSONObject todoSearchByStatus(JSONObject jsonObject);

}
