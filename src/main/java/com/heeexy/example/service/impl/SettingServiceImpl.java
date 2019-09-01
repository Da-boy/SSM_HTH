package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.SettingDao;
import com.heeexy.example.service.SettingService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: bguan
 * @description: 系统中设置相关
 */
@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingDao settingDao;
    /**
     * 自动催账设置和读取
     */
    @Override
    public JSONObject getAutoUrgeBill(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = settingDao.getAutoUrgeBill(jsonObject);
        System.out.print(list);
        return CommonUtil.successPage(jsonObject, list, list.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject setAutoUrgeBill(JSONObject jsonObject) {
        //查询数据库是否存在设置
        int count = settingDao.countAutoUrgeBill();
        if(count == 0){
            settingDao.setAutoUrgeBill(jsonObject);
        }
        else{
            List<JSONObject> list = settingDao.getAutoUrgeBill(jsonObject);
            System.out.println(list);
            System.out.println(list.get(0).getInteger("id"));

            jsonObject.put("id", list.get(0).getInteger("id"));
            settingDao.updateAutoUrgeBill(jsonObject);
        }
        return CommonUtil.successJson();
    }



    /**
     * 打印方案保存和读取
     */
    @Override
    public JSONObject getPrintScheme(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = settingDao.getPrintScheme(jsonObject);
        System.out.println("settingDao.getPrintScheme: "+list);
        return CommonUtil.successPage(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject setPrintScheme(JSONObject jsonObject) {
        //查询数据库是否存在设置
        List<JSONObject> list = settingDao.getPrintScheme(jsonObject);
        System.out.println("PrintScheme:"+list);
        if(list.size() == 0){
            settingDao.insertPrintScheme(jsonObject);
        } else {
            settingDao.setPrintScheme(jsonObject);
        }
        return CommonUtil.successJson();
    }

    /**
     * 全局设置保存和读取
     */
    @Override
    public JSONObject getGlobalConfig(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = settingDao.getGlobalConfig(jsonObject);
        System.out.println("settingDao.getPrintScheme: "+list);
        return CommonUtil.successPage(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject setGlobalConfig(JSONObject jsonObject) {
        //查询数据库是否存在设置
        List<JSONObject> list = settingDao.getGlobalConfig(jsonObject);
        System.out.println("PrintScheme:"+list);
        if(list.size() == 0){
            settingDao.insertGlobalConfig(jsonObject);
        } else {
            settingDao.setGlobalConfig(jsonObject);
        }
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject getThemeColorByUserId(JSONObject jsonObject) {
        List<JSONObject> list = settingDao.getThemeColorByUserId(jsonObject);
        System.out.println("list.size():"+list.size());
        if(list.size() >= 1){
            return CommonUtil.successJson(list.get(0));
        }
        else{
            return CommonUtil.successJson("failed");
        }
    }

    @Override
    public JSONObject saveColorFun(JSONObject jsonObject) {
        List<JSONObject> list = settingDao.getThemeColorByUserId(jsonObject);
        System.out.println("list.size():"+list.size());
        if (list.size() >= 1){
            settingDao.updateThemeColorByUserId(jsonObject);
            return CommonUtil.successJson("更新成功！");
        }else{
            settingDao.addThemeColorByUserId(jsonObject);
            return CommonUtil.successJson("添加成功！");
        }
    }


    /**
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addTableDisplayByUserId(JSONObject jsonObject) {
        settingDao.addTableDisplayByUserId(jsonObject);
        return CommonUtil.successJson();
    }


    /**
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject setTableDisplayByUserId(JSONObject jsonObject) {
        settingDao.setTableDisplayByUserId(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject insertTableDisplayByUserId(JSONObject jsonObject) {
        List<JSONObject> list = settingDao.getTableDisplayByUserId(jsonObject);
        System.out.println("list.size():"+list.size());
        if(list.size() < 1){
            settingDao.addTableDisplayByUserId(jsonObject);
        }
        else{
            settingDao.setTableDisplayByUserId(jsonObject);
        }
        //settingDao.setTableDisplayByUserId(jsonObject);
        return CommonUtil.successJson();
    }


    @Override
    public JSONObject getTableDisplayByUserId(JSONObject jsonObject) {
        List<JSONObject> list = settingDao.getTableDisplayByUserId(jsonObject);
        System.out.println("list.size():"+list.size());
        if(list.size() >= 1){
            return CommonUtil.successJson(list.get(0));
        }
        else{
            return CommonUtil.successJson("failed");
        }
    }

    /**
     * 各模块主页默认显示数据条数的设置
     */
    @Override
    public JSONObject getDefPageNum(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = settingDao.getDefPageNum(jsonObject);
        System.out.print(list);
        //若当前用户还未有记录，生成新记录
        if(list.size()<1){
            settingDao.insertDefPageNum(jsonObject);
            list = settingDao.getDefPageNum(jsonObject);
        }
//        return CommonUtil.successPage(jsonObject, list, list.size());
        return CommonUtil.successJson(list.get(0));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateDefPageNum(JSONObject jsonObject) {
        jsonObject.put("update_time", CommonUtil.getCurTimestampForDB());
        settingDao.updateDefPageNum(jsonObject);
        return CommonUtil.successJson();
    }

}
