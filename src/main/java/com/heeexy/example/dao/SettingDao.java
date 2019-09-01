package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: bguan
 * @description: 系统中设置相关
 */
public interface SettingDao {

    /**
     * 自动催账设置和读取
     */
    List<JSONObject> getAutoUrgeBill(JSONObject jsonObject);
    int setAutoUrgeBill(JSONObject jsonObject);
    int updateAutoUrgeBill(JSONObject jsonObject);
    int countAutoUrgeBill();

    /**
     * 打印方案保存和读取
     */
    List<JSONObject> getPrintScheme(JSONObject jsonObject);
    int insertPrintScheme(JSONObject jsonObject);
    int setPrintScheme(JSONObject jsonObject);

    /**
     * 全局设置保存和读取
     */
    List<JSONObject> getGlobalConfig(JSONObject jsonObject);
    int insertGlobalConfig(JSONObject jsonObject);
    int setGlobalConfig(JSONObject jsonObject);

    List<JSONObject> getThemeColorByUserId(JSONObject jsonObject);
    void updateThemeColorByUserId(JSONObject jsonObject);

    void addThemeColorByUserId(JSONObject jsonObject);

    int addTableDisplayByUserId(JSONObject jsonObject);

    List<JSONObject> getTableDisplayByUserId(JSONObject jsonObject);

    int setTableDisplayByUserId(JSONObject jsonObject);

    /**
     * 各模块主页默认显示数据条数的设置
     */
    List<JSONObject> getDefPageNum(JSONObject jsonObject);
    int insertDefPageNum(JSONObject jsonObject);
    int updateDefPageNum(JSONObject jsonObject);



    /*
    * 添加人员的时候添加个人配置
    * */

    void addGlobalConfig(JSONObject jsonObject);


}
