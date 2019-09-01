package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: bguan
 * @description: 系统中设置相关
 */
public interface SettingService {
    /**
     * 自动催账设置和读取
     */
    JSONObject getAutoUrgeBill(JSONObject jsonObject);
    JSONObject setAutoUrgeBill(JSONObject jsonObject);

    /**
     * 打印方案保存和读取
     */
    JSONObject getPrintScheme(JSONObject jsonObject);
    JSONObject setPrintScheme(JSONObject jsonObject);

    /**
     * 全局设置保存和读取
     */
    JSONObject getGlobalConfig(JSONObject jsonObject);
    JSONObject setGlobalConfig(JSONObject jsonObject);


    JSONObject getThemeColorByUserId(JSONObject request2Json);
    JSONObject saveColorFun(JSONObject request2Json);

    JSONObject addTableDisplayByUserId(JSONObject jsonObject);

    JSONObject setTableDisplayByUserId(JSONObject jsonObject);

    JSONObject getTableDisplayByUserId(JSONObject request2Json);

    JSONObject insertTableDisplayByUserId(JSONObject request2Json);

    /**
     * 各模块主页默认显示数据条数的设置
     */
    JSONObject getDefPageNum(JSONObject request2Json);
    JSONObject updateDefPageNum(JSONObject requestJson);
}
